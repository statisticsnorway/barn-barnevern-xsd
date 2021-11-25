package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class RegionCityPart : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 09: Bydelsnummer"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (context.rootObject.avgiver.kommunenummer != "0301") {
            return null
        }

        return mapOf(
            Pair(
                "Filen mangler bydelsnummer.",
                context.rootObject.avgiver.bydelsnummer
            ),
            Pair(
                "Filen mangler bydelsnavn.",
                context.rootObject.avgiver.bydelsnavn
            )
        ).asSequence()
            .filter { it.value.isNullOrEmpty() }
            .map { createReportEntry(it.key) }
            .toList()
            .ifEmpty { null }
    }
}