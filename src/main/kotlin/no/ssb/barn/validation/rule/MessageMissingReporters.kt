package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MessageMissingReporters : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 4: Konkludert melding mangler melder",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.melding.asSequence()
            .filter { melding ->
                val conclusion = melding.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && !melding.melder.any()
            }
            .map {
                createReportEntry(
                    "Konkludert melding mangler melder(e)",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}