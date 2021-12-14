package no.ssb.barn.generator

import java.time.LocalDate
import java.util.*

class Simulation(
    startDate: LocalDate,
    private val endDate: LocalDate
) {
    private val caseList = mutableSetOf<CaseEntry>()
    private val testDataGenerator = TestDataGenerator()

    private var numberOfUpdatesForCurrentDay = 0
    private var numberOfNewCasesForCurrentDay = 0
    private var currentDate = startDate

    companion object {
        const val CHANGE_LIMIT_DAY_LOWER = 10
        const val CHANGE_LIMIT_DAY_UPPER = 30
    }

    fun run() = sequence {

        while (currentDate.isBefore(endDate.plusDays(1))) {

            val requiredNumberOfUpdatesForCurrentDay =
                (CHANGE_LIMIT_DAY_LOWER..CHANGE_LIMIT_DAY_UPPER).random()

            while (numberOfUpdatesForCurrentDay < requiredNumberOfUpdatesForCurrentDay + 1) {

                if (!mutableCasesExists() || shouldCreateNewCase()) {
                    val currentBarnevernType =
                        testDataGenerator.createInitialMutation()

                    val currentMutatedCase = CaseMutation(
                        barnevern = currentBarnevernType,
                        created = currentDate
                    )

                    caseList.add(CaseEntry(UUID.randomUUID(), currentMutatedCase))
                    numberOfNewCasesForCurrentDay++

                    yield(currentBarnevernType)
                } else {
                    // find a case to mutate
                    val currentCase = getRandomCaseToMutate()
                    val mostRecentMutation = currentCase.mutations.last()

                    val currentNewMutation = CaseMutation(
                        generation = mostRecentMutation.generation + 1,
                        isMutable = mostRecentMutation.generation < 4, // TODO: scaffolding for use during development
                        created = currentDate,
                        barnevern = testDataGenerator.mutate(mostRecentMutation.barnevern)
                    )

                    currentCase.mutations.add(currentNewMutation)
                    yield(currentNewMutation.barnevern)
                }

                numberOfUpdatesForCurrentDay++
            }

            currentDate = currentDate.plusDays(1)
            numberOfUpdatesForCurrentDay = 0
            numberOfNewCasesForCurrentDay = 0
        }
    }

    /**
     * Note: This function assumes 50/50 mix of new cases and existing cases
     */
    private fun shouldCreateNewCase(): Boolean {
        val weight =
            (1 - (numberOfNewCasesForCurrentDay.toDouble() / numberOfUpdatesForCurrentDay.toDouble())) * 100

        return (0..100).random() < weight
    }

    private fun getRandomCaseToMutate(): CaseEntry =
        caseList.asSequence()
            .filter {
                it.mutations.last().isMutable
                        && it.mutations.last().created.isBefore(currentDate)
            }
            .toList()
            .random()


    private fun mutableCasesExists(): Boolean =
        caseList.any {
            it.mutations.last().isMutable
                    && it.mutations.last().created.isBefore(currentDate)
        }
}