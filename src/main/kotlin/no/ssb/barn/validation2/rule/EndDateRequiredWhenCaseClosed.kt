package no.ssb.barn.validation2.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class EndDateRequiredWhenCaseClosed : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 02d: Avslutta 31 12 medfører at sluttdato skal være satt"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")

/*
        "Individet er avsluttet hos barnevernet og skal dermed være avsluttet. Sluttdato er "
        + individSluttDatoString + ". Kode for avsluttet er '" + avslutta3112 + "'.",
        Constants.CRITICAL_ERROR), avslutta3112,
*/

    }
}