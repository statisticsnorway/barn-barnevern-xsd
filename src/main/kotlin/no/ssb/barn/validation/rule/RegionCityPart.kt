package no.ssb.barn.validation.rule

import no.ssb.barn.generator.GeneratorConstants
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.AvgiverType

/**
 * TODO: Investigate. This rule addresses the same as BusinessUrbanDistrictNumberAndName,
 * but is more forgiving. Delete candidate.
 */
class RegionCityPart : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 09: Bydelsnummer",
    AvgiverType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (context.rootObject.avgiver.kommunenummer != GeneratorConstants.OSLO) {
            return null
        }

        val result = mutableListOf<ReportEntry>()

        if (context.rootObject.avgiver.bydelsnummer.isNullOrEmpty())
            result.add(createReportEntry("Filen mangler bydelsnummer."))

        if (context.rootObject.avgiver.bydelsnavn.isNullOrEmpty())
            result.add(createReportEntry("Filen mangler bydelsnavn."))

        return result.ifEmpty { null }
    }
}