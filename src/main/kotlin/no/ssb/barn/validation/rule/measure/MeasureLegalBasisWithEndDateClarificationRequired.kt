package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType
import no.ssb.barn.xsd.erOmsorgsTiltak
import java.util.*

class MeasureLegalBasisWithEndDateClarificationRequired : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 12: Omsorgstiltak med sluttdato krever årsak til opphevelse",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.tiltak.asSequence()
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
                    "Opphevelse av omsorgstiltak mangler presisering",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}