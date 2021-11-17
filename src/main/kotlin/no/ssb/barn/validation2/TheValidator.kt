package no.ssb.barn.validation2

import com.google.gson.Gson
import no.ssb.barn.framework.ValidationContext

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
class TheValidator private constructor() {

    private val gson = Gson()

    private val validatorMap = mapOf(
        Pair(VERSION_ONE, VersionOneValidator()),
        Pair(VERSION_TWO, VersionTwoValidator())
    )

    /**
     * Validates XML and returns validation report on JSON format.
     *
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report on JSON format
     */
    fun validate(xsdVersion: Int, xmlBody: String): String {
        val currentValidator = validatorMap[xsdVersion]
        val context = ValidationContext(xmlBody)
        return gson.toJson(currentValidator?.validate(context))
    }

    companion object {

        const val VERSION_ONE = 1
        const val VERSION_TWO = 2

        /**
         * Builds a validator instance.
         *
         * @return [TheValidator] instance
         */
        @JvmStatic
        fun create(): TheValidator {

            // assemble the validator here
            return TheValidator()
        }
    }
}