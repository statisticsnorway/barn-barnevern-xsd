package no.ssb.barn.generator

import no.ssb.barn.xsd.*
import java.time.LocalDate

object CaseMutator {

    @JvmStatic
    fun mutate(caseToMutate: CaseEntry): CaseEntry =
        newStateFuncMap.entries.asSequence()
            .filter { it.key.first == caseToMutate.state }
            .toList()
            .ifEmpty { return caseToMutate } // if state == Plan, there is no transition
            .random()
            .run {
                value(caseToMutate) // call transformation
                caseToMutate.state = key.second
                caseToMutate
            }

    // START Melding

    @JvmStatic
    fun fromMessageToInvestigationStarted(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val message = company.melding.last()
        val investigation = UndersokelseType()

        company.undersokelse.add(investigation)

        company.relasjon.add(
            RelasjonType(
                fraId = message.id,
                fraType = BegrepsType.MELDING,
                tilId = investigation.id,
                tilType = BegrepsType.UNDERSOKELSE
            )
        )
    }

    @JvmStatic
    fun fromMessageToDecision(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val message = company.melding.last()
        val decision = createVedtakType()

        company.vedtak.add(decision)

        company.relasjon.add(
            RelasjonType(
                fraId = message.id,
                fraType = BegrepsType.MELDING,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )
    }

    // START Undersokelse

    @JvmStatic
    fun fromInvestigationStartedToEnded(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last()
            .undersokelse.last()
            .konklusjon = UndersokelseKonklusjonType(
            kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                .filter { it.code != "1" }
                .random()
                .code
        )
    }

    @JvmStatic
    fun fromInvestigationStartedToMeasureViaDecision(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val investigation = company.undersokelse.last()

        investigation.konklusjon = UndersokelseKonklusjonType(
            kode = UndersokelseKonklusjonType.getCodes(LocalDate.now())
                .first { it.code == "1" }
                .code
        )

        val decision = createVedtakType()
        decision.konklusjon = VedtakKonklusjonType()

        company.vedtak.add(decision)
        company.relasjon.add(
            RelasjonType(
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )

        val measure = createTiltakType(LocalDate.now())
        company.tiltak.add(measure)
        company.relasjon.add(
            RelasjonType(
                fraId = decision.id,
                fraType = BegrepsType.VEDTAK,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromInvestigationEndedToMeasure(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val investigation = company.undersokelse.last()
        val measure = createTiltakType(LocalDate.now())

        company.tiltak.add(measure)

        company.relasjon.add(
            RelasjonType(
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromInvestigationEndedToDecision(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val investigation = company.undersokelse.last()
        val decision = createVedtakType()

        company.vedtak.add(decision)

        company.relasjon.add(
            RelasjonType(
                fraId = investigation.id,
                fraType = BegrepsType.UNDERSOKELSE,
                tilId = decision.id,
                tilType = BegrepsType.VEDTAK
            )
        )
    }

    // START Plan
    // N/A

    // START Tiltak

    @JvmStatic
    fun fromMeasureToPlan(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().plan.add(PlanType())
    }

    @JvmStatic
    fun fromMeasureToDecision(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().vedtak.add(createVedtakType())
    }

    @JvmStatic
    fun fromMeasureToAfterCare(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().ettervern.add(EttervernType())
    }

    // START Vedtak

    @JvmStatic
    fun fromDecisionToMeasure(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val decision = company.vedtak.last()
        val measure = createTiltakType(caseEntry.updated)

        company.tiltak.add(measure)

        company.relasjon.add(
            RelasjonType(
                fraId = decision.id,
                fraType = BegrepsType.VEDTAK,
                tilId = measure.id,
                tilType = BegrepsType.TILTAK
            )
        )
    }

    @JvmStatic
    fun fromDecisionToAnotherDecision(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().vedtak.add(
            createVedtakType()
        )
    }

    @JvmStatic
    fun fromDecisionToAfterCare(caseEntry: CaseEntry) {
        val company = caseEntry.barnevern.sak.virksomhet.last()
        val decision = company.vedtak.last()
        val afterCare = EttervernType()

        company.ettervern.add(afterCare)

        company.relasjon.add(
            RelasjonType(
                fraId = decision.id,
                fraType = BegrepsType.VEDTAK,
                tilId = afterCare.id,
                tilType = BegrepsType.ETTERVERN
            )
        )
    }

    // START Ettervern

    @JvmStatic
    fun fromAfterCareToMeasure(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().tiltak.add(
            createTiltakType(caseEntry.updated)
        )
    }

    @JvmStatic
    fun fromAfterCareToDecision(caseEntry: CaseEntry) {
        caseEntry.barnevern.sak.virksomhet.last().vedtak.add(
            createVedtakType()
        )
    }

    private val newStateFuncMap = mapOf(
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
            Pair(BarnevernState.AFTERCARE, BarnevernState.MEASURE),
            ::fromAfterCareToMeasure
        ),
        Pair(
            Pair(BarnevernState.AFTERCARE, BarnevernState.DECISION),
            ::fromAfterCareToDecision
        )
    )

    private fun createVedtakType(): VedtakType =
        VedtakType(
            lovhjemmel = LovhjemmelType(
                kapittel = "1",
                paragraf = "2",
                ledd = mutableListOf("~ledd~")
            )
        )

    private fun createTiltakType(startDate: LocalDate): TiltakType =
        TiltakType(
            startDato = startDate,
            lovhjemmel = LovhjemmelType(
                kapittel = "1",
                paragraf = "2",
                ledd = mutableListOf("~ledd~")
            ),
            kategori = KategoriType(presisering = "~Presisering~")
        )
}