package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SakType

class CaseHasContent : AbstractRule(
    WarningLevel.ERROR,
    "Har meldinger, planer eller tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .filter { virksomhet ->
                !(virksomhet.melding.any() || virksomhet.tiltak.any() || virksomhet.plan.any())
            }
            .map {
                createReportEntry(
                    "Klienten har ingen meldinger, planer eller tiltak.",
                    context.rootObject.sak.id
                )
            }
            .toList()
            .ifEmpty { null }
}