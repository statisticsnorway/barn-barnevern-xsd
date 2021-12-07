package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType
import java.time.LocalDate

class InvestigationDueDatePassedConclusionRequired : AbstractRule(
    WarningLevel.WARNING,
    "Undersøkelse Kontroll 8: Ukonkludert undersøkelse påbegynt før 1. juli er ikke konkludert",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.undersokelse }
            .flatten()
            .filter { undersokelse ->
                undersokelse.konklusjon == null
                        && undersokelse.startDato.plusMonths(6)
                    .isBefore(LocalDate.now())
            }
            .map {
                createReportEntry(
                    "Undersøkelse (${it.id})."
                            + " Undersøkelsen startet ${it.startDato}"
                            + " og skal konkluderes da den har pågått i mer enn"
                            + " 6 måneder",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}