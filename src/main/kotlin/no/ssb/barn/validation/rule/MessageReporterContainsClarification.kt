package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageReporterContainsClarification : AbstractRule(
    WarningLevel.ERROR,
    "Melder Kontroll 2: Kontroll av kode og presisering",
    MeldingType::class.java.simpleName.removeSuffix("Type")
) {
    // TODO: Sjekk på meldingSluttDato.isAfter(forrigeTelleDato) mangler

    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding -> melding.konklusjon?.sluttDato != null && melding.melder?.any() == true }
            .flatMap { melding ->
                melding.melder!!
                    .filter { melder -> melder.kode == "22" && melder.presisering.isNullOrEmpty() }
                    .map {
                        createReportEntry(
                            "Melder med kode (${it.kode}) mangler presisering",
                            melding.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
}