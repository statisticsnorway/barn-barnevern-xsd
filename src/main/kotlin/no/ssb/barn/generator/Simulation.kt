package no.ssb.barn.generator

import java.time.LocalDate
import java.util.*

class Simulation(
    startDate: LocalDate,
    private val endDate: LocalDate,
    private val minUpdatesPerDay: Int = 10,
    private val maxUpdatesPerDay: Int = 20,
) {
    private val caseList = mutableSetOf<CaseEntry>()
    private val testDataGenerator = TestDataGenerator()

    private var numberOfUpdatesForCurrentDay = 0
    private var numberOfNewCasesForCurrentDay = 0
    private var currentDate = startDate

    fun run() = sequence {

        var requiredNumberOfUpdatesForCurrentDay =
            (minUpdatesPerDay..maxUpdatesPerDay).random()

        while (currentDate.isBefore(endDate.plusDays(1))) {

            if (!mutableCasesExists() || shouldCreateNewCase()) {
                val currentBarnevernType =
                    testDataGenerator.createInitialMutation()

                caseList.add(
                    CaseEntry(
                        UUID.randomUUID(),
                        currentBarnevernType
                    )
                )
                numberOfNewCasesForCurrentDay++
                yield(currentBarnevernType)
            } else {

                // find a case to mutate
                val currentCase = getRandomCaseToMutate()
                val mutation =
                    testDataGenerator.mutate(currentCase.barnevern)

                if (currentCase.generation + 1 > 4) {
                    caseList.remove(currentCase)
                } else {
                    with(currentCase) {
                        barnevern = mutation
                        generation++
                        updated = currentDate
                    }
                }
                yield(mutation)
            }

            if (++numberOfUpdatesForCurrentDay > requiredNumberOfUpdatesForCurrentDay) {
                requiredNumberOfUpdatesForCurrentDay =
                    (minUpdatesPerDay..maxUpdatesPerDay).random()

                currentDate = currentDate.plusDays(1)
                numberOfUpdatesForCurrentDay = 0
                numberOfNewCasesForCurrentDay = 0
            }
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
                it.isMutable
                        && it.updated.isBefore(currentDate)
            }
            .toList()
            .random()


    private fun mutableCasesExists(): Boolean =
        caseList.any {
            it.isMutable
                    && it.updated.isBefore(currentDate)
        }
}