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
        return null

/*      TODO fix me
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .flatMap { virksomhet ->
                virksomhet.melding.asSequence()
                    .filter { melding ->
                        melding.startDato.isBefore(virksomhet.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Meldingens startdato (${it.startDato}) er før"
                                    + " virksomhetens startdato (${virksomhet.startDato})",
                            it.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
*/
    }
}