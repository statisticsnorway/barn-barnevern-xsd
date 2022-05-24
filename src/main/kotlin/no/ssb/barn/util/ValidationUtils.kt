package no.ssb.barn.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Year
import java.util.regex.Pattern
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory


object ValidationUtils {

    private val xsdAsText = this::class.java.getResource("/Barnevern.xsd")!!.readText()

    fun getSchemaValidator() = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .apply {
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        }.newSchema(StreamSource(xsdAsText.byteInputStream()))
        .newValidator()

    val controlSumDigits1 = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2, 1)
    val controlSumDigits2 = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1)

    @JvmStatic
    fun validateSSN(ssn: String): Boolean =
        modulo11(ssn.substring(0, ssn.length - 1), controlSumDigits1) == 0
                && modulo11(ssn, controlSumDigits2) == 0

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

    @JvmStatic
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

    @JvmStatic
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

    @JvmStatic
    fun dnr2fnr(dnr: String): String {
        var first = dnr.first().toString().toInt()

        /* When using d-number then 4 is added to the first digit. */
        /* When the first digit is greater than 3 then subtract 4 to get a valid date  */
        if (first > 3) {
            first -= 4
        }
        return first.toString() + dnr.substring(1, 6)
    }

    @JvmStatic
    fun getAge(socialSecurityNumber: String?): Int =
        if (socialSecurityNumber != null)
            getAlderFromFnr(
                socialSecurityNumber, Year.now().value % 100
            )
        else {
            -2
        }

    @JvmStatic
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

    private fun isValidDate(dateStr: String): Boolean {
        val sdf: DateFormat = SimpleDateFormat("ddMMyy")
        sdf.isLenient = false
        try {
            sdf.parse(dateStr)
        } catch (e: ParseException) {
            return false
        }
        return true
    }
}
