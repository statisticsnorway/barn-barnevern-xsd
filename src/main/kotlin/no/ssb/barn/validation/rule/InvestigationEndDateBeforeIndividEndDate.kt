package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType

class InvestigationEndDateBeforeIndividEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2c: Sluttdato mot individets sluttdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individEndDate = sak.sluttDato ?: return null

        return sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.undersokelse }
            .flatten()
            .filter { undersokelse ->
                undersokelse.konklusjon?.sluttDato?.isAfter(individEndDate) == true
            }
            .map {
                createReportEntry(
                    """Undersøkelse (${it.id}). Undersøkelsens sluttdato
                        | (${it.konklusjon?.sluttDato}) er etter individets
                        | sluttdato ($individEndDate)"""
                        .trimMargin()
                        .replace("\n", ""),
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}