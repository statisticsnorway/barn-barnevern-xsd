package no.ssb.barn.validation2

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.framework.ValidatorContract
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.ValidationReport
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation2.rule.XsdRule

class VersionOneValidator : ValidatorContract {

    private val rules = listOf(
        XsdRule(xsdResourceName = "Barnevern.xsd")
    )

    override fun validate(context: ValidationContext): ValidationReport {

        val reportEntries = ArrayList<ReportEntry>()

        rules.forEach { rule ->

            // run current validation
            val currReportEntries = rule.validate(context)

            if (currReportEntries != null) {
                reportEntries.addAll(currReportEntries as Collection<ReportEntry>)

                // if any FATAL warnings, like XSD validation error, skip further validations
                if (currReportEntries.any { entry -> entry.warningLevel == WarningLevel.FATAL }) {
                    return@forEach
                }
            }
        }

        return ValidationReport(
            "~journalId~",
            "~individualId~",
            reportEntries
        )
    }
}