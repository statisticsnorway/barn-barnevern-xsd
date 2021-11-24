package no.ssb.barn.framework

import no.ssb.barn.xsd.BarnevernType

data class ValidationContext(
    val xml: String,
    val rootObject: BarnevernType?
) {
    constructor(xml: String) : this(xml, null)
}
