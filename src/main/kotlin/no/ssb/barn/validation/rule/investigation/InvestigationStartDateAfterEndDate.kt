package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2a: StartDato er etter SluttDato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.undersokelse.asSequence()
            .filter { undersokelse ->
                with(undersokelse) {
                    konklusjon != null && startDato.isAfter(konklusjon!!.sluttDato)
                }
            }
            .map {
                createReportEntry(
                    "Undersøkelses startdato (${it.startDato}) "
                            + " er etter sluttdato (${it.konklusjon!!.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}