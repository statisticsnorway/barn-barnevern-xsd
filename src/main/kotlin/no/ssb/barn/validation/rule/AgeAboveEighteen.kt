package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.Companion.getAge

class AgeAboveEighteen : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 08: Alder i forhold til tiltak"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (getAge(context.rootObject.sak.fodselsnummer) < 18) {
            return null
        }

        return context.rootObject.sak.virksomhet.asSequence()
            .filter { virksomhet -> virksomhet.tiltak?.none() == true }
            .map {
                createReportEntry(
                    "Individet er over 18 år og skal dermed ha tiltak"
                )
            }
            .distinct()
            .toList()
            .ifEmpty { null }
    }
}