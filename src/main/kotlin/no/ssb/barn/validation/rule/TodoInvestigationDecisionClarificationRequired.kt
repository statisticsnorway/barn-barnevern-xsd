package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType

class TodoInvestigationDecisionClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Vedtaksgrunnlag Kontroll 2: Kontroll av kode \" + vedtaksgrunnlagKode + \" og presisering",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}