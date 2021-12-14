package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VirksomhetType",
    propOrder = [
        "startDato", "sluttDato", "organisasjonsnummer", "bydelsnummer", "bydelsnavn", "distriktsnummer",
        "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste", "flytting",
        "relasjon"]
)
data class VirksomhetType(
    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = null,

    @field:XmlAttribute(name = "Organisasjonsnummer", required = true)
    var organisasjonsnummer: String = "999999999",

    @field:XmlAttribute(name = "Bydelsnummer")
    var bydelsnummer: String? = null,

    @field:XmlAttribute(name = "Bydelsnavn")
    var bydelsnavn: String? = null,

    @field:XmlAttribute(name = "Distriktsnummer")
    var distriktsnummer: String? = null,

    @field:XmlElement(name = "Melding")
    var melding: List<MeldingType> = mutableListOf(),

    @field:XmlElement(name = "Undersokelse")
    var undersokelse: List<UndersokelseType> = mutableListOf(),

    @field:XmlElement(name = "Plan")
    var plan: List<PlanType> = mutableListOf(),

    @field:XmlElement(name = "Tiltak")
    var tiltak: List<TiltakType> = mutableListOf(),

    @field:XmlElement(name = "Vedtak")
    var vedtak: List<VedtakType> = mutableListOf(),

    @field:XmlElement(name = "Ettervern")
    var ettervern: List<EttervernType> = mutableListOf(),

    @field:XmlElement(name = "OversendelseBarneverntjeneste")
    var oversendelseBarneverntjeneste: List<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:XmlElement(name = "Flytting")
    var flytting: List<FlyttingType> = mutableListOf(),

    @field:XmlElement(name = "Relasjon")
    var relasjon: List<RelasjonType> = mutableListOf()
)