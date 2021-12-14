package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class LegalBasisWithEndDateClarificationRequired : AbstractRule(
    WarningLevel.WARNING,
    "Lovhjemmel Kontroll 2: omsorgstiltak med sluttdato krever årsak til opphevelse",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val legalBasis = tiltak.lovhjemmel

                tiltak.konklusjon?.sluttDato != null
                        && tiltak.opphevelse?.kode == "4"
                        && tiltak.opphevelse?.presisering.isNullOrEmpty()
                        && legalBasis != null
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
                    "Tiltak ($it.id). Opphevelse av omsorgstiltak"
                            + " mangler presisering",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}