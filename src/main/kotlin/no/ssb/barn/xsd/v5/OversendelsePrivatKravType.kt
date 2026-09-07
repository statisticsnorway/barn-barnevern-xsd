package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OversendelsePrivatKravType", propOrder = ["id", "datoKravMottatt", "konklusjon", "datoOversendtFylkesnemnd"]
)
data class OversendelsePrivatKravType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "DatoKravMottatt", required = true)
    @XmlSchemaType(name = "date")
    val datoKravMottatt: LocalDate,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: String? = null,

    @field:XmlElement(name = "DatoOversendtFylkesnemnd")
    @XmlSchemaType(name = "date")
    val datoOversendtFylkesnemnd: LocalDate? = null
)