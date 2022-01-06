package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageEndDateBeforeIndividEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2c: Sluttdato mot individets sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individEndDate = sak.sluttDato ?: return null

        return sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.melding }
            .filter { melding ->
                val conclusion = melding.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && conclusion.sluttDato.isAfter(individEndDate)
            }
            .map {
                createReportEntry(
                    "Meldingens sluttdato (${it.konklusjon!!.sluttDato})"
                            + " er etter individets"
                            + " sluttdato ($individEndDate)",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}
