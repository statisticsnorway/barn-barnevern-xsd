package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageContainsCaseContent : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 5: Kontroll av konkludert melding, saksinnhold"
) {
    private val codesThatRequiresCaseContent = listOf("1", "2")

    // TODO: This part is missing
    // if (forrigeTelleDato.getYear() < sluttDato.getYear()

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding ->
                val conclusion = melding.konklusjon
                melding.saksinnhold == null
                        && conclusion != null
                        && codesThatRequiresCaseContent.contains(conclusion.kode)
            }
            .map {
                createReportEntry("Melding (${it.id}). Konkludert melding mangler saksinnhold.")
            }
            .toList()
            .ifEmpty { null }
}