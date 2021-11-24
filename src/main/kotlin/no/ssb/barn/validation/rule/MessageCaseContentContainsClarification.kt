package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils

class MessageCaseContentContainsClarification : AbstractRule(
    WarningLevel.ERROR,
    "Saksinnhold Kontroll 2: Kontroll av kode og presisering"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {

        // TODO: Legg til UndersokelseType når denne er på plass

        // 18 = Andre forhold ved foreldre/familien
        // 19 = Andre forhold ved barnets situasjon
        val codesThatRequiresClarification = listOf("18", "19")

        val reportEntries = context.rootObject.sak.virksomhet
            .asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .mapNotNull { melding -> melding.saksinnhold }
            .flatten()
            .filter { saksinnhold ->
                codesThatRequiresClarification.contains(saksinnhold.kode)
                        && !ValidationUtils.existsAndHasLength(saksinnhold.presisering)
            }
            .map {
                createReportEntry("Saksinnhold med kode (${it.kode}) mangler presisering")
            }
            .toList()

        return reportEntries.ifEmpty {
            null
        }
    }
}