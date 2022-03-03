package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.SakType

class CaseHasContent : AbstractRule(
    WarningLevel.ERROR,
    "Sak Kontroll 06: Har meldinger, planer eller tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        if (context.rootObject.sak.melding.any()
            || context.rootObject.sak.tiltak.any()
            || context.rootObject.sak.plan.any())
            null
        else
            createSingleReportEntryList(
                "Klienten har ingen meldinger, planer eller tiltak.",
                context.rootObject.sak.id
            )
}