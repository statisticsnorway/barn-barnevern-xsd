package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.UndersokelseType

class InvestigationProcessingTimePassedDueDate : AbstractRule(
    WarningLevel.WARNING,
    "Undersøkelse Kontroll 11: Fristoverskridelse på behandlingstid i forhold til melding sin startdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val businesses = context.rootObject.sak.virksomhet.asSequence()
        val investigations = businesses.flatMap { virksomhet -> virksomhet.undersokelse }
        val relations = businesses.flatMap { virksomhet -> virksomhet.relasjon }
        val messages = businesses.flatMap { virksomhet -> virksomhet.melding }

        return relations
            .map { relation ->
                if (relation.tilType != BegrepsType.UNDERSOKELSE || relation.fraType != BegrepsType.MELDING)
                    return@map null

                val message = messages.firstOrNull { message ->
                    message.id == relation.fraId
                } ?: return@map null

                val investigation = investigations.firstOrNull { investigation ->
                    investigation.id == relation.tilId
                } ?: return@map null

                if (investigation.konklusjon != null)
                    return@map null

                val currentDate = context.rootObject.datoUttrekk

                if (currentDate.toLocalDate().isAfter(message.startDato.plusDays(7 + 90))
                    && (investigation.utvidetFrist == null
                            || investigation.utvidetFrist!!.innvilget == null
                            || investigation.utvidetFrist!!.innvilget == "2"
                            )
                ) {
                    return@map createReportEntry(
                        "Undersøkelse skal konkluderes innen 7 + 90 dager etter melding sin startdato",
                        relation.tilId
                    )
                }

                if (currentDate.toLocalDate().isAfter(message.startDato.plusDays(7 + 180))) {
                    return@map createReportEntry(
                        "Undersøkelse skal konkluderes innen 7 + 180 dager etter melding sin startdato",
                        relation.tilId
                    )
                }

                return@map null
            }
            .filterNotNull()
            .toList()
            .ifEmpty { null }
    }
}