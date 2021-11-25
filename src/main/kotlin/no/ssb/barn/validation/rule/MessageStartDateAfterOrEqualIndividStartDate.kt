package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageStartDateAfterOrEqualIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2e: Startdato mot individets startdato"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individStartDate = sak.startDato

        return sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding ->
                melding.startDato < individStartDate
            }
            .map {
                createReportEntry(
                    """Startdato (${it.startDato}) skal være lik eller etter 
                        |individets startdato ($individStartDate)""".trimMargin()
                )
            }
            .toList()
            .ifEmpty { null }
    }
}