package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.UndersokelseType

class InvestigationProcessingTimeMoreThan97Days : AbstractRule(
    WarningLevel.WARNING,
    "Undersøkelse Kontroll 11: Fristoverskridelse på behandlingstid på mer enn 97 dager etter melding sin startdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val businesses = context.rootObject.sak.virksomhet.asSequence()
        val investigations = businesses.flatMap { virksomhet -> virksomhet.undersokelse }
        val relations = businesses.flatMap { virksomhet -> virksomhet.relasjon }
        val messages = businesses.flatMap { virksomhet -> virksomhet.melding }

        return relations
            .filter { relation ->
                with(relation) {
                    if (tilType == BegrepsType.UNDERSOKELSE
                        && tilId in investigations.map { it.id }
                        && fraType == BegrepsType.MELDING
                        && fraId in messages.map { it.id }
                    ) {
                        val message = messages.first { message -> message.id == fraId }
                        val investigation = investigations.first { investigation -> investigation.id == tilId }
                        val conclusion = investigation.konklusjon // when JaCoCo improves, use "?."

                        return@filter (
                                conclusion != null
                                        && conclusion.sluttDato.isAfter(message.startDato.plusDays(7 + 90))
                                        && (
                                        (
                                                investigation.utvidetFrist == null
                                                )
                                                ||
                                                (
                                                        investigation.utvidetFrist!!.innvilget == null
                                                                || investigation.utvidetFrist!!.innvilget == "2"
                                                        )
                                        )
                                )
                    } else {
                        false
                    }
                }
            }
            .map { relation ->
                createReportEntry(
                    "Undersøkelse skal konkluderes innen 7 + 90 dager etter melding sin startdato",
                    relation.tilId
                )
            }
            .toList()
            .ifEmpty { null }
    }
}