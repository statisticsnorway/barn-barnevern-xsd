package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType
import java.time.LocalDate
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
            .mapNotNull { virksomhet -> virksomhet.tiltak }
            .flatten()
            .filter { tiltak ->
                kodelistePlasseringstiltak.contains(tiltak.kategori?.kode)
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
                if (outerIndex == innerIndex || !areOverlappingWithThreeMonths(
                        outerMeasure,
                        innerMeasure
                    )
                ) {
                    return@inner
                }

                val errorMsg =
                    "Plasseringstiltak ${outerMeasure.id} med sluttdato " +
                            outerMeasure.konklusjon?.sluttDato?.format(
                                DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            ) +
                            " er mer enn 3 måneder etter ${innerMeasure.id}"
                " med startdato " +
                        innerMeasure.startDato?.format(
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

    private fun areOverlappingWithThreeMonths(
        outerMeasure: TiltakType, innerMeasure: TiltakType
    ): Boolean {

        val outerRange =
            outerMeasure.startDato!!.rangeTo(outerMeasure.konklusjon!!.sluttDato)

        val innerRange =
            innerMeasure.startDato!!.rangeTo(innerMeasure.konklusjon!!.sluttDato)

        return areOverlapping(outerRange, innerRange)
                && getMaxDate(outerRange.start, innerRange.start)
            .plusMonths(3)
            .minusDays(1) // in case both intervals are equal
            .isBefore(
                getMinDate(
                    outerRange.endInclusive,
                    innerRange.endInclusive
                )
            )
    }

    private fun areOverlapping(
        first: ClosedRange<LocalDate>, second: ClosedRange<LocalDate>
    ): Boolean =
        first.start <= second.endInclusive
                && second.start <= first.endInclusive

    private fun getMaxDate(first: LocalDate, second: LocalDate): LocalDate =
        if (first > second) first else second

    private fun getMinDate(first: LocalDate, second: LocalDate): LocalDate =
        if (first < second) first else second
}