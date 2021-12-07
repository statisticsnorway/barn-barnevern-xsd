package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType

class TestDataGenerator {

    fun getTestData(numberOfItems: Int): Sequence<BarnevernType> =
        (1..numberOfItems).asSequence()
            .map { createInitialMutation() }
            .flatMap { createMutations(it) }

    private fun createInitialMutation(): BarnevernType {
        TODO("Implement this")

        // 1. Populate an instance of BarnevernType with data from XML

        // 2. Randomize data
    }

    private fun createMutations(incoming: BarnevernType): List<BarnevernType> {
        val mutations = mutableListOf<BarnevernType>()
        var currentMutation: BarnevernType? = incoming

        while (currentMutation != null) {
            mutations.add(currentMutation)
            currentMutation = mutate(currentMutation)
        }
        return mutations
    }

    private fun mutate(incoming: BarnevernType): BarnevernType? {
        TODO("Implement this")

        // 1. Check state of incoming. If impossible to mutate, return null

        // 2. Get list of available next states for incoming based on state
        //    for incoming.

        // 3. Select a random one, mutate incoming and return mutated version.
    }
}