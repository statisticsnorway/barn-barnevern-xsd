package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType

class JonOleTodoInvestigationConclusionRequired : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 4: Konklusjon av undersøkelse",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Jon Ole: Regelen sier at avsluttet undersøkelse skal ha")
        TODO("konklusjon. Men det er ikke mulig å ha en avsluttet")
        TODO("undersøkelse uten konklusjon, fordi sluttdato hentes fra")
        TODO("konklusjon")
    }
}