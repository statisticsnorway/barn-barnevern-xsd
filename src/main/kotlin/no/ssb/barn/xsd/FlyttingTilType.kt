package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FlyttingTilType",
    propOrder = ["kode", "presisering"]
)
data class FlyttingTilType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = MelderType.getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null

) {
    companion object {
        private val validFrom = LocalDate.of(2022, 1, 1)
        private val codeList = listOf(
            CodeListItem("1", "Fosterhjem i familie og nære nettverk", validFrom),
            CodeListItem("2", "Fosterhjem utenfor familie og nære nettverk", validFrom),
            CodeListItem("3", "Beredskapshjem", validFrom),
            CodeListItem("4", "Barnevernsinstitusjon", validFrom),
            CodeListItem("5", "Bolig med oppfølging", validFrom),
            CodeListItem("6", "Foreldre", validFrom),
            CodeListItem("7", "Egen bolig uten oppfølging", validFrom),
            CodeListItem("8", "Annet bosted (spesifiser)", validFrom),
            CodeListItem("9", "Adresseendring", validFrom)
        )


        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)
    }
}


