package no.ssb.barn.generator

import java.time.LocalDate
import java.util.*

class Simulation(
    startDate: LocalDate,
    private val endDate: LocalDate,
    private val minUpdatesPerDay: Int,
    private val maxUpdatesPerDay: Int
) {
    constructor(startDate: LocalDate, endDate: LocalDate)
            : this(startDate, endDate, MIN_UPDATES_PER_DAY, MAX_UPDATES_PER_DAY)

    companion object {
        const val MIN_UPDATES_PER_DAY = 10
        const val MAX_UPDATES_PER_DAY = 20
    }

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
                    testDataGenerator.createInitialMutation(currentDate)

                caseList.add(
                    CaseEntry(
                        UUID.randomUUID(),
                        currentBarnevernType,
                        currentDate
                    )
                )
                numberOfNewCasesForCurrentDay++
                yield(currentBarnevernType)
            } else {

                // find a case to mutate
                val currentCase = getRandomCaseToMutate()
                testDataGenerator.mutate(currentCase)

                if (currentCase.generation + 1 > 4) { // replace this with logic later
                    caseList.remove(currentCase)
                } else {
                    with(currentCase) {
                        generation++
                        updated = currentDate
                    }
                }
                yield(currentCase.barnevern)
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

    private fun shouldCreateNewCase(): Boolean {
        val weight =
            (1 - (numberOfNewCasesForCurrentDay.toDouble() / numberOfUpdatesForCurrentDay.toDouble())) * 100

        return (0..100).random() < weight
    }

    private fun getRandomCaseToMutate(): CaseEntry =
        caseList.asSequence()
            .filter {
                it.updated.isBefore(currentDate)
            }
            .toList()
            .random()

    private fun mutableCasesExists(): Boolean =
        caseList.any {
            it.updated.isBefore(currentDate)
        }
}