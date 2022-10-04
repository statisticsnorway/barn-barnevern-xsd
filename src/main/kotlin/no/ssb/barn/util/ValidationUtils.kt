package no.ssb.barn.util

import no.ssb.barn.util.Shared.controlSumDigits1
import no.ssb.barn.util.Shared.controlSumDigits2
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Year
import java.util.regex.Pattern
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator


object ValidationUtils {

    fun getSchemaValidator(): Validator = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .apply { setFeature("http://apache.org/xml/features/disallow-doctype-decl", true) }
        .newSchema(StreamSource(xsdAsText.byteInputStream()))
        .newValidator()

    fun validateSSN(socialSecurityId: String): Boolean =
        modulo11(socialSecurityId.substring(0, socialSecurityId.length - 1), controlSumDigits1) == 0
                && modulo11(socialSecurityId, controlSumDigits2) == 0

    fun modulo11(toCheck: String, controlDigits: List<Int>): Int =
        if (toCheck.length == controlDigits.size && Pattern.compile("^\\d+$").matcher(toCheck).matches()) {
            toCheck.asSequence()
                .map { it.toString().toInt() }
                .zip(controlDigits.asSequence()) { digit, controlDigit -> digit * controlDigit }
                .sum()
                .mod(11)
        } else {
            -1
        }

    fun validateDUF(duf: String): Boolean =
        Pattern.compile("^\\d{12}$").matcher(duf).matches()
                &&
                duf.asSequence()
                    .take(10)
                    .map { it.toString().toInt() }
                    .zip(sequenceOf(4, 6, 3, 2, 4, 6, 3, 2, 7, 5)) { digit, weight -> digit * weight }
                    .sum()
                    .mod(11)
                    .toString()
                    .padStart(2, '0') == duf.substring(10)

    fun validateFNR(fnr: String): Boolean =
        Pattern.compile("^\\d{11}$").matcher(fnr).matches()
                && (
                validateSSN(fnr)
                        || (
                        isValidDate(dnr2fnr(fnr))
                                && (
                                fnr.endsWith("00100")
                                        || fnr.endsWith("00200")
                                        || fnr.endsWith("99999")
                                )
                        )
                )

    fun getAge(socialSecurityNumber: String?): Int =
        if (socialSecurityNumber != null) getAlderFromFnr(socialSecurityNumber, Year.now().value % 100)
        else -2

    internal fun dnr2fnr(dnr: String): String {
        var first = dnr.first().toString().toInt()

        /** When using d-number then 4 is added to the first digit. */
        /** When the first digit is greater than 3 then subtract 4 to get a valid date  */
        if (first > 3) {
            first -= 4
        }
        return first.toString() + dnr.substring(1, 6)
    }

    private fun getAlderFromFnr(fodselsnummer: String, aargang: Int): Int =
        if (isValidDate(fodselsnummer.substring(0, 6))) {
            val fodselsAar = fodselsnummer.substring(4, 6).toInt()

            val alder =
                if (aargang < fodselsAar) aargang + 100 - fodselsAar
                else aargang - fodselsAar

            if (alder == 99) -1 else alder
        } else {
            -1
        }

    private fun isValidDate(dateStr: String): Boolean = try {
        SIMPLE_DATE_FORMAT.parse(dateStr)
        true
    } catch (e: ParseException) {
        false
    }

    private val xsdAsText = this.javaClass.getResource("/Barnevern.xsd")!!.readText()

    private val SIMPLE_DATE_FORMAT = SimpleDateFormat("ddMMyy")
        .apply { isLenient = false }
}
