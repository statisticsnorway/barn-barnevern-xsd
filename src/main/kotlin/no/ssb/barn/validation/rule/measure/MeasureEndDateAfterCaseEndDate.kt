package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2c: Sluttdato mot individets sluttdato",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individEndDate = sak.sluttDato ?: return null

        return sak.tiltak.asSequence()
            .filter { tiltak ->
                val conclusion = tiltak.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && conclusion.sluttDato!!.isAfter(individEndDate)
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}). Sluttdato"
                            + " (${it.konklusjon!!.sluttDato}) er etter individets"
                            + " sluttdato ($individEndDate)",
                    it.id!!

                )
            }
            .toList()
            .ifEmpty { null }
    }
}