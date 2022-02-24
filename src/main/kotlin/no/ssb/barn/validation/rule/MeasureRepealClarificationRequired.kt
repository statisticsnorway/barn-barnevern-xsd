package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class MeasureRepealClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 8: Kontroll av kode og presisering av opphevelse",
    TiltakType::class.java.simpleName
) {
    private val codesThatRequiresClarification = listOf("4")

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                val repeal = tiltak.opphevelse // when JaCoCo improves, use "?."

                if (repeal != null) {
                    val clarification = repeal.presisering

                    (((repeal.kode in codesThatRequiresClarification)
                            && (clarification.isNullOrEmpty() || clarification.isBlank())))
                } else {
                    false
                }
            }
            .map {
                createReportEntry(
                    "Opphevelse (${it.opphevelse!!.kode}) mangler presisering.",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}