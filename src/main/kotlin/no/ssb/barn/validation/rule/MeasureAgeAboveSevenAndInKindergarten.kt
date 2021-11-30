package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.xsd.TiltakType

class MeasureAgeAboveSevenAndInKindergarten : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 5: Barn over 7 år og i barnehage",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)

        if (age > 7) {
            return context.rootObject.sak.virksomhet.asSequence()
                .mapNotNull { virksomhet -> virksomhet.tiltak }
                .flatten()
                .filter { tiltak ->
                    tiltak.kategori?.kode == "4.1"
                }
                .map {
                    createReportEntry(
                        "Tiltak ($it.id). Barnet er over 7 år og i"
                                + " barnehage. Barnets alder er $age år.",
                        it.id ?: "N/A"
                    )
                }
                .toList()
                .ifEmpty { null }
        } else {
            return null
        }
    }
}