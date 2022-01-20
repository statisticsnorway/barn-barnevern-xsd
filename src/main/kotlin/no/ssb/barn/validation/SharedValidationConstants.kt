package no.ssb.barn.validation

object SharedValidationConstants {

    const val SOCIAL_SECURITY_RULE_NAME =
        "Sak Kontroll 11: Fødselsnummer"

    const val SOCIAL_SECURITY_AND_DUF_RULE_NAME =
        "Individ Kontroll 03: Fødselsnummer og DUFnummer"

    const val MULTIPLE_MEASURES_RULE_NAME =
        "Tiltak Kontroll 9: Flere plasseringstiltak i samme periode"

    @JvmStatic
    val kodelistePlasseringstiltak = listOf(
        "1.1",
        "1.2",
        "1.99",
        "2.1",
        "2.2",
        "2.3",
        "2.4",
        "2.5",
        "2.6",
        "2.99",
        "8.2"
    )
}