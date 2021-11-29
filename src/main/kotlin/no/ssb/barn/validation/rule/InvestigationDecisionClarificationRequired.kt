package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType

class InvestigationDecisionClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Vedtaksgrunnlag Kontroll 2: Kontroll av kode og presisering",
    SaksinnholdType::class.java.simpleName
) {
    private val codesThatRequiresClarification = listOf("18", "19")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.undersokelse }
            .flatten()
            .mapNotNull { undersokelse -> undersokelse.vedtaksgrunnlag }
            .flatten()
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