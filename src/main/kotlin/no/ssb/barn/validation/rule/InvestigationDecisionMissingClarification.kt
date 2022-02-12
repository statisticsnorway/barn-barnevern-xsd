package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationDecisionMissingClarification : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 3: Vedtaksgrunnlag mangler presisering",
    UndersokelseType::class.java.simpleName
) {
    private val codesThatRequiresDecision = listOf("18", "19")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.undersokelse }
            .flatMap { undersokelse ->
                undersokelse.vedtaksgrunnlag
                    .filter { vedtaksgrunnlag ->
                        vedtaksgrunnlag.kode in codesThatRequiresDecision
                                && vedtaksgrunnlag.presisering.isNullOrBlank()
                    }
                    .map {
                        createReportEntry(
                            "Vedtaksgrunnlag med kode (${it.kode}) mangler Presisering",
                            undersokelse.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
}