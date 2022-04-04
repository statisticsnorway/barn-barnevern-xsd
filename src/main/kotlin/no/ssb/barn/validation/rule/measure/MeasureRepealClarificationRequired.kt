package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureRepealClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 8: Kontroll av kode og presisering av opphevelse",
    TiltakType::class.java.simpleName
) {
    private val codesThatRequiresClarification = listOf("4")

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                val repeal = tiltak.opphevelse
                repeal != null
                        && repeal.kode in codesThatRequiresClarification
                        && repeal.presisering.isNullOrEmpty()
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