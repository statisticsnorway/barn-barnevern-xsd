package no.ssb.barn;

/**
 * Barn XML validator. Clients of this library will typically keep an instance
 * of this class as a bean.
 *
 * @author roar.skinderviken@ssb.no
 * @since 1.0
 */
public final class TheValidator {

    /**
     * Builds a validator instance.
     *
     * @return {@link TheValidator} instance
     */
    public static TheValidator create() {
        final TheValidator validator = new TheValidator();

        // assemble the validator here

        return validator;
    }

    /**
     * Validates XML and returns validation report on JSON format.
     *
     * @param xsdVersion XSD version for xmlBody
     * @param xmlBody    XML body
     * @return Validation report on JSON format
     */
    public String validate(final int xsdVersion, final String xmlBody) {
        return "{hello: \"world\"}";
    }

    /**
     * Private constructor.
     */
    private TheValidator() {
    }
}
