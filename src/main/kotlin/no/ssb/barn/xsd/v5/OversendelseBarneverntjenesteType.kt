package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OversendelseBarneverntjenesteType",
    propOrder = ["id", "migrertId", "datoOversendtFylkesnemnd", "erSlettet", "lovhjemmel", "jmfrLovhjemmel"]
)
data class OversendelseBarneverntjenesteType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "DatoOversendtFylkesnemnd", required = true)
    @XmlSchemaType(name = "date")
    val datoOversendtFylkesnemnd: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    val lovhjemmel: LovhjemmelType,

    @field:XmlElement(name = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf()
)