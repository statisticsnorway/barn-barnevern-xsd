package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageStartDateAfterOrEqualIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2e: Startdato mot individets startdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individStartDate = sak.startDato

        return sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.melding }
            .filter { melding ->
                melding.startDato.isBefore(individStartDate)
            }
            .map {
                createReportEntry(
                    "Startdato (${it.startDato}) skal være lik eller etter"
                            + " individets startdato ($individStartDate)",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}