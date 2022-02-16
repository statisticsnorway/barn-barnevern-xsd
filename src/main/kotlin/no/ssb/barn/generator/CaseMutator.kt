package no.ssb.barn.generator

import no.ssb.barn.xsd.*
import java.time.LocalDate
import java.util.*

object CaseMutator {

    @JvmStatic
    fun mutate(caseToMutate: CaseEntry): CaseEntry =
        newStateFuncMap.entries.asSequence()
            .filter { (stateTransition, _) -> stateTransition.first == caseToMutate.state }
            .toList()
            .ifEmpty { return caseToMutate } // if state == Plan, there is no transition
            .random()
            .let { (stateTransition, transformFunc) ->
                transformFunc(caseToMutate) // call transformation
                caseToMutate.state = stateTransition.second
                caseToMutate
            }

    // START Melding

    @JvmStatic
    fun fromMessageToCaseClosed(caseEntry: CaseEntry) {
        closeCurrentMessage("1", caseEntry)
    }

    @JvmStatic
    fun fromMessageToInvestigationStarted(caseEntry: CaseEntry) {

        // reset company and add message
        val company = closeCurrentMessage("2", caseEntry)

        UndersokelseType().also { investigation ->
            company.undersokelse.add(investigation)

            company.relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = company.melding.last().id,
                    fraType = BegrepsType.MELDING,
                    tilId = investigation.id,
                    tilType = BegrepsType.UNDERSOKELSE
                )
            )
        }
    }

    @JvmStatic
    fun fromMessageToDecision(caseEntry: CaseEntry) {

        val company = closeCurrentMessage("2", caseEntry)


        createVedtakType().also { decision ->
            company.vedtak.add(decision)

            company.relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = company.melding.last().id,
                    fraType = BegrepsType.MELDING,
                    tilId = decision.id,
                    tilType = BegrepsType.VEDTAK
                )
            )
        }
    }

    private fun closeCurrentMessage(
        code: String,
        caseEntry: CaseEntry
    ): VirksomhetType {
        val message = caseEntry.lastCompany.melding.last()

        // close active message
        message.konklusjon = MeldingKonklusjonType(
            kode = MeldingKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == code }.code
        )

        // reset company and add message
        val company = caseEntry.resetCompany()
        company.melding.add(message)

        return company
    }

    // START Undersokelse

    @JvmStatic
    fun fromInvestigationStartedToEnded(caseEntry: CaseEntry) {
        closeCurrentInvestigation(
            UndersokelseKonklusjonType(
                kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                    .filter { it.code != "1" }
                    .random()
                    .code
            ),
            caseEntry)
    }

    @JvmStatic
    fun fromInvestigationStartedToMeasureViaDecision(caseEntry: CaseEntry) {
        val company = closeCurrentInvestigation(
            UndersokelseKonklusjonType(
                kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                    .first { it.code == "1" }
                    .code
            ),
            caseEntry
        )

        val decision = createVedtakType()
        decision.konklusjon = VedtakKonklusjonType()

        company.vedtak.add(decision)
        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = company.undersokelse.last().id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )

        val measure = createTiltakType(LocalDate.now())
        company.tiltak.add(measure)
        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = decision.id,
                fraType = BegrepsType.VEDTAK,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromInvestigationEndedToMeasure(caseEntry: CaseEntry) {
        val investigation = caseEntry.lastCompany.undersokelse.last()
        val measure = createTiltakType(LocalDate.now())

        val company = caseEntry.resetCompany()

        company.tiltak.add(measure)

        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromInvestigationEndedToDecision(caseEntry: CaseEntry) {
        val investigation = caseEntry.lastCompany.undersokelse.last()
        val decision = createVedtakType()

        val company = caseEntry.resetCompany()

        company.vedtak.add(decision)

        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )
    }

    private fun closeCurrentInvestigation(
        conclusion: UndersokelseKonklusjonType,
        caseEntry: CaseEntry
    ): VirksomhetType {
        val investigation = caseEntry.lastCompany
            .undersokelse
            .last()

        // close active investigation
        investigation.konklusjon = conclusion

        // reset company and add investigation
        val company = caseEntry.resetCompany()
        company.undersokelse.add(investigation)

        return company
    }

    // START Plan

    @JvmStatic
    fun fromPlanToEvaluatedPlan(caseEntry: CaseEntry) {
        caseEntry.lastCompany
            .plan
            .last()
            .evaluering.add(PlanEvalueringType())
    }

    @JvmStatic
    fun fromPlanToCaseClosed(caseEntry: CaseEntry) {
        caseEntry.lastCompany
            .plan
            .last()
            .konklusjon = PlanKonklusjonType()
    }

    // START Tiltak

    @JvmStatic
    fun fromMeasureToPlan(caseEntry: CaseEntry) {
        closeCurrentMeasure(caseEntry).plan.add(PlanType())
    }

    @JvmStatic
    fun fromMeasureToDecision(caseEntry: CaseEntry) {
        closeCurrentMeasure(caseEntry).vedtak.add(createVedtakType())
    }

    @JvmStatic
    fun fromMeasureToAfterCare(caseEntry: CaseEntry) {
        closeCurrentMeasure(caseEntry).ettervern.add(EttervernType())
    }

    private fun closeCurrentMeasure(caseEntry: CaseEntry): VirksomhetType {
        val measure = caseEntry.lastCompany.tiltak.last()
        measure.konklusjon = TiltakKonklusjonType()

        val company = caseEntry.resetCompany()
        company.tiltak.add(measure)

        return company
    }

    // START Vedtak

    @JvmStatic
    fun fromDecisionToMeasure(caseEntry: CaseEntry) {
        val company = closeCurrentDecision(caseEntry)

        val measure = createTiltakType(caseEntry.updated)
        company.tiltak.add(measure)

        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = company.vedtak.last().id,
                fraType = BegrepsType.VEDTAK,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromDecisionToAnotherDecision(caseEntry: CaseEntry) {
        val company = closeCurrentDecision(caseEntry)

        val decision = createVedtakType()
        company.vedtak.add(decision)

        if (company.tiltak.any()) {
            company.relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = decision.id,
                    fraType = BegrepsType.VEDTAK,
                    tilId = company.tiltak.last().id,
                    tilType = BegrepsType.TILTAK
                )
            )
        }
    }

    @JvmStatic
    fun fromDecisionToAfterCare(caseEntry: CaseEntry) {
        val company = closeCurrentDecision(caseEntry)

        val afterCare = EttervernType()
        company.ettervern.add(afterCare)

        company.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = company.vedtak.last().id,
                fraType = BegrepsType.VEDTAK,
                tilId = afterCare.id,
                tilType = BegrepsType.ETTERVERN
            )
        )
    }

    private fun closeCurrentDecision(caseEntry: CaseEntry): VirksomhetType {
        val decision = caseEntry.lastCompany.vedtak.last()
        decision.konklusjon = VedtakKonklusjonType()

        val company = caseEntry.resetCompany()
        company.vedtak.add(decision)

        return company
    }

    // START Ettervern

    @JvmStatic
    fun fromAfterCareToCaseClosed(caseEntry: CaseEntry) {
        closeCurrentAfterCare(
            EttervernKonklusjonType(
                kode = EttervernKonklusjonType.getCodes(LocalDate.now())
                    .first { it.code == "1" }.code
            ),
            caseEntry
        )
    }

    @JvmStatic
    fun fromAfterCareToMeasure(caseEntry: CaseEntry) {
        closeCurrentAfterCare(
            EttervernKonklusjonType(),
            caseEntry
        ).tiltak.add(createTiltakType(caseEntry.updated))
    }

    @JvmStatic
    fun fromAfterCareToDecision(caseEntry: CaseEntry) {
        closeCurrentAfterCare(
            EttervernKonklusjonType(),
            caseEntry
        ).vedtak.add(createVedtakType())
    }

    private fun closeCurrentAfterCare(
        conclusion: EttervernKonklusjonType,
        caseEntry: CaseEntry
    ): VirksomhetType {
        val afterCare = caseEntry.lastCompany.ettervern.last()
        afterCare.konklusjon = conclusion

        val company = caseEntry.resetCompany()
        company.ettervern.add(afterCare)

        return company
    }

    private val newStateFuncMap = mapOf(
        Pair(
            Pair(BarnevernState.MESSAGE, BarnevernState.CASE_CLOSED),
            ::fromMessageToCaseClosed
        ),
        Pair(
            Pair(BarnevernState.MESSAGE, BarnevernState.INVESTIGATION_STARTED),
            ::fromMessageToInvestigationStarted
        ),
        Pair(
            Pair(BarnevernState.MESSAGE, BarnevernState.DECISION),
            ::fromMessageToDecision
        ),

        Pair(
            Pair(
                BarnevernState.INVESTIGATION_STARTED,
                BarnevernState.INVESTIGATION_ENDED
            ),
            ::fromInvestigationStartedToEnded
        ),

        Pair(
            Pair(BarnevernState.INVESTIGATION_STARTED, BarnevernState.MEASURE),
            ::fromInvestigationStartedToMeasureViaDecision
        ),

        Pair(
            Pair(BarnevernState.INVESTIGATION_ENDED, BarnevernState.MEASURE),
            ::fromInvestigationEndedToMeasure
        ),
        Pair(
            Pair(BarnevernState.INVESTIGATION_ENDED, BarnevernState.DECISION),
            ::fromInvestigationEndedToDecision
        ),

        Pair(
            Pair(BarnevernState.PLAN, BarnevernState.PLAN),
            ::fromPlanToEvaluatedPlan
        ),
        Pair(
            Pair(BarnevernState.PLAN, BarnevernState.CASE_CLOSED),
            ::fromPlanToCaseClosed
        ),

        Pair(
            Pair(BarnevernState.MEASURE, BarnevernState.PLAN),
            ::fromMeasureToPlan
        ),
        Pair(
            Pair(BarnevernState.MEASURE, BarnevernState.DECISION),
            ::fromMeasureToDecision
        ),
        Pair(
            Pair(BarnevernState.MEASURE, BarnevernState.AFTERCARE),
            ::fromMeasureToAfterCare
        ),

        Pair(
            Pair(BarnevernState.DECISION, BarnevernState.MEASURE),
            ::fromDecisionToMeasure
        ),
        Pair(
            Pair(BarnevernState.DECISION, BarnevernState.DECISION),
            ::fromDecisionToAnotherDecision
        ),
        Pair(
            Pair(BarnevernState.DECISION, BarnevernState.AFTERCARE),
            ::fromDecisionToAfterCare
        ),

        Pair(
            Pair(BarnevernState.AFTERCARE, BarnevernState.CASE_CLOSED),
            ::fromAfterCareToCaseClosed
        ),
        Pair(
            Pair(BarnevernState.AFTERCARE, BarnevernState.MEASURE),
            ::fromAfterCareToMeasure
        ),
        Pair(
            Pair(BarnevernState.AFTERCARE, BarnevernState.DECISION),
            ::fromAfterCareToDecision
        )
    )

    private fun createVedtakType(): VedtakType =
        VedtakType(lovhjemmel = createLegalBasis())

    private fun createTiltakType(startDate: LocalDate): TiltakType =
        TiltakType(
            startDato = startDate,
            kategori = KategoriType(
                kode = KategoriType.getCodes(startDate).random().code,
                presisering = "~Presisering~"
            ),
            lovhjemmel = createLegalBasis()
        )

    private fun createLegalBasis(): LovhjemmelType =
        LovhjemmelType(
            kapittel = listOf("4", "5").random(), // make 50% of TiltakType = "omsorgstiltak"
            paragraf = listOf("8", "12").random(),
            ledd = mutableListOf(listOf("2", "3").random())
        )
}