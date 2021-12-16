package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class MeasureRepealClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 8: Kontroll av kode og presisering for opphevelse",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val opphevelse = tiltak.opphevelse // when JaCoCo improves, use "?."
                opphevelse != null
                        && opphevelse.presisering.isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}}). Tiltaksopphevelse" +
                            " (${it.opphevelse!!.kode}) mangler presisering",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}