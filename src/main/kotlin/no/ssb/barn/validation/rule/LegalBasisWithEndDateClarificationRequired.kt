package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
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
                val conclusion = tiltak.konklusjon
                val repeal = tiltak.opphevelse

                legalBasis != null
                        && conclusion != null
                        && repeal != null
                        && repeal.kode == "4"
                        && repeal.presisering.isNullOrEmpty()
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