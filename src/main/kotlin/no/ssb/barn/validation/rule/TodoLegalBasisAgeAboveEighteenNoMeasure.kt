package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType

class TodoLegalBasisAgeAboveEighteenNoMeasure : AbstractRule(
    WarningLevel.ERROR,
    "Lovhjemmel Kontroll 3: Individet er over 18 år og har omsorgstiltak",
    LovhjemmelType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}