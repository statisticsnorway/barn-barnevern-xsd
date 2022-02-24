package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.KategoriType
import no.ssb.barn.xsd.TiltakType

class MeasureClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 7: Kontroll av manglende presisering for tiltakskategori",
    TiltakType::class.java.simpleName
) {
    private val codesThatRequiresClarification =
        listOf("1.99", "2.99", "3.7", "3.99", "4.99", "5.99", "6.99", "7.99", "8.99")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                tiltak.kategori.kode in codesThatRequiresClarification
                        && tiltak.kategori.presisering.isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Tiltakskategori (${it.kategori.kode}) mangler presisering.",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}