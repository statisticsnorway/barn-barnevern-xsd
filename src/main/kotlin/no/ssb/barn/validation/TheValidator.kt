package no.ssb.barn.validation

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ValidationReport

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
class TheValidator private constructor() {
    private val validatorMap = mapOf(
        Pair(VERSION_ONE, VersionOneValidator()),
        Pair(VERSION_TWO, VersionTwoValidator())
    )

    /**
     * Validates XML and returns validation report on JSON format.
     *
     * @param messageId Vendor-specified message-id
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report instance
     */
    fun validate(messageId: String, xsdVersion: Int, xmlBody: String): ValidationReport {
        val currentValidator = validatorMap[xsdVersion]
            ?: throw IndexOutOfBoundsException("No validator found")

        val context = ValidationContext(messageId, xmlBody)
        return currentValidator.validate(context)
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