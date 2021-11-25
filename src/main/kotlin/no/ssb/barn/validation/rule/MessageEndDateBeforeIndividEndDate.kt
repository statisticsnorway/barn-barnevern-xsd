package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageEndDateBeforeIndividEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2c: Sluttdato mot individets sluttdato"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individEndDate = sak.sluttDato ?: return null

        return sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding ->
                val conclusion = melding.konklusjon
                conclusion != null
                        && conclusion.sluttDato > individEndDate
            }
            .map {
                createReportEntry(
                    """Meldingens sluttdato (${it.konklusjon?.sluttDato}) er etter individets 
                            |sluttdato ($individEndDate)""".trimMargin()
                )
            }
            .toList()
            .ifEmpty { null }
    }
}
