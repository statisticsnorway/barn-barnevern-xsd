package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.UndersokelseType
import java.util.*

class InvestigationProcessingTimePassedDueDate : AbstractRule(
    WarningLevel.WARNING,
    "Undersøkelse Kontroll 11: Fristoverskridelse på behandlingstid i forhold til melding sin startdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val investigations = context.rootObject.sak.undersokelse
        val relations = context.rootObject.sak.relasjon
        val messages = context.rootObject.sak.melding

        return relations
            .filter { relation ->
                relation.fraType == BegrepsType.MELDING
                        && relation.tilType == BegrepsType.UNDERSOKELSE
            }
            .map { relation ->
                val message = messages.firstOrNull { message ->
                    message.id == relation.fraId
                } ?: return@map null

                val investigation = investigations.firstOrNull { investigation ->
                    investigation.id == relation.tilId
                } ?: return@map null

                if (investigation.konklusjon != null)
                    return@map null

                val currentDate = context.rootObject.datoUttrekk

                if (currentDate.isAfter(message.startDato.plusDays(7 + 90))
                    && (investigation.utvidetFrist == null
                            || investigation.utvidetFrist!!.innvilget == null
                            || investigation.utvidetFrist!!.innvilget == "2"
                            )
                ) {
                    return@map createReportEntry(
                        "Undersøkelse skal konkluderes innen 7 + 90 dager etter melding sin startdato",
                        relation.tilId as UUID
                    )
                }

                if (currentDate.isAfter(message.startDato.plusDays(7 + 180))) {
                    return@map createReportEntry(
                        "Undersøkelse skal konkluderes innen 7 + 180 dager etter melding sin startdato",
                        relation.tilId as UUID
                    )
                }

                return@map null
            }
            .filterNotNull()
            .toList()
            .ifEmpty { null }
    }
}