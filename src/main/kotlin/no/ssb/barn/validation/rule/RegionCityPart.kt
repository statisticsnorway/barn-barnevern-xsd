package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.generator.GeneratorConstants
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.AvgiverType

class RegionCityPart : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 09: Bydelsnummer",
    AvgiverType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (context.rootObject.avgiver.kommunenummer != GeneratorConstants.OSLO) {
            return null
        }

        return context.rootObject.sak.virksomhet.asSequence()
            .filter { it.bydelsnummer.isNullOrEmpty() || it.bydelsnavn.isNullOrEmpty() }
            .flatMap { virksomhet ->
                mapOf(
                    Pair(
                        "Filen mangler bydelsnummer.",
                        virksomhet.bydelsnummer
                    ),
                    Pair(
                        "Filen mangler bydelsnavn.",
                        virksomhet.bydelsnavn
                    )
                )
                    .filter { it.value.isNullOrEmpty() }
                    .map { createReportEntry(it.key) }
            }
            .toList()
            .ifEmpty { null }
    }
}