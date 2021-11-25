package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.Companion.validateSSN
import java.util.regex.Pattern

class SocialSecurityIdAndDuf : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 03: Fødselsnummer og DUFnummer"
) {
    private val dufPattern = Pattern.compile("^\\d{12}$")

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val fodselsnummer = context.rootObject.sak.fodselsnummer
        val duFnummer = context.rootObject.sak.duFnummer

        if (fodselsnummer != null) {
            return if (validateSSN(fodselsnummer)
                || fodselsnummer.endsWith("00100")
                || fodselsnummer.endsWith("00200")
                || fodselsnummer.endsWith("55555")
                || fodselsnummer.endsWith("99999")
            ) {
                null
            } else {
                createSingleReportEntryList(
                    "Feil i fødselsnummer. Kan ikke identifisere individet."
                )
            }
        }

        if (duFnummer != null) {
            return if (!dufPattern.matcher(duFnummer).matches()) {
                createSingleReportEntryList(
                    "DUFnummer mangler. Kan ikke identifisere individet."
                )
            } else {
                null
            }
        }

        return createSingleReportEntryList(
            "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere individet."
        )
    }
}