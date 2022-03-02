package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate
import java.time.LocalDateTime
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

    private val caseSet = mutableSetOf<CaseEntry>()

    fun run(daysBack: Int): Sequence<BarnevernType> =
        (-daysBack until 1).asSequence()
            .map { LocalDate.now().plusDays(it.toLong()) }
            .flatMap { produceCasesForCurrentDate(it) }

    private fun produceCasesForCurrentDate(currentDate: LocalDate): Sequence<BarnevernType> =
        (1..(minUpdatesPerDay..maxUpdatesPerDay).random()).asSequence()
            .map { mutateOrCreateCaseEntry(currentDate.atStartOfDay()) }

    private fun mutateOrCreateCaseEntry(currentDate: LocalDateTime): BarnevernType =
        if (mutableCaseCount(
                currentDate.toLocalDate(),
                caseSet
            ) < 1 || shouldCreateNewCase()
        ) {
            InitialMutationProvider.createInitialMutation(currentDate)
                .also {
                    caseSet.add(
                        CaseEntry(
                            UUID.randomUUID(),
                            it,
                            currentDate
                        )
                    )
                }
        } else {
            CaseMutator.mutate(getRandomCaseToMutate(currentDate, caseSet))
                .run {
                    generation++
                    updated = currentDate

                    if (generation > maxMutations
                        || state == BarnevernState.CASE_CLOSED
                    ) {
                        caseSet.remove(this)
                    }

                    // update case timestamp
                    barnevern.datoUttrekk =
                        currentDate.plusHours(
                            (8..20).random().toLong()
                        )

                    barnevern
                }
        }

    companion object {
        const val DEFAULT_MIN_UPDATES_PER_DAY = 10
        const val DEFAULT_MAX_UPDATES_PER_DAY = 20
        const val DEFAULT_MAX_MUTATIONS = 3

        // flip a coin
        private fun shouldCreateNewCase(): Boolean = (0..1).random() == 1

        @JvmStatic
        fun getRandomCaseToMutate(
            currentDate: LocalDateTime, caseSet: Set<CaseEntry>
        ): CaseEntry =
            caseSet
                .filter { it.updated.isBefore(currentDate) }
                .drop((0 until mutableCaseCount(currentDate.toLocalDate(), caseSet)).random())
                .first()

        @JvmStatic
        fun mutableCaseCount(
            currentDate: LocalDate,
            caseSet: Set<CaseEntry>
        ): Int =
            caseSet.count { it.updated.toLocalDate().isBefore(currentDate) }
    }
}