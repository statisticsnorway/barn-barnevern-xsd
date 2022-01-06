package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SakType

class HasContent : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 06: Har meldinger, planer eller tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .filter { virksomhet ->
                !(virksomhet.melding.any() || virksomhet.tiltak.any() || virksomhet.plan.any())
            }
            .map {
                createReportEntry(
                    "Individet har ingen meldinger, planer eller"
                            + " tiltak i løpet av året.",
                    context.rootObject.sak.id
                )
            }
            .toList()
            .ifEmpty { null }
}