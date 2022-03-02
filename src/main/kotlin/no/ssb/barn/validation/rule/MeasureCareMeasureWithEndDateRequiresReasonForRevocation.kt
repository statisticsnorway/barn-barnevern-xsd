package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.TiltakType

class MeasureCareMeasureWithEndDateRequiresReasonForRevocation : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 4: Omsorgstiltak med sluttdato krever årsak til opphevelse",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                val revocation = tiltak.opphevelse // when JaCoCo improves, use "?."
                (isCareMeasure(tiltak.lovhjemmel) || tiltak.jmfrLovhjemmel.any{ isCareMeasure(it) })
                        && revocation == null
            }
            .map {
                createReportEntry(
                    "Omsorgstiltak med sluttdato krever årsak til opphevelse.",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }

    private fun isCareMeasure(legalAuthority: LovhjemmelType?): Boolean =
        if (legalAuthority == null) {
            false

        } else {
            with(legalAuthority) {
                (kapittel == "4")
                        && (
                        (paragraf == "12")
                                ||
                                (paragraf == "8" && ledd.any { it in listOf("2", "3") })
                        )

            }
        }
}