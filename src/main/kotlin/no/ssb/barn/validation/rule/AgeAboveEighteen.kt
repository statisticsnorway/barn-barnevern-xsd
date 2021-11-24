package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils

class AgeAboveEighteen : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 08: Alder i forhold til tiltak"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)

        // TODO: We are going to check for existence of TiltakType, but
        // it is not implemented yet
        // return if (age < 18 || context.rootObject.sak.virksomhet.tiltak.any()) {

        return if (age < 18 || context.rootObject.sak.virksomhet.any()) {
            null
        } else {
            createSingleReportEntryList(
                "Individet er over 18 år og skal dermed ha tiltak")
        }
    }
}