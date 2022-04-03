package no.ssb.barn.xsd.jackson

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.MeldingKonklusjonType
import no.ssb.barn.xsd.SaksinnholdType
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MeldingType",
    propOrder = ["id", "migrertId", "startDato", "melder", "saksinnhold", "konklusjon"]
)
data class MeldingTypeJackson(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String?,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: MeldingKonklusjonType?,

    @JacksonXmlProperty(localName = "Melder")
    @JacksonXmlElementWrapper(useWrapping = false)
    val melder: Collection<MelderType> = listOf(),

    @JacksonXmlProperty(localName = "Saksinnhold")
    @JacksonXmlElementWrapper(useWrapping = false)
    val saksinnhold: Collection<SaksinnholdType> = listOf()
)

