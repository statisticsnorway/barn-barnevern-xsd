package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType
import no.ssb.barn.xsd.erOmsorgsTiltak

class LegalBasisWithEndDateClarificationRequired : AbstractRule(
    WarningLevel.WARNING,
    "Lovhjemmel Kontroll 2: omsorgstiltak med sluttdato krever årsak til opphevelse",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val conclusion = tiltak.konklusjon
                val repeal = tiltak.opphevelse

                tiltak.erOmsorgsTiltak()
                        && conclusion != null
                        && repeal != null
                        && repeal.kode == "4"
                        && repeal.presisering.isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}). Opphevelse av omsorgstiltak"
                            + " mangler presisering",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}