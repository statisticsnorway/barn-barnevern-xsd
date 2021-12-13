package no.ssb.barn.generator

import java.util.*

class CaseEntry(val id: UUID, initialMutation: CaseMutation) {

    val mutations = mutableListOf(initialMutation)
}
