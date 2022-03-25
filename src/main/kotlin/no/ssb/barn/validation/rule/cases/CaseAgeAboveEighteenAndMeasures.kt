package no.ssb.barn.validation.rule.cases

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.getAge
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.SakType
import java.util.*

class CaseAgeAboveEighteenAndMeasures : AbstractRule(
    WarningLevel.ERROR,
    "Sak Kontroll 08: Alder i forhold til tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        if (getAge(context.rootObject.sak.fodselsnummer) < 18
            || context.rootObject.sak.tiltak.any())
            null
        else
            createSingleReportEntryList(
                "Klienten er over 18 år og skal dermed ha tiltak",
                context.rootObject.sak.id as UUID
            )
}