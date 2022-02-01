package no.ssb.barn.validation

import no.ssb.barn.report.ValidationReport
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.rule.XsdRule

class VersionOneXsdValidator : ValidatorContract {
    private val rules = listOf(
        XsdRule(xsdResourceName = "Barnevern.xsd")
    )
    override fun validate(context: ValidationContext): ValidationReport {
        val reportEntries = rules.asSequence()
            .mapNotNull { it.validate(context) }
            .flatten()
            .toList()

        return ValidationReport(
            messageId = context.messageId,
            reportEntries = reportEntries,
            severity = reportEntries.asSequence()
                .map { it.warningLevel }
                .maxByOrNull { it.ordinal } ?: WarningLevel.OK
        )
    }
}