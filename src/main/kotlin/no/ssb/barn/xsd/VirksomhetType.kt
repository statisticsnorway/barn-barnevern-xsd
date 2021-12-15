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
    var melding: MutableList<MeldingType> = mutableListOf(),

    @field:XmlElement(name = "Undersokelse")
    var undersokelse: MutableList<UndersokelseType> = mutableListOf(),

    @field:XmlElement(name = "Plan")
    var plan: MutableList<PlanType> = mutableListOf(),

    @field:XmlElement(name = "Tiltak")
    var tiltak: MutableList<TiltakType> = mutableListOf(),

    @field:XmlElement(name = "Vedtak")
    var vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:XmlElement(name = "Ettervern")
    var ettervern: MutableList<EttervernType> = mutableListOf(),

    @field:XmlElement(name = "OversendelseBarneverntjeneste")
    var oversendelseBarneverntjeneste: MutableList<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:XmlElement(name = "Flytting")
    var flytting: MutableList<FlyttingType> = mutableListOf(),

    @field:XmlElement(name = "Relasjon")
    var relasjon: MutableList<RelasjonType> = mutableListOf()
)