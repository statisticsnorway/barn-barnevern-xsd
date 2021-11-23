package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeldingType",
    propOrder = ["id", "migrertId", "startDato", "melder", "saksinnhold", "konklusjon"],
    factoryMethod = "createMeldingType")
data class MeldingType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlElement(name = "Melder")
    var melder: MutableList<MelderType?>? = null,

    @field:XmlElement(name = "Saksinnhold")
    var saksinnhold: MutableList<SaksinnholdType>? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: MeldingKonklusjonType? = null
){
    companion object {
        @JvmStatic
        fun createMeldingType(): MeldingType {
            return MeldingType(
                RandomGenerator.generateRandomString(10),
                null,
                LocalDate.now(),
                MutableList(1) { MelderType.createMelderType() },
                MutableList(1) { SaksinnholdType.createSaksinnholdType() },
                null
            )
        }
    }
}
