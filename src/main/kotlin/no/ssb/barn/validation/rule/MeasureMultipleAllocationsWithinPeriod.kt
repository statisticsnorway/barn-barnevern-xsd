package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.areOverlappingWithAtLeastThreeMonths
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.SharedValidationConstants.MULTIPLE_MEASURES_RULE_NAME
import no.ssb.barn.validation.SharedValidationConstants.kodelistePlasseringstiltak
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType
import java.time.format.DateTimeFormatter
import java.util.*

class MeasureMultipleAllocationsWithinPeriod : AbstractRule(
    WarningLevel.WARNING,
    MULTIPLE_MEASURES_RULE_NAME,
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val measures = context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                val category = tiltak.kategori  // when JaCoCo improves, use "?."
                category.kode in kodelistePlasseringstiltak && tiltak.opphevelse != null
            }
            .toList()

        if (measures.count() < 2) {
            return null
        }

        val reportEntries = mutableListOf<ReportEntry>()

        measures.forEachIndexed { outerIndex, outerMeasure ->
            measures.forEachIndexed inner@{ innerIndex, innerMeasure ->
                if (outerIndex == innerIndex || !areOverlappingWithAtLeastThreeMonths(
                        outerMeasure,
                        innerMeasure
                    )
                ) {
                    return@inner
                }

                val errorMsg =
                    "Plasseringstiltak ${outerMeasure.id} med sluttdato " +
                            outerMeasure.opphevelse!!.sluttDato!!.format(
                                DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            ) +
                            " er mer enn 3 måneder etter ${innerMeasure.id}"
                " med startdato " +
                        innerMeasure.startDato!!.format(
                            DateTimeFormatter.ofPattern(
                                "dd.MM.yyyy"
                            )
                        ) +
                        ". Dette gir en overlapp på mer enn 3 måneder."

                reportEntries.add(
                    createReportEntry(
                        errorMsg,
                        outerMeasure.id as UUID
                    )
                )
            }
        }
        return reportEntries.ifEmpty { null }
    }
}