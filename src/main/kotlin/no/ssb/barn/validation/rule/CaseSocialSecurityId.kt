package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.validateFNR
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.SharedValidationConstants
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.SakType

class CaseSocialSecurityId : AbstractRule(
    WarningLevel.WARNING,
    SharedValidationConstants.SOCIAL_SECURITY_RULE_NAME,
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val fodselsnummer = context.rootObject.sak.fodselsnummer ?: ""

        return if (!validateFNR(fodselsnummer)
        ) {
            createSingleReportEntryList(
                "Saken har ufullstendig fødselsnummer. Korriger fødselsnummer.",
                context.rootObject.sak.id
            )
        } else {
            null
        }
    }
}