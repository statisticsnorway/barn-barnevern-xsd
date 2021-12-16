package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class LegalBasisValidCode : AbstractRule(
    WarningLevel.ERROR,
    "Lovhjemmel Kontroll 4: Lovhjemmel",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val legalBasis = tiltak.lovhjemmel
                legalBasis != null
                        &&
                        (legalBasis.paragraf.startsWith("0")
                                || legalBasis.kapittel.startsWith("0"))
            }
            .map {
                createReportEntry(
                    "Tiltak ($it.id). Kapittel"
                            + " (${it.lovhjemmel!!.kapittel}) eller paragraf"
                            + " (${it.lovhjemmel!!.paragraf}) er rapportert med"
                            + " den ugyldige koden 0",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}