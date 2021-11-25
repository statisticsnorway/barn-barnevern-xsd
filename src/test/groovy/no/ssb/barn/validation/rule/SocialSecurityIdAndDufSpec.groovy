package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SocialSecurityIdAndDufSpec extends Specification {

    @Subject
    SocialSecurityIdAndDuf sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new SocialSecurityIdAndDuf()
        context = TestDataProvider.getTestContext()
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
        "123456055555"   | null           || null
        "123456099999"   | null           || null
        "123456012345"   | null           || "Feil i fødselsnummer."
        null             | "123456789012" || null
        null             | "123456789"    || "DUFnummer mangler."
        null             | null           || "Fødselsnummer og DUFnummer mangler."
    }
}