package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType


class MessageStartDateBeforeBusinessStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2e: Startdato er før virksomhetens startdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .map { virksomhet ->
                val businessStartDate = virksomhet.startDato

                virksomhet.melding.asSequence()
                    .filter { melding ->
                        melding.startDato.isBefore(virksomhet.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Startdato (${it.startDato}) skal være lik eller etter"
                                    + " individets startdato ($businessStartDate)",
                            it.id
                        )
                    }
            }
            .flatten()
            .toList()
            .ifEmpty { null }
    }
}