package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.getAge
import no.ssb.barn.xsd.SakType

class CaseAgeAboveEighteen : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 08: Alder i forhold til tiltak",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (getAge(context.rootObject.sak.fodselsnummer) < 18) {
            return null
        }

        return context.rootObject.sak.virksomhet.asSequence()
            .filter { virksomhet -> !virksomhet.tiltak.any() }
            .map {
                createReportEntry(
                    "Individet er over 18 år og skal dermed ha tiltak",
                    context.rootObject.sak.id
                )
            }
            .distinct()
            .toList()
            .ifEmpty { null }
    }
}