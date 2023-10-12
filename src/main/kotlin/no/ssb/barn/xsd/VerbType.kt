package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlEnumValue

enum class VerbType {
    @field:XmlEnumValue("POST")
    POST,

    @field:XmlEnumValue("PATCH")
    PATCH,

    @field:XmlEnumValue("PUT")
    PUT,

    @field:XmlEnumValue("DELETE")
    DELETE
}