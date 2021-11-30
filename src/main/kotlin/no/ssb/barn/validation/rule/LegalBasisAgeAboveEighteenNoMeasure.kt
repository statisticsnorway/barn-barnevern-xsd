package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils
import no.ssb.barn.xsd.TiltakType

class LegalBasisAgeAboveEighteenNoMeasure : AbstractRule(
    WarningLevel.ERROR,
    "Lovhjemmel Kontroll 3: Individet er over 18 år og har omsorgstiltak",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val age = ValidationUtils.getAge(context.rootObject.sak.fodselsnummer)
        if (age < 18) {
            return null
        }

        return context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.tiltak }
            .flatten()
            .filter { tiltak ->
                val legalBasis = tiltak.lovhjemmel
                legalBasis != null
                        && legalBasis.kapittel == "4"
                        && (
                        legalBasis.paragraf == "12"
                                ||
                                legalBasis.paragraf == "8"
                                && legalBasis.ledd.any {
                            listOf("2", "3").contains(it)
                        })
            }
            .map {
                createReportEntry(
                    "Tiltak ($it.id). Individet er $age år og skal "
                            + "dermed ikke ha omsorgstiltak",
                    it.id ?: "N/A"
                )
            }
            .toList()
            .ifEmpty { null }
    }
}