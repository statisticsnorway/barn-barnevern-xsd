package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SamvaerEtterOmsorgsovertakelseType", propOrder = ["hyppighetVedtak", "gjennomforing"
    ]
)
data class SamvaerEtterOmsorgsovertakelseType(
    @field:XmlElement(name = "HyppighetVedtak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val hyppighetVedtak: MutableList<SamvaerHyppighetVedtakType> = mutableListOf(),

    @field:XmlElement(name = "Gjennomforing")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val gjennomforing: MutableList<SamvaerGjennomforingType> = mutableListOf()
)