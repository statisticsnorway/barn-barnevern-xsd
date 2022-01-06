package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureAgeAboveElevenAndInSfo : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 6: Barn over 11 år og i SFO",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)

        if (age > 11) {
            return context.rootObject.sak.virksomhet.asSequence()
                .flatMap { virksomhet -> virksomhet.tiltak }
                .filter { tiltak ->
                    tiltak.kategori?.kode == "4.2"
                }
                .map {
                    createReportEntry(
                        "Tiltak (${it.id}). Barnet er over 11 år og i"
                                + " SFO. Barnets alder er $age år.",
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