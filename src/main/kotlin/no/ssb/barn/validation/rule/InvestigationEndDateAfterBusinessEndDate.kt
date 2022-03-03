package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationEndDateAfterBusinessEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2c: Sluttdato er etter virksomhetens sluttdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return null
/*
        TODO fix med

        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .map { virksomhet ->
                val businessEndDate = virksomhet.sluttDato ?: return@map null

                virksomhet.undersokelse.asSequence()
                    .filter { undersokelse ->
                        val conclusion = undersokelse.konklusjon
                        conclusion != null && undersokelse.konklusjon!!.sluttDato.isAfter(businessEndDate)
                    }
                    .map {
                        createReportEntry(
                            "Undersøkelsens sluttdato (${it.konklusjon!!.sluttDato})"
                                    + " er etter virksomhetens sluttdato ($businessEndDate)",
                            it.id
                        )
                    }
            }
            .filterNotNull()
            .flatten()
            .toList()
            .ifEmpty { null }
*/
    }
}