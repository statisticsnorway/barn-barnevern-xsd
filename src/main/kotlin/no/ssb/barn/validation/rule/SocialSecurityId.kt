package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.Companion.validateSSN

class SocialSecurityId : AbstractRule(
    WarningLevel.WARNING,
    "Individ Kontroll 11: Fødselsnummer"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val fodselsnummer = context.rootObject.sak.fodselsnummer

        return if (fodselsnummer == null
            || fodselsnummer.isEmpty()
            || fodselsnummer.endsWith("55555")
            || !validateSSN(fodselsnummer)
        ) {
            createSingleReportEntryList(
                "Individet har ufullstendig fødselsnummer. Korriger fødselsnummer."
            )
        } else {
            null
        }
    }
}