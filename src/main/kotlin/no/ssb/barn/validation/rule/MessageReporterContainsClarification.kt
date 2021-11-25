package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageReporterContainsClarification : AbstractRule(
    WarningLevel.ERROR,
    "Melder Kontroll 2: Kontroll av kode og presisering"
) {
    // TODO: Sjekk på meldingSluttDato.isAfter(forrigeTelleDato) mangler

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding -> melding.konklusjon?.sluttDato != null }
            .mapNotNull { melding -> melding.melder }
            .flatten()
            .filter { melder -> melder.kode == "22" && melder.presisering.isNullOrEmpty() }
            .map {
                createReportEntry("Melder med kode (${it.kode}) mangler presisering")
            }
            .toList()
            .ifEmpty { null }
}