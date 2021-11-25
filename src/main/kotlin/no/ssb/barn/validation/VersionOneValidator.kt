package no.ssb.barn.validation

import no.ssb.barn.deserialize.BarnevernDeserializer
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
        MessageEndDateBeforeIndividEndDate(),
        MessageReporterContainsClarification(),
        MessageStartDateAfterOrEqualIndividStartDate(),
        RegionCityPart(),
        SocialSecurityId(),
        SocialSecurityIdAndDuf()
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
                    // this is not a great solution, fix me
                    val innerContext = ValidationContext(
                        context.xml,
                        BarnevernDeserializer.unmarshallXml(context.xml)
                    )

                    rules.asSequence()
                        .mapNotNull { it.validate(innerContext) }
                        .flatten()
                        .toList()
                }
        )
}