package no.ssb.barn.util

import no.ssb.barn.xsd.TiltakType
import org.xml.sax.SAXException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import javax.xml.XMLConstants
import javax.xml.transform.Source
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

object ValidationUtils {

    @JvmStatic
    fun areOverlappingWithAtLeastThreeMonths(
        outerMeasure: TiltakType, innerMeasure: TiltakType
    ): Boolean {

        val outerRange =
            outerMeasure.startDato!!.rangeTo(outerMeasure.konklusjon!!.sluttDato)

        val innerRange =
            innerMeasure.startDato!!.rangeTo(innerMeasure.konklusjon!!.sluttDato)

        return areOverlapping(outerRange, innerRange)
                && getMaxDate(outerRange.start, innerRange.start)
            .plusMonths(3)
            .minusDays(1) // in case both intervals are equal
            .isBefore(
                getMinDate(
                    outerRange.endInclusive,
                    innerRange.endInclusive
                )
            )
    }

    private fun areOverlapping(
        first: ClosedRange<LocalDate>, second: ClosedRange<LocalDate>
    ): Boolean =
        first.start.isBefore(second.endInclusive)
                && second.start.isBefore(first.endInclusive)

    @JvmStatic
    fun getMaxDate(first: LocalDate, second: LocalDate): LocalDate =
        if (first.isAfter(second)) first else second

    @JvmStatic
    fun getMinDate(first: LocalDate, second: LocalDate): LocalDate =
        if (first.isBefore(second)) first else second

    @JvmStatic
    @Throws(SAXException::class)
    fun validateFromSources(xsdFile: Source, xmlFile: Source): Boolean {
        // create a SchemaFactory capable of understanding WXS schemas
        val factory: SchemaFactory =
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

        // load a WXS schema, represented by a Schema instance
        val schema: Schema = factory.newSchema(xsdFile)

        // create a Validator instance, which can be used to validate an instance document
        val validator: Validator = schema.newValidator()

        // validate the DOM tree
        validator.validate(xmlFile)
        return true
    }

    private val controlSumDigits1 = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2, 1)
    private val controlSumDigits2 = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1)

    @JvmStatic
    fun validateSSN(ssn: String): Boolean =
        modulo11(ssn.substring(0, ssn.length - 1), controlSumDigits1)
                && modulo11(ssn, controlSumDigits2)

    private fun modulo11(toCheck: String, controlDigits: List<Int>): Boolean =
        if (toCheck.length != controlDigits.size) {
            false
        } else {
            toCheck
                .mapIndexed { index, currentChar ->
                    if (!currentChar.isDigit()) {
                        return false
                    }
                    currentChar.toString().toInt() * controlDigits[index]
                }
                .sum()
                .mod(11) == 0
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
