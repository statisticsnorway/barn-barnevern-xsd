package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.areOverlappingWithAtLeastThreeMonths
import no.ssb.barn.xsd.TiltakType
import java.time.format.DateTimeFormatter

class MeasureMultipleAllocationsWithinPeriod : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 9: Flere plasseringstiltak i samme periode",
    TiltakType::class.java.simpleName
) {
    private val kodelistePlasseringstiltak = listOf(
        "1.1",
        "1.2",
        "1.99",
        "2.1",
        "2.2",
        "2.3",
        "2.4",
        "2.5",
        "2.6",
        "2.99",
        "8.2"
    )

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val measures = context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val category = tiltak.kategori  // when JaCoCo improves, use "?."
                category != null
                        && kodelistePlasseringstiltak.contains(category.kode)
                        && tiltak.startDato != null
                        && tiltak.konklusjon != null
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
                            outerMeasure.konklusjon!!.sluttDato.format(
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
                        outerMeasure.id
                    )
                )
            }
        }
        return reportEntries.ifEmpty { null }
    }
}