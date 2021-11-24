package no.ssb.barn.validation

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.framework.ValidatorContract
import no.ssb.barn.report.ValidationReport
import no.ssb.barn.validation.rule.*

class VersionOneValidator : ValidatorContract {

    private val preCheckRules = listOf(
        XsdRule(xsdResourceName = "Barnevern.xsd")
    )

    private val rules = listOf(
        AgeAboveEighteen(),
        AgeAboveTwentyFive(),
        EndDateAfterStartDate(),
        EndDateRequiredWhenCaseClosed(),
        HasContent(),
        MessageCaseContentContainsClarification(),
        MessageContainsCaseContent(),
        MessageContainsReporters(),
        MessageEndDateAfterStartDate(),
        MessageEndDateBeforeIndividEndDate()
    )

    override fun validate(context: ValidationContext): ValidationReport =
        ValidationReport(
            "~journalId~",
            "~individualId~",
            preCheckRules.asSequence()
                .mapNotNull { it.validate(context) }
                .flatten()
                .toList()
                .ifEmpty {
                    rules.asSequence()
                        .mapNotNull { it.validate(context) }
                        .flatten()
                        .toList()
                }
        )
}