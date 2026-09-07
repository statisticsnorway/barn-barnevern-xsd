package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "HenvendelseKonklusjonType", propOrder = ["sluttdato", "kode"]
)
data class HenvendelseKonklusjonType(
    @field:XmlElement(name = "Sluttdato", required = true)
    @XmlSchemaType(name = "date")
    var sluttdato: LocalDate,

    @field:XmlElement(name = "Kode", required = true)
    var kode: String,
)