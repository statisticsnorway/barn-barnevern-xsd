package no.ssb.barn.validation.rule.message

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType

class MessageCaseContentMissingClarification : AbstractRule(
    WarningLevel.ERROR,
    "Saksinnhold Kontroll 2: Kontroll av kode og presisering",
    SaksinnholdType::class.java.simpleName
) {
    // 18 = Andre forhold ved foreldre/familien
    // 19 = Andre forhold ved barnets situasjon
    private val codesThatRequiresClarification = listOf("18", "19")

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.melding.asSequence()
            .flatMap { melding -> melding.saksinnhold }
            .filter { saksinnhold ->
                saksinnhold.kode in codesThatRequiresClarification
                        && saksinnhold.presisering.isNullOrEmpty()
            }
            .map {
                createReportEntry(
                    "Saksinnhold med kode (${it.kode}) mangler presisering"
                )
            }
            .toList()
            .ifEmpty { null }
}