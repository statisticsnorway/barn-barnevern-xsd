package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageContainsReporters : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 4: Kontroll av konkludert melding, melder"
) {
    private val codesThatRequiresCaseContent = listOf("1", "2")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding ->
                val conclusion = melding.konklusjon
                melding.melder?.any() != true
                        && conclusion != null
                        && codesThatRequiresCaseContent.contains(conclusion.kode)
            }
            .map {
                createReportEntry("Melding (${it.id}). Konkludert melding mangler melder(e).")
            }
            .toList()
            .ifEmpty { null }
}