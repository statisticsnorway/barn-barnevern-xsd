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
    var melding: MutableList<MeldingType>? = MutableList(1) { MeldingType() },

    @field:XmlElement(name = "Undersokelse")
    var undersokelse: MutableList<UndersokelseType>? = MutableList(1) { UndersokelseType() },

    @field:XmlElement(name = "Plan")
    var plan: MutableList<PlanType>? = MutableList(1) { PlanType() },

    @field:XmlElement(name = "Tiltak")
    var tiltak: MutableList<TiltakType>? = MutableList(1) { TiltakType() },

    @field:XmlElement(name = "Vedtak")
    var vedtak: MutableList<VedtakType>? = MutableList(1) { VedtakType() },

    @field:XmlElement(name = "Ettervern")
    var ettervern: MutableList<EttervernType>? = MutableList(1) { EttervernType() },

    @field:XmlElement(name = "OversendelseBarneverntjeneste")
    var oversendelseBarneverntjeneste: List<OversendelseBarneverntjenesteType>? = MutableList(1) { OversendelseBarneverntjenesteType() },

    @field:XmlElement(name = "Flytting")
    var flytting: MutableList<FlyttingType>? = MutableList(1) { FlyttingType() },

    @field:XmlElement(name = "Relasjon")
    var relasjon: MutableList<RelasjonType>? = MutableList(1) { RelasjonType() }
)