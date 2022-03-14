package no.ssb.barn.generator

import no.ssb.barn.xsd.*
import java.time.LocalDate
import java.time.ZonedDateTime
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
        caseEntry.barnevern.sak.melding.last().konklusjon =
            MeldingKonklusjonType(
                sluttDato = ZonedDateTime.now(),
                kode = MeldingKonklusjonType.getCodes(LocalDate.now())
                    .first { it.code == "1" }.code
            )
    }

    @JvmStatic
    fun fromMessageToInvestigationStarted(caseEntry: CaseEntry) {
        // close active message
        caseEntry.barnevern.sak.melding.last().konklusjon = MeldingKonklusjonType(
            sluttDato = ZonedDateTime.now(),
            kode = MeldingKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == "2" }.code
        )

        UndersokelseType(
            id = UUID.randomUUID(),
            startDato = ZonedDateTime.now()
        ).also { investigation ->
            caseEntry.barnevern.sak.undersokelse.add(investigation)

            caseEntry.barnevern.sak.relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = caseEntry.barnevern.sak.melding.last().id,
                    fraType = BegrepsType.MELDING,
                    tilId = investigation.id,
                    tilType = BegrepsType.UNDERSOKELSE
                )
            )
        }
    }

    @JvmStatic
    fun fromMessageToDecision(caseEntry: CaseEntry) {
        // close active message
        caseEntry.barnevern.sak.melding.last().konklusjon = MeldingKonklusjonType(
            sluttDato = ZonedDateTime.now(),
            kode = MeldingKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == "2" }.code
        )

        createVedtakType().also { decision ->
            caseEntry.barnevern.sak.vedtak.add(decision)

            caseEntry.barnevern.sak.relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = caseEntry.barnevern.sak.melding.last().id,
                    fraType = BegrepsType.MELDING,
                    tilId = decision.id,
                    tilType = BegrepsType.VEDTAK
                )
            )
        }
    }

    // START Undersokelse

    @JvmStatic
    fun fromInvestigationStartedToEnded(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak
            .undersokelse
            .last()
            .konklusjon = UndersokelseKonklusjonType(
            sluttDato = ZonedDateTime.now(),
            kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                .filter { it.code != "1" }
                .random()
                .code
        )
    }

    @JvmStatic
    fun fromInvestigationStartedToMeasureViaDecision(caseEntry: CaseEntry) {
        val investigation = caseEntry.barnevern.sak.undersokelse.last()

        investigation.konklusjon = UndersokelseKonklusjonType(
            sluttDato = ZonedDateTime.now(),
            kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == "1" }
                .code
        )

        val decision = createVedtakType()
        decision.konklusjon = VedtakKonklusjonType(sluttDato = ZonedDateTime.now())

        caseEntry.barnevern.sak.vedtak.add(decision)
        caseEntry.barnevern.sak.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )

        val measure = createTiltakType(ZonedDateTime.now())
        caseEntry.barnevern.sak.tiltak.add(measure)
        caseEntry.barnevern.sak.relasjon.add(
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
        val measure = createTiltakType(ZonedDateTime.now())

        caseEntry.barnevern.sak.tiltak.add(measure)

        caseEntry.barnevern.sak.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = caseEntry.barnevern.sak.undersokelse.last().id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromInvestigationEndedToDecision(caseEntry: CaseEntry) {
        val decision = createVedtakType()
        decision.jmfrLovhjemmel = mutableListOf(
            LovhjemmelType(
                ledd = mutableListOf("~ledd~")
            )
        )
        decision.status = mutableListOf(VedtakStatusType(endretDato = ZonedDateTime.now()))

        caseEntry.barnevern.sak.vedtak.add(decision)

        caseEntry.barnevern.sak.relasjon.add(
            RelasjonType(
                id = UUID.randomUUID(),
                fraId = caseEntry.barnevern.sak.undersokelse.last().id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )
    }

    // START Plan

    @JvmStatic
    fun fromPlanToEvaluatedPlan(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak
            .plan
            .last()
            .evaluering.add(PlanEvalueringType())
    }

    @JvmStatic
    fun fromPlanToCaseClosed(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak
            .plan
            .last()
            .konklusjon = PlanKonklusjonType(ZonedDateTime.now())
    }

    // START Tiltak

    @JvmStatic
    fun fromMeasureToPlan(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {

            // close current measure
            tiltak.last().konklusjon = TiltakKonklusjonType()

            plan.add(PlanType(id = UUID.randomUUID()))
        }
    }

    @JvmStatic
    fun fromMeasureToDecision(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {

            // close current measure
            tiltak.last().konklusjon = TiltakKonklusjonType()

            vedtak.add(createVedtakType())
        }
    }

    @JvmStatic
    fun fromMeasureToAfterCare(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {

            // close current measure
            tiltak.last().konklusjon = TiltakKonklusjonType()

            ettervern.add(
                EttervernType(
                    id = UUID.randomUUID(),
                    tilbudSendtDato = ZonedDateTime.now()
                )
            )
        }
    }

    // START Vedtak

    @JvmStatic
    fun fromDecisionToMeasure(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {
            // close current decision
            vedtak.last().konklusjon = VedtakKonklusjonType(sluttDato = ZonedDateTime.now())

            val measure = createTiltakType(caseEntry.updated)
            tiltak.add(measure)

            relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = vedtak.last().id,
                    fraType = BegrepsType.VEDTAK,
                    tilId = measure.id,
                    tilType = BegrepsType.TILTAK
                )
            )
        }
    }

    @JvmStatic
    fun fromDecisionToAnotherDecision(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {
            // close current decision
            vedtak.last().konklusjon = VedtakKonklusjonType(sluttDato = ZonedDateTime.now())

            val decision = createVedtakType()
            vedtak.add(decision)

            if (tiltak.any()) {
                relasjon.add(
                    RelasjonType(
                        id = UUID.randomUUID(),
                        fraId = decision.id,
                        fraType = BegrepsType.VEDTAK,
                        tilId = tiltak.last().id,
                        tilType = BegrepsType.TILTAK
                    )
                )
            }
        }
    }

    @JvmStatic
    fun fromDecisionToAfterCare(caseEntry: CaseEntry) {
        with(caseEntry.barnevern.sak) {
            // close current decision
            vedtak.last().konklusjon = VedtakKonklusjonType(sluttDato = ZonedDateTime.now())

            val afterCare = EttervernType(
                id = UUID.randomUUID(),
                tilbudSendtDato = ZonedDateTime.now()
            )
            ettervern.add(afterCare)

            relasjon.add(
                RelasjonType(
                    id = UUID.randomUUID(),
                    fraId = vedtak.last().id,
                    fraType = BegrepsType.VEDTAK,
                    tilId = afterCare.id,
                    tilType = BegrepsType.ETTERVERN
                )
            )
        }
    }

    // START Ettervern

    @JvmStatic
    fun fromAfterCareToCaseClosed(caseEntry: CaseEntry) {
        // close current after-care
        caseEntry.barnevern.sak
            .ettervern
            .last().konklusjon = EttervernKonklusjonType(
            sluttDato = ZonedDateTime.now(),
            kode = EttervernKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == "1" }.code
        )
    }

    @JvmStatic
    fun fromAfterCareToMeasure(caseEntry: CaseEntry) {
        // close current after-care
        caseEntry.barnevern.sak
            .ettervern
            .last()
            .konklusjon = EttervernKonklusjonType(sluttDato = ZonedDateTime.now())

        caseEntry.barnevern.sak.tiltak.add(
            createTiltakType(caseEntry.updated)
        )
    }

    @JvmStatic
    fun fromAfterCareToDecision(caseEntry: CaseEntry) {
        // close current after-care
        caseEntry.barnevern.sak
            .ettervern
            .last()
            .konklusjon = EttervernKonklusjonType(sluttDato = ZonedDateTime.now())

        caseEntry.barnevern.sak.vedtak.add(
            createVedtakType()
        )
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

    private fun createVedtakType(): VedtakType {
        return VedtakType(
            id = UUID.randomUUID(),
            startDato = ZonedDateTime.now(),
            status = mutableListOf(VedtakStatusType(endretDato = ZonedDateTime.now())),
            lovhjemmel = createLegalBasis(),
            jmfrLovhjemmel = mutableListOf(
                LovhjemmelType(
                    ledd = mutableListOf("~ledd~")
                )
            )
        )
    }

    private fun createTiltakType(startDate: ZonedDateTime): TiltakType =
        TiltakType(
            id = UUID.randomUUID(),
            startDato = startDate,
            kategori = KategoriType(
                kode = KategoriType.getCodes(startDate.toLocalDate()).random().code,
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