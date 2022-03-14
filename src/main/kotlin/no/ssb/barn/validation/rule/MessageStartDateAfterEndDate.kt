package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MessageStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2a: Startdato etter sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.melding.asSequence()
            .filter { melding ->
                val conclusion = melding.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && melding.startDato!!.isAfter(conclusion.sluttDato)
            }
            .map {
                createReportEntry(
                    "Meldingens startdato (${it.startDato}) er etter"
                            + " meldingens sluttdato (${it.konklusjon!!.sluttDato})",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}