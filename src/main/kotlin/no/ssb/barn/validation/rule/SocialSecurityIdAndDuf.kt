package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.validateSSN
import no.ssb.barn.xsd.SakType
import java.util.regex.Pattern

class SocialSecurityIdAndDuf : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 03: Fødselsnummer og DUFnummer",
    SakType::class.java.simpleName
) {
    private val dufPattern = Pattern.compile("^\\d{12}$")

    private val suffixExceptions = setOf("00100", "00200", "55555", "99999")

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val fodselsnummer = context.rootObject.sak.fodselsnummer
        val duFnummer = context.rootObject.sak.duFnummer

        if (!fodselsnummer.isNullOrEmpty()) {
            return if (
                fodselsnummer.length == 11
                    && (
                        suffixExceptions.contains(fodselsnummer.takeLast(5))
                        || validateSSN(fodselsnummer))) {
                null
            } else {
                createSingleReportEntryList(
                    "Feil i fødselsnummer. Kan ikke identifisere individet.",
                    context.rootObject.sak.id
                )
            }
        }

        if (!duFnummer.isNullOrEmpty()) {
            return if (!dufPattern.matcher(duFnummer).matches()) {
                createSingleReportEntryList(
                    "DUFnummer mangler. Kan ikke identifisere individet.",
                    context.rootObject.sak.id
                )
            } else {
                null
            }
        }

        return createSingleReportEntryList(
            "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere individet.",
            context.rootObject.sak.id
        )
    }
}