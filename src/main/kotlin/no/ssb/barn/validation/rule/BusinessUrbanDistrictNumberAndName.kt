package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VirksomhetType

class BusinessUrbanDistrictNumberAndName : AbstractRule(
    WarningLevel.ERROR,
    "Virksomhet Kontroll 3: Bydelsnummer og bydelsnavn",
    VirksomhetType::class.java.simpleName
) {
    private val businessIdList = listOf(
        "958935420", // Oslo
        "964338531", // Bergen
        "942110464", // Trondheim
        "964965226"  // Stavanger
    )

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .filter { virksomhet ->
                with(virksomhet) {
                    return@filter organisasjonsnummer in businessIdList
                            && (bydelsnummer.isNullOrBlank() || bydelsnavn.isNullOrBlank())
                }
            }
            .map {
                createReportEntry(
                    "Virksomhetens Bydelsnummer og Bydelsnavn skal være utfylt",
                    context.rootObject.sak.id
                )
            }
            .toList()
            .ifEmpty { null }
}