package no.ssb.barn.generator

import no.ssb.barn.xsd.UndersokelseType

object CaseMutator {

    @JvmStatic
    fun mutate(caseToMutate: CaseEntry) =
        newStateFuncMap.entries.asSequence()
            .filter { it.key.first == caseToMutate.state }
            .toList()
            .ifEmpty { return } // if state == Plan, there is no transition
            .random()
            .run {
                value(caseToMutate) // call transformation
                caseToMutate.state = key.second
            }

    // START Melding

    @JvmStatic
    fun fromMessageToInvestigation(case: CaseEntry) {
        case.barnevern.sak.virksomhet[0].undersokelse =
            listOf(UndersokelseType())
    }

    @JvmStatic
    fun fromMessageToDecision(case: CaseEntry) {
    }

    // START Undersokelse

    @JvmStatic
    fun fromInvestigationToMeasure(case: CaseEntry) {
    }

    @JvmStatic
    fun fromInvestigationToDecision(case: CaseEntry) {
    }

    // START Plan
    // N/A

    // START Tiltak

    @JvmStatic
    fun fromMeasureToPlan(case: CaseEntry) {
    }

    @JvmStatic
    fun fromMeasureToDecision(case: CaseEntry) {
    }

    @JvmStatic
    fun fromMeasureToAfterCare(case: CaseEntry) {
    }

    // START Vedtak

    @JvmStatic
    fun fromDecisionToMeasure(case: CaseEntry) {
    }

    @JvmStatic
    fun fromDecisionToAnotherDecision(case: CaseEntry) {
    }

    @JvmStatic
    fun fromDecisionToAfterCare(case: CaseEntry) {
    }

    // START Ettervern

    @JvmStatic
    fun fromAfterCareToMeasure(case: CaseEntry) {
    }

    @JvmStatic
    fun fromAfterCareToDecision(case: CaseEntry) {
    }

    private val newStateFuncMap = mapOf(
        Pair(
            Pair(BarnevernState.MESSAGE, BarnevernState.INVESTIGATION),
            ::fromMessageToInvestigation
        ),
        Pair(
            Pair(BarnevernState.MESSAGE, BarnevernState.DECISION),
            ::fromMessageToDecision
        ),

        Pair(
            Pair(BarnevernState.INVESTIGATION, BarnevernState.MEASURE),
            ::fromInvestigationToMeasure
        ),
        Pair(
            Pair(BarnevernState.INVESTIGATION, BarnevernState.DECISION),
            ::fromInvestigationToDecision
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
}