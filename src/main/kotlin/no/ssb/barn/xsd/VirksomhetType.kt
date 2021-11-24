package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VirksomhetType",
    propOrder = [
        "startDato", "sluttDato", "organisasjonsnummer", "bydelsnummer", "bydelsnavn", "distriktsnummer",
        "melding"/*, "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste", "flytting",
        "relasjon"*/]
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
    var melding: MutableList<MeldingType>? = null,

// TODO

//        @field:XmlElement(name = "Undersokelse")
//        var undersokelse: List<UndersokelseType>? = null,
//
//        @field:XmlElement(name = "Plan")
//        var plan: List<PlanType>? = null,
//
//        @field:XmlElement(name = "Tiltak")
//        var tiltak: List<TiltakType>? = null,
//
//        @field:XmlElement(name = "Vedtak")
//        var vedtak: List<VedtakType>? = null,
//
//        @field:XmlElement(name = "Ettervern")
//        var ettervern: List<EttervernType>? = null,
//
//        @field:XmlElement(name = "OversendelseBarneverntjeneste")
//        var oversendelseBarneverntjeneste: List<OversendelseBarneverntjenesteType>? = null,
//
//        @field:XmlElement(name = "Flytting")
//        var flytting: List<FlyttingType>? = null,
//
//        @field:XmlElement(name = "Relasjon")
//        var relasjon: List<RelasjonType>? = null
)