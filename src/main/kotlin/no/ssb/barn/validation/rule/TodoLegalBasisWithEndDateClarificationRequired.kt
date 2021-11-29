package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType

class TodoLegalBasisWithEndDateClarificationRequired : AbstractRule(
    WarningLevel.WARNING,
    "Lovhjemmel Kontroll 2: omsorgstiltak (\" + tiltakId + \") med sluttdato krever årsak til opphevelse",
    LovhjemmelType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}