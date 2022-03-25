package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType
import java.util.*

class InvestigationConcludedMissingDecision : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 7: Konkludert undersøkelse mangler vedtaksgrunnlag",
    UndersokelseType::class.java.simpleName
) {
    private val codesThatRequiresDecision = listOf("1", "2")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.undersokelse.asSequence()
            .filter { undersokelse ->
                val conclusion = undersokelse.konklusjon
                conclusion != null
                        && conclusion.kode in codesThatRequiresDecision
                        && !undersokelse.vedtaksgrunnlag.any()
            }
            .map {
                createReportEntry(
                    "Undersøkelse konkludert med kode ${it.konklusjon!!.kode} mangler vedtaksgrunnlag",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}