package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.getAge
import no.ssb.barn.xsd.SakType

class CaseAgeAboveTwentyFive : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 07: Klient over 25 år avsluttes",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = getAge(context.rootObject.sak.fodselsnummer)

        return if (age < 25) {
            null
        } else {
            createSingleReportEntryList(
                "Individet er $age år og skal avsluttes som klient",
                context.rootObject.sak.id
            )
        }
    }
}