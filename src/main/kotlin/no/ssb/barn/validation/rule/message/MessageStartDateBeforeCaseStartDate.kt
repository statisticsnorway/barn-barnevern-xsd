package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MessageStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2e: Startdato er før sakens startdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.melding.asSequence()
            .filter { melding ->
                melding.startDato!!.isBefore(sak.startDato)
            }
            .map {
                createReportEntry(
                    "Meldingens startdato (${it.startDato}) er før"
                            + " sakens startdato (${sak.startDato})",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
    }
}