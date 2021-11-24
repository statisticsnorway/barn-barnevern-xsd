package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class DuplicateSocialSecurityId : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 04: Dublett på fødselsnummer"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented. This rule is to be deleted because it is impossible to have duplicates of social security numbers when only one exists")
    }
}