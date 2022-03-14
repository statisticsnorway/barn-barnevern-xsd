package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MessageProcessingTimeOverdue : AbstractRule(
    WarningLevel.WARNING,
    "Melding Kontroll 3: Fristoverskridelse på behandlingstid",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.melding.asSequence()
            .filter { melding ->
                val conclusion = melding.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && conclusion.sluttDato!!.isAfter(melding.startDato.plusDays(7))
            }
            .map {
                createReportEntry(
                    "Fristoverskridelse på behandlingstid for melding,"
                            + " (${it.startDato} -> ${it.konklusjon!!.sluttDato})",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}