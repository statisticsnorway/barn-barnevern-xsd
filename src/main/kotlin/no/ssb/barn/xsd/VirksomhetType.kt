package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirksomhetType", propOrder = [
        "startDato", "sluttDato", "organisasjonsnummer", "bydelsnummer", "bydelsnavn", "distriktsnummer",
        "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste", "flytting",
        "relasjon"])
data class VirksomhetType(
        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlAttribute(name = "SluttDato")
        @XmlSchemaType(name = "date")
        var sluttDato: XMLGregorianCalendar? = null,

        @XmlAttribute(name = "Organisasjonsnummer", required = true)
        var organisasjonsnummer: String,

        @XmlAttribute(name = "Bydelsnummer")
        var bydelsnummer: String? = null,

        @XmlAttribute(name = "Bydelsnavn")
        var bydelsnavn: String? = null,

        @XmlAttribute(name = "Distriktsnummer")
        var distriktsnummer: String? = null,

        @XmlElement(name = "Melding")
        var melding: List<MeldingType?>? = null,

        @XmlElement(name = "Undersokelse")
        var undersokelse: List<UndersokelseType>? = null,

        @XmlElement(name = "Plan")
        var plan: List<PlanType>? = null,

        @XmlElement(name = "Tiltak")
        var tiltak: List<TiltakType>? = null,

        @XmlElement(name = "Vedtak")
        var vedtak: List<VedtakType>? = null,

        @XmlElement(name = "Ettervern")
        var ettervern: List<EttervernType>? = null,

        @XmlElement(name = "OversendelseBarneverntjeneste")
        var oversendelseBarneverntjeneste: List<OversendelseBarneverntjenesteType>? = null,

        @XmlElement(name = "Flytting")
        var flytting: List<FlyttingType>? = null,

        @XmlElement(name = "Relasjon")
        var relasjon: List<RelasjonType>? = null
)
