package no.ssb.barn.validation2

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.framework.ValidatorContract
import no.ssb.barn.report.ValidationReport
import no.ssb.barn.validation2.rule.XsdRule

class VersionOneValidator : ValidatorContract {

    private val rules = listOf(
        XsdRule(xsdResourceName = "Barnevern.xsd")
    )

    override fun validate(context: ValidationContext): ValidationReport =
        ValidationReport(
            "~journalId~",
            "~individualId~",
            rules.mapNotNull { it.validate(context) }
                .flatten()
        )
}