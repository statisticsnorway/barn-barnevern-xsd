package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType

class InvestigationDecisionClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 3: Vedtaksgrunnlag mangler presisering",
    SaksinnholdType::class.java.simpleName
) {
    private val codesThatRequiresClarification = listOf("18", "19")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.undersokelse }
            .flatMap { undersokelse -> undersokelse.vedtaksgrunnlag }
            .filter { vedtaksgrunnlag ->
                codesThatRequiresClarification.contains(vedtaksgrunnlag.kode)
                        && vedtaksgrunnlag.presisering.isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Vedtaksgrunnlag med kode ${it.kode})"
                            + " mangler presisering"
                )
            }
            .toList()
            .ifEmpty { null }
}