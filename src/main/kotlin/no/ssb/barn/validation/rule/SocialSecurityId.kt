package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.validateSSN
import no.ssb.barn.xsd.SakType

class SocialSecurityId : AbstractRule(
    WarningLevel.WARNING,
    "Individ Kontroll 11: Fødselsnummer",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val fodselsnummer = context.rootObject.sak.fodselsnummer

        return if (fodselsnummer == null
            || fodselsnummer.isEmpty()
            || fodselsnummer.endsWith("55555")
            || !validateSSN(fodselsnummer)
        ) {
            createSingleReportEntryList(
                "Individet har ufullstendig fødselsnummer. Korriger fødselsnummer.",
                context.rootObject.sak.id
            )
        } else {
            null
        }
    }
}