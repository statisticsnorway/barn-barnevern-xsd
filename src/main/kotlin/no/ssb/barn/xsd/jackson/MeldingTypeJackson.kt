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
    var id: UUID? = null,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: MeldingKonklusjonType? = null
) {
    @JacksonXmlProperty(localName = "Melder")
    @JacksonXmlElementWrapper(useWrapping = false)
    var melder: List<MelderType> = mutableListOf()
        set(value) {
            field = melder + value
        }

    @JacksonXmlProperty(localName = "Saksinnhold")
    @JacksonXmlElementWrapper(useWrapping = false)
    var saksinnhold: List<SaksinnholdType> = mutableListOf()
        set(value) {
            field = saksinnhold + value
        }
}
