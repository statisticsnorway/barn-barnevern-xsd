package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.SaksinnholdType
import java.time.LocalDate

class TestDataGenerator(xmlResourceName: String) {

    constructor() : this("/initial_mutation.xml")

    private val initialMutationXml: String = getResourceAsText(xmlResourceName)

    fun createInitialMutation(currentDate: LocalDate): BarnevernType {
        val instance = BarnevernConverter.unmarshallXml(initialMutationXml)
        val currentDateTime =
            currentDate.atStartOfDay().plusHours((6..20).random().toLong())
        var globalCompanyId: String

        with(instance) {
            datoUttrekk = currentDateTime
            fagsystem = RandomUtils.generateRandomFagsystemType()
            avgiver = RandomUtils.generateRandomAvgiverType()
            globalCompanyId = avgiver.organisasjonsnummer
        }

        with(instance.sak) {
            startDato = currentDateTime.toLocalDate()
            id = java.util.UUID.randomUUID()
            journalnummer =
                RandomUtils.generateRandomString(15)
            fodselsnummer = RandomUtils.generateRandomSSN(
                LocalDate.now().minusYears(20),
                LocalDate.now().minusYears(1)
            )
        }

        if (!instance.sak.virksomhet.any()) {
            return instance
        }

        with(instance.sak.virksomhet.first()) {
            startDato = currentDateTime.toLocalDate()
            organisasjonsnummer = globalCompanyId // TODO: Oslo

            if (!melding.any()) {
                return instance
            }

            with(melding.first()) {
                id = java.util.UUID.randomUUID()
                startDato = currentDateTime.toLocalDate()

                melder = mutableListOf(
                    MelderType(
                        MelderType.getRandomCode(
                            currentDateTime.toLocalDate()
                        )
                    )
                )

                saksinnhold = mutableListOf(
                    SaksinnholdType(
                        SaksinnholdType.getRandomCode(
                            currentDateTime.toLocalDate()
                        )
                    )
                )
            }
        }
        return instance
    }

    fun mutate(caseToMutate: CaseEntry) {

        // get a random transition based on existing state
        val transitionEntry = newStateFuncMap.entries.asSequence()
            .filter { it.key.first == caseToMutate.state }
            .toList()
            .ifEmpty { return } // if state == Plan, there is no transition
            .random()

        // run mutation on incoming instance
        transitionEntry.value(caseToMutate)

        // assign new state to case
        caseToMutate.state = transitionEntry.key.second
    }

    // START Melding

    fun fromMessageToInvestigation(case: CaseEntry) {
        println(case)
    }

    fun fromMessageToDecision(case: CaseEntry) {
    }


    // START Undersokelse

    fun fromInvestigationToMeasure(case: CaseEntry) {
    }

    fun fromInvestigationToDecision(case: CaseEntry) {
    }

    // START Plan
    // N/A

    // START Tiltak

    fun fromMeasureToPlan(case: CaseEntry) {
    }

    fun fromMeasureToDecision(case: CaseEntry) {
    }

    fun fromMeasureToAfterCare(case: CaseEntry) {
    }

    // START Vedtak

    fun fromDecisionToMeasure(case: CaseEntry) {
    }

    fun fromDecisionToAnotherDecision(case: CaseEntry) {
    }

    fun fromDecisionToAfterCare(case: CaseEntry) {
    }

    // START Ettervern

    fun fromAfterCareToMeasure(case: CaseEntry) {
    }

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
        ),
    )

    companion object {
        private fun getResourceAsText(path: String): String =
            TestDataGenerator::class.java.getResource(path)!!.readText()
    }
}