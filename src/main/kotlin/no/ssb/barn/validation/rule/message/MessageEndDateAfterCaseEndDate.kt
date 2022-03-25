package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType

class MessageEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2c: Sluttdato er etter virksomhetens sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return if (sak.sluttDato == null) {
            null
        } else {
            sak.melding.asSequence()
                .filter { melding ->
                    val conclusion = melding.konklusjon
                    conclusion != null && conclusion.sluttDato!!.isAfter(sak.sluttDato)
                }
                .map {
                    createReportEntry(
                        "Meldingens sluttdato (${it.konklusjon!!.sluttDato})"
                                + " er etter sakens"
                                + " sluttdato (${sak.sluttDato})",
                        it.id!!
                    )
                }
                .toList()
                .ifEmpty { null }
        }
    }
}
