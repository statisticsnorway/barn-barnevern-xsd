package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.UndersokelseType

class InvestigationRelatedFromMessage : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 10: Undersøkelse skal ha relasjon til melding",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val businesses = context.rootObject.sak.virksomhet.asSequence()
        val investigations = businesses.flatMap { virksomhet -> virksomhet.undersokelse }
        val relations = businesses.flatMap { virksomhet -> virksomhet.relasjon }
        val messages = businesses.flatMap { virksomhet -> virksomhet.melding }

        return investigations
            .filter { investigation ->
                relations
                    .none { relation ->
                        with(relation){
                            tilType == BegrepsType.UNDERSOKELSE
                                    && tilId == investigation.id
                                    && fraType == BegrepsType.MELDING
                                    && fraId in messages.map { it.id }
                        }
                    }
            }
            .map {
                createReportEntry(
                    "Undersøkelse mangler en relasjon til melding",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}
