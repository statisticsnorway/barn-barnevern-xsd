package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MessageMissingCaseContent : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 5: Kontroll av konkludert melding, saksinnhold",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.melding.asSequence()
            .filter { melding ->
                val conclusion = melding.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && !melding.saksinnhold.any()
            }
            .map {
                createReportEntry(
                    "Melding (${it.id}). Konkludert melding mangler saksinnhold.",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}