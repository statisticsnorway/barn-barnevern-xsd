package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate
import java.util.*

class Simulation(
    private val minUpdatesPerDay: Int,
    private val maxUpdatesPerDay: Int
) {
    constructor() : this(MIN_UPDATES_PER_DAY, MAX_UPDATES_PER_DAY)

    companion object {
        const val MIN_UPDATES_PER_DAY = 10
        const val MAX_UPDATES_PER_DAY = 20

        // flip a coin
        private fun shouldCreateNewCase(): Boolean = (0..1).random() == 1
    }

    private val caseList = mutableSetOf<CaseEntry>()
    private val initialMutationProvider = InitialMutationProvider()

    fun run(daysBack: Int): Sequence<BarnevernType> =
        (-daysBack until 0).asSequence()
            .map { LocalDate.now().plusDays(it.toLong()) }
            .flatMap { produceCasesForCurrentDate(it) }

    private fun produceCasesForCurrentDate(currentDate: LocalDate): Sequence<BarnevernType> =
        (1..(minUpdatesPerDay..maxUpdatesPerDay).random()).asSequence()
            .map { getOrCreateCurrentCase(currentDate).barnevern }

    private fun getOrCreateCurrentCase(currentDate: LocalDate): CaseEntry =
        if (!mutableCasesExists(currentDate) || shouldCreateNewCase()) {
            initialMutationProvider.createInitialMutation(currentDate)
                .let {
                    val currentCaseEntry = CaseEntry(
                        UUID.randomUUID(),
                        it,
                        currentDate
                    )

                    caseList.add(currentCaseEntry)
                    return@let currentCaseEntry
                }
        } else {
            getRandomCaseToMutate(currentDate)
                .also {
                    CaseMutator.mutate(it)

                    with(it) {
                        generation++
                        updated = currentDate
                    }

                    if (it.generation > 4) { // replace this with logic later
                        caseList.remove(it)
                    }
                }
        }

    private fun getRandomCaseToMutate(currentDate: LocalDate): CaseEntry =
        caseList.asSequence()
            .filter {
                it.updated.isBefore(currentDate)
            }
            .toList()
            .random()

    private fun mutableCasesExists(currentDate: LocalDate): Boolean =
        caseList.any {
            it.updated.isBefore(currentDate)
        }
}