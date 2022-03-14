package no.ssb.barn.validation

import no.ssb.barn.xsd.BarnevernType
import java.util.*

data class ValidationContext(
    val messageId: String,
    val xml: String,
    val rootObject: BarnevernType
) {
    constructor(messageId: String, xml: String) : this(
        messageId,
        xml,
        BarnevernType(id = UUID.randomUUID())
    )
}
