package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SakType

class HasContent : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 06: Har meldinger, planer eller tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        // TODO legg til sjekk på Tiltak og Plan
        return if (context.rootObject.sak.virksomhet.any {
                it.melding?.any() == true
            }) {
            null
        } else {
            createSingleReportEntryList(
                "Individet har ingen meldinger, planer eller tiltak i løpet av året",
                context.rootObject.sak.id
            )
        }
    }
}