package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SakType

class EndDateRequiredWhenCaseClosed : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 02d: Avslutta 31 12 medfører at sluttdato skal være satt",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {

        val endDate = context.rootObject.sak.sluttDato

        return if (context.rootObject.sak.avsluttet == true && endDate == null) {
            createSingleReportEntryList(
                "Individet er avsluttet hos barnevernet og skal"
                        + " dermed være avsluttet. Sluttdato er $endDate."
                        + " Kode for avsluttet er TODO",
                context.rootObject.sak.id
            )
        } else
            null
    }

/*
    TODO Jon Ole : Her er originalkoden

    fun controlAvslutta3112(
        er: ErrorReport,
        ere: ErrorReportEntry?,
        kode: String,
        sluttDato: LocalDate?,
        frist: LocalDate?
    ) {
        try {
            if (kode.equals("1", ignoreCase = true)) {
                if (sluttDato == null || frist == null || sluttDato.isAfter(
                        frist
                    )
                ) {
                    er.addEntry(ere)
                }
            }
        } catch (e: NullPointerException) {
            er.addEntry(ere)
        }
    }
*/

}