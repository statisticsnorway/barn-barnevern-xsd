package no.ssb.barn.validation2

import com.google.common.collect.ImmutableList
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.framework.ValidatorContract
import no.ssb.barn.report.ValidationReport

class VersionOneValidator : ValidatorContract {

    private val rules = ImmutableList.of(
        XsdRule(xsdResourceName = "Barnevern.xsd"))

    override fun validate(context: ValidationContext): ValidationReport {
        return ValidationReport(
            "~journalId~",
            "~individualId~",
            rules.mapNotNull { it.validate(context) }
        )
    }
}