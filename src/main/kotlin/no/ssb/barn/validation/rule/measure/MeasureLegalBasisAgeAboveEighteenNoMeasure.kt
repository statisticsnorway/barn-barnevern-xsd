package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType
import no.ssb.barn.xsd.erOmsorgsTiltak
import java.util.*

class MeasureLegalBasisAgeAboveEighteenNoMeasure : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 13: Individ er over 18 år og har omsorgtiltak",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)
        if (age < 18) {
            return null
        }

        return context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak -> tiltak.erOmsorgsTiltak() }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}). Individet er $age år og skal "
                            + "dermed ikke ha omsorgstiltak",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
    }
}