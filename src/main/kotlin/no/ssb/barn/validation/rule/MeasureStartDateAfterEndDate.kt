package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType
import java.util.*

class MeasureStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2a: Startdato er etter sluttdato",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                val repeal = tiltak.konklusjon // when JaCoCo improves, use "?."
                repeal != null && tiltak.startDato.isAfter(repeal.sluttDato)
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}}). Startdato (${it.startDato})"
                            + " for tiltaket er etter sluttdato"
                            + " (${it.konklusjon!!.sluttDato}) for tiltaket",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
}