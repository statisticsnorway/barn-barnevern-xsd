package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureLegalBasisValidCode : AbstractRule(
    WarningLevel.ERROR,
    "Lovhjemmel Kontroll 4: Lovhjemmel",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                (tiltak.lovhjemmel.paragraf.startsWith("0")
                        || tiltak.lovhjemmel.kapittel.startsWith("0"))
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}). Kapittel"
                            + " (${it.lovhjemmel.kapittel}) eller paragraf"
                            + " (${it.lovhjemmel.paragraf}) er rapportert med"
                            + " den ugyldige koden 0",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}