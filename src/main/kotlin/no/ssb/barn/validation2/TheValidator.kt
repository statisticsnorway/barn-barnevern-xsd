package no.ssb.barn.validation2

import com.google.gson.Gson
import mu.KotlinLogging
import no.ssb.barn.framework.ValidationContext
import java.lang.IndexOutOfBoundsException

private val logger = KotlinLogging.logger {}

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
open class TheValidator private constructor() {

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
            ?: throw IndexOutOfBoundsException("No validator found")

        logger.info { "Received xmlBody: $xmlBody" }

        val context = ValidationContext(xmlBody)
        val validationReport = currentValidator.validate(context)

        logger.info { "Got validation report: $validationReport" }

        return Gson().toJson(validationReport)
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
        fun create(): TheValidator = TheValidator()
    }
}