package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageProcessingTimeOverdue : AbstractRule(
    WarningLevel.WARNING,
    "Melding Kontroll 3: Behandlingstid av melding",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.melding }
            .filter { melding ->
                melding.konklusjon != null
                        && melding.startDato.plusDays(7)
                    .isBefore(
                        melding.konklusjon?.sluttDato
                    )
            }
            .map {
                createReportEntry(
                    "Melding (${it.id})."
                            + " Fristoverskridelse på behandlingstid for melding,"
                            + " (${it.startDato} -> ${it.konklusjon!!.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}