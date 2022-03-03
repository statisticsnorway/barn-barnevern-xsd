package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureAgeAboveElevenAndInSfo : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 6: Barnet er over 11 år og i SFO",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)

        if (age > 11) {
            return context.rootObject.sak.tiltak.asSequence()
                .filter { tiltak ->
                    tiltak.kategori.kode == "4.2"
                }
                .map {
                    createReportEntry(
                        "Barnet er over 11 år og i SFO",
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