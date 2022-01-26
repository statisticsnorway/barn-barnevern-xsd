package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageEndDateAfterBusinessEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2c: Sluttdato er etter virksomhetens sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .map { virksomhet ->
                val businessEndDate = virksomhet.sluttDato ?: return@map null

                virksomhet.melding.asSequence()
                    .filter { melding ->
                        val conclusion = melding.konklusjon
                        conclusion != null && melding.konklusjon!!.sluttDato.isAfter(businessEndDate)
                    }
                    .map {
                        createReportEntry(
                            "Meldingens sluttdato (${it.konklusjon!!.sluttDato})"
                                    + " er etter virksomhetens"
                                    + " sluttdato ($businessEndDate)",
                            it.id
                        )
                    }
            }
            .filterNotNull()
            .flatten()
            .toList()
            .ifEmpty { null }
    }
}
