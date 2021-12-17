package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate
import java.util.*

class Simulation(
    private val minUpdatesPerDay: Int,
    private val maxUpdatesPerDay: Int,
    private val maxMutations: Int
) {
    constructor() : this(
        DEFAULT_MIN_UPDATES_PER_DAY,
        DEFAULT_MAX_UPDATES_PER_DAY,
        DEFAULT_MAX_MUTATIONS
    )

    companion object {
        const val DEFAULT_MIN_UPDATES_PER_DAY = 10
        const val DEFAULT_MAX_UPDATES_PER_DAY = 20
        const val DEFAULT_MAX_MUTATIONS = 3

        // flip a coin
        private fun shouldCreateNewCase(): Boolean = (0..1).random() == 1
    }

    private val caseList = mutableSetOf<CaseEntry>()

    fun run(daysBack: Int): Sequence<BarnevernType> =
        (-daysBack until 0).asSequence()
            .map { LocalDate.now().plusDays(it.toLong()) }
            .flatMap { produceCasesForCurrentDate(it) }

    private fun produceCasesForCurrentDate(currentDate: LocalDate): Sequence<BarnevernType> =
        (1..(minUpdatesPerDay..maxUpdatesPerDay).random()).asSequence()
            .map { mutateOrCreateCaseEntry(currentDate) }

    private fun mutateOrCreateCaseEntry(currentDate: LocalDate): BarnevernType =
        if (mutableCaseCount(currentDate) < 1 || shouldCreateNewCase()) {
            InitialMutationProvider.createInitialMutation(currentDate)
                .also {
                    caseList.add(
                        CaseEntry(
                            UUID.randomUUID(),
                            it,
                            currentDate
                        )
                    )
                }
        } else {
            CaseMutator.mutate(getRandomCaseToMutate(currentDate))
                .run {
                    generation++
                    updated = currentDate

                    if (generation > maxMutations) {
                        caseList.remove(this)
                    }
                    barnevern
                }
        }

    private fun getRandomCaseToMutate(currentDate: LocalDate): CaseEntry =
        caseList.asSequence()
            .filter {
                it.updated.isBefore(currentDate)
            }
            .drop((0 until mutableCaseCount(currentDate)).random())
            .first()

    private fun mutableCaseCount(currentDate: LocalDate): Int =
        caseList.count {
            it.updated.isBefore(currentDate)
        }
}