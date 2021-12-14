package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType

class InvestigationDecisionRequired : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 7: Konkludert undersøkelse skal ha vedtaksgrunnlag",
    UndersokelseType::class.java.simpleName
) {
    private val codesThatRequiresDecision = listOf("1", "2")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.undersokelse }
            .filter { undersokelse ->
                codesThatRequiresDecision.contains(undersokelse.konklusjon?.kode)
                        && !undersokelse.vedtaksgrunnlag.any()
            }
            .map {
                createReportEntry(
                    "Undersøkelse (${it.id})."
                            + " Undersøkelse konkludert med kode"
                            + " ${it.konklusjon?.kode}"
                            + " skal ha vedtaksgrunnlag",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}