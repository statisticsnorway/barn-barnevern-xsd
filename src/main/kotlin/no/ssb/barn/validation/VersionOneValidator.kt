package no.ssb.barn.validation

import no.ssb.barn.converter.BarnevernConverter
import no.ssb.barn.report.ValidationReport
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.rule.*

class VersionOneValidator : ValidatorContract {

    private val preCheckRules = listOf(
        XsdRule(xsdResourceName = "Barnevern.xsd")
    )

    private val rules = listOf(
        BusinessEndDateAfterCaseEndDate(),
        BusinessEndDateAfterStartDate(),
        BusinessStartDateBeforeCaseStartDate(),
        BusinessUrbanDistrictNumberAndName(),
        CaseAgeAboveEighteenAndMeasures(),
        CaseAgeAboveTwentyFive(),
        CaseEndDateAfterStartDate(),
        CaseHasContent(),
        CaseSocialSecurityId(),
        CaseSocialSecurityIdAndDuf(),
        InvestigationDecisionClarificationRequired(),
        InvestigationDecisionRequired(),
        InvestigationDueDatePassedConclusionRequired(),
        InvestigationEndDateBeforeIndividEndDate(),
        InvestigationStartDateAfterIndividStartDate(),
        LegalBasisAgeAboveEighteenNoMeasure(),
        LegalBasisValidCode(),
        LegalBasisWithEndDateClarificationRequired(),
        MeasureAgeAboveElevenAndInSfo(),
        MeasureAgeAboveSevenAndInKindergarten(),
        MeasureClarificationRequired(),
        MeasureEndDateAfterStartDate(),
        MeasureEndDateBeforeIndividEndDate(),
        MeasureMultipleAllocationsWithinPeriod(),
        MeasureRepealClarificationRequired(),
        MeasureStartDateAfterIndividStartDate(),
        MessageCaseContentContainsClarification(),
        MessageContainsCaseContent(),
        MessageContainsReporters(),
        MessageStartDateAfterEndDate(),
        MessageEndDateBeforeIndividEndDate(),
        MessageProcessingTimeOverdue(),
        MessageReporterContainsClarification(),
        MessageStartDateAfterOrEqualIndividStartDate(),
        PlanEndDateAfterStartDate(),
        PlanEndDateBeforeIndividEndDate(),
        PlanStartDateAfterIndividStartDate(),
        RegionCityPart()
    )

    override fun validate(context: ValidationContext): ValidationReport {

        val reportEntries = preCheckRules.asSequence()
            .mapNotNull { it.validate(context) }
            .flatten()
            .toList()
            .ifEmpty {
                val innerContext = ValidationContext(
                    context.messageId,
                    context.xml,
                    BarnevernConverter.unmarshallXml(context.xml)
                )
                rules.asSequence()
                    .mapNotNull { it.validate(innerContext) }
                    .flatten()
                    .toList()
            }

        return ValidationReport(
            messageId = context.messageId,
            reportEntries = reportEntries,
            severity = reportEntries.asSequence()
                .map { it.warningLevel }
                .maxByOrNull { it.ordinal } ?: WarningLevel.OK
        )
    }
}