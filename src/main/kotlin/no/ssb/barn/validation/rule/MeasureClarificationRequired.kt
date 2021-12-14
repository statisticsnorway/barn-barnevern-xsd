package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class MeasureClarificationRequired : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 7: Kontroll av manglende presisering for tiltakskategori",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.tiltak }
            .filter { tiltak ->
                tiltak.kategori != null
                        && tiltak.kategori?.presisering?.trim().isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Tiltak ($it.id). Tiltakskategori"
                            + "(${it.kategori?.kode}) mangler presisering",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}