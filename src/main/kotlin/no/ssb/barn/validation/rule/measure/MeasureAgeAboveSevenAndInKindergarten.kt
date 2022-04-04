package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureAgeAboveSevenAndInKindergarten : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 5: Kontroll om barnet er over 7 år og er i barnehage",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)

        if (age > 7) {
            return context.rootObject.sak.tiltak.asSequence()
                .filter { tiltak ->
                    tiltak.kategori.kode == "4.1"
                }
                .map {
                    createReportEntry(
                        "Barnet er over 7 år og i barnehage.",
                        it.id
                    )
                }
                .toList()
                .ifEmpty { null }
        } else {
            return null
        }
    }
}