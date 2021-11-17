package no.ssb.barn

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
class TheValidator private constructor() {

    /**
     * Validates XML and returns validation report on JSON format.
     *
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report on JSON format
     */
    fun validate(xsdVersion: Int, xmlBody: String): String {
        return "{ hello: \"world\" }"
    }

    companion object {
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