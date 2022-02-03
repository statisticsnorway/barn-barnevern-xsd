package no.ssb.barn.validation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.ValidationReport
import no.ssb.barn.report.WarningLevel

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
class TheValidator private constructor() {
    private data class Validators(val xsdValidator: ValidatorContract, val validator: ValidatorContract)

    private val validatorMap = mapOf(
        Pair(VERSION_ONE, Validators(validator = VersionOneValidator(), xsdValidator = VersionOneXsdValidator()))
    )

    /**
     * Validates XML towards XSD then returns validation report on JSON format.
     * I.e. only structural validation
     *
     * @param messageId Vendor-specified message-id
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report instance
     */
    fun validateXmlStructure(messageId: String, xsdVersion: Int, xmlBody: String): ValidationReport {
        // Validate xsd version with elvis operator
        val currentValidator = validatorMap[xsdVersion]?.xsdValidator
            ?: return ValidationReport(messageId,
                listOf(
                    ReportEntry(WarningLevel.FATAL,
                        "Validering av xsd-versjon",
                        "Oppgitt XSD-versjon eksisterer ikke. Oppgitt: ${xsdVersion}, forventet en av: ${validatorMap.keys.joinToString { i -> i.toString() }}",
                        "N/A"
                    )
                ),
                WarningLevel.FATAL)

        val context = ValidationContext(messageId, xmlBody)
        return currentValidator.validate(context)
    }

    /**
     * Validates XML and logic rules, then returns validation report on JSON format.
     *
     * @param messageId Vendor-specified message-id
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report instance
     */
    fun validate(messageId: String, xsdVersion: Int, xmlBody: String): ValidationReport {
        val currentValidator = validatorMap[xsdVersion]?.validator
            ?: throw IndexOutOfBoundsException("No validator found")

        val context = ValidationContext(messageId, xmlBody)
        return currentValidator.validate(context)
    }

    companion object {

        const val VERSION_ONE = 1

        /**
         * Builds a validator instance.
         *
         * @return [TheValidator] instance
         */
        @JvmStatic
        fun create(): TheValidator = TheValidator()
    }
}