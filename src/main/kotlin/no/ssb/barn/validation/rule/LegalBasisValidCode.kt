package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType

class LegalBasisValidCode : AbstractRule(
    WarningLevel.ERROR,
    "Lovhjemmel Kontroll 4: Lovhjemmel",
    LovhjemmelType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.tiltak }
            .flatten()
            .filter { tiltak ->
                tiltak.lovhjemmel?.paragraf?.startsWith("0") == true
                        || tiltak.lovhjemmel?.kapittel?.startsWith("0") == true
            }
            .map {
                createReportEntry(
                    "Tiltak ($it.id). Kapittel"
                            + " (${it.lovhjemmel?.kapittel}) eller paragraf"
                            + " (${it.lovhjemmel?.paragraf}) er rapportert med"
                            + " den ugyldige koden 0",
                    it.id ?: "N/A"
                )
            }
            .toList()
            .ifEmpty { null }
    }
}