package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class DuplicateJournalId : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 05: Dublett på journalnummer"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented. This rule is to be deleted because it is impossible to have duplicates of journal-ids when only one exists")
    }
}