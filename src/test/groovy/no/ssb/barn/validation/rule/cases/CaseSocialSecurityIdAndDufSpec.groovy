package no.ssb.barn.validation.rule.cases

import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Sak Kontroll 3: Fødselsnummer og DUFnummer

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Feil i fødselsnummer. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når fødselsnummer mangler og DUF-nummer mangler <br/>
så gi feilmeldingen "DUFnummer mangler. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når fødselsnummer og DUF-nummer mangler <br/>
så gi feilmeldingen "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere klienten."

Alvorlighetsgrad: ERROR
""")
class CaseSocialSecurityIdAndDufSpec extends Specification {

    @Subject
    CaseSocialSecurityIdAndDuf sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new CaseSocialSecurityIdAndDuf()
        context = getTestContext()
    }

    @Unroll
    def "Tester alle personnr- og duFnr-scenarier"() {
        given:
        context.rootObject.sak.fodselsnummer = socialSecurityId
        and:
        context.rootObject.sak.duFnummer = duFnr

        when:
        def reportEntries = sut.validate(context)

        then:
        if (expectedError == null) {
            assert null == reportEntries
        } else {
            assert 1 == reportEntries.size()
            assert reportEntries[0].errorText.startsWith(expectedError)
        }

        where:
        socialSecurityId | duFnr          || expectedError
        "02011088123"    | null           || null
        "12345600100"    | null           || null
        "12345600200"    | null           || null
        "12345655555"    | null           || null
        "12345699999"    | null           || null
        "12345612345"    | null           || "Feil i fødselsnummer."
        "123456"         | null           || "Feil i fødselsnummer."
        ""               | "123456789012" || null
        null             | "123456789012" || null
        null             | "123456789"    || "DUFnummer mangler."
        ""               | "123456789"    || "DUFnummer mangler."
        null             | null           || "Fødselsnummer og DUFnummer mangler."
        ""               | null           || "Fødselsnummer og DUFnummer mangler."
        null             | ""             || "Fødselsnummer og DUFnummer mangler."
        ""               | ""             || "Fødselsnummer og DUFnummer mangler."
    }
}