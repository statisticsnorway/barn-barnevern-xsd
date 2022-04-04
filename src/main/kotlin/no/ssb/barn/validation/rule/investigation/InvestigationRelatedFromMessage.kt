package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.UndersokelseType

class InvestigationRelatedFromMessage : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 10: Undersøkelse skal ha relasjon fra melding",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val investigations = context.rootObject.sak.undersokelse
        val relations = context.rootObject.sak.relasjon
        val messages = context.rootObject.sak.melding

        return investigations
            .filter { investigation ->
                relations
                    .none { relation ->
                        with(relation) {
                            fraType == BegrepsType.MELDING
                                    && fraId in messages.map { it.id }
                                    && tilType == BegrepsType.UNDERSOKELSE
                                    && tilId == investigation.id
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
