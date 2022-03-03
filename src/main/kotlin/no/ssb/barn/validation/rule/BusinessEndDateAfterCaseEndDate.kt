package no.ssb.barn.validation.rule

import jdk.jfr.Description
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext

@Description("""
Virksomhet Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en sak der SluttDato finnes og virksomhet der SluttDato finnes<br/>
når virksomhetens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Virksomhetens startdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
"""
)
class BusinessEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Virksomhet Kontroll 2c: SluttDato er etter sakens SluttDato",
    "TODO"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val caseEndDate = sak.sluttDato ?: return null

        return null
/*
        TODO: Fix me
        return sak.virksomhet.asSequence()
            .filter { virksomhet ->
                val endDate = virksomhet.sluttDato
                endDate != null
                        && endDate.isAfter(caseEndDate)
            }
            .map {
                createReportEntry(
                    "Virksomhetens sluttdato (${it.sluttDato}) "
                            + "er etter sakens sluttdato ($caseEndDate)",
                    UUID.randomUUID() // Faking id because Virksomhet/Business does not have an id based on UUID
                )
            }
            .toList()
            .ifEmpty { null }
*/
    }
}