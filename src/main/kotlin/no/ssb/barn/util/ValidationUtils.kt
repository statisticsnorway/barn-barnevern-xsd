package no.ssb.barn.util

import org.xml.sax.SAXException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.xml.XMLConstants
import javax.xml.transform.Source
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

class ValidationUtils {
    companion object {

        /**
         * Kontrollerer at en dato er etter en annen dato. Hvis dato1 er etter
         * dato2, returnéres false, ellers true
         *
         * @param first - LocalDate
         * @param second - LocalDate
         */
        fun secondDateIsAfterFirstDate(
            first: LocalDate?,
            second: LocalDate?
        ): Boolean =
            if (first == null || second == null) true
            else (second.isAfter(first))

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

        @JvmStatic
        fun validateSSN(ssn: String): Boolean {
            // Validate norwegian sosial security number (ssn), known as fnr or dnr
            val regex = "^\\d{11}$".toRegex()
            if (!regex.matches(ssn)) return false

            val s = ssn.map { it.toString().toInt() }
            var k1 = (s[0] * 3) + (s[1] * 7) + (s[2] * 6) + s[3] + (s[4] * 8) +
                    (s[5] * 9) + (s[6] * 4) + (s[7] * 5) + (s[8] * 2)

            val rest1 = k1 % 11
            if (rest1 == 1) return false
            k1 = if (rest1 == 0) 0 else 11 - rest1

            var k2 =
                (s[0] * 5) + (s[1] * 4) + (s[2] * 3) + (s[3] * 2) + (s[4] * 7) +
                        (s[5] * 6) + (s[6] * 5) + (s[7] * 4) + (s[8] * 3) + (k1 * 2)

            val rest2 = k2 % 11
            if (rest2 == 1) return false
            k2 = if (rest2 == 0) 0 else 11 - rest2

            return (k1 == s[9]) && (k2 == s[10])
        }

        @JvmStatic
        fun getAge(socialSecurityNumber: String?): Int {
            return if (socialSecurityNumber != null)
                getAlderFromFnr(
                    socialSecurityNumber, "2021" /* TODO args.getAargang()*/
                )
            else -2
        }

        @JvmStatic
        fun getAlderFromFnr(fodselsnummer: String, rappAarYYYY: String): Int {
            if (isValidDate(
                    fodselsnummer.substring(
                        0,
                        6
                    ), "ddMMyy"
                )
            ) {
                val fodselsAar = fodselsnummer.substring(4, 6).toInt()
                val aargang = rappAarYYYY.substring(2, 4).toInt()

                val alder =
                    if (aargang < fodselsAar) aargang + 100 - fodselsAar
                    else aargang - fodselsAar

                return if (alder == 99) -1 else alder
            }
            return -1
        }

        private fun isValidDate(dateStr: String, dateFormat: String): Boolean {
            val sdf: DateFormat = SimpleDateFormat(dateFormat)
            sdf.isLenient = false
            try {
                sdf.parse(dateStr)
            } catch (e: ParseException) {
                return false
            }
            return true
        }
    }
}
