package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SocialSecurityIdSpec extends Specification {

    @Subject
    SocialSecurityId sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new SocialSecurityId()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Tester alle personnr-scenarier"() {
        given:
        context.rootObject.sak.fodselsnummer = socialSecurityId

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
        socialSecurityId || expectedError
        "02011088123"    || null
        "12345655555"    || "Individet har ufullstendig fødselsnummer."
        null             || "Individet har ufullstendig fødselsnummer."
        ""               || "Individet har ufullstendig fødselsnummer."
        " "              || "Individet har ufullstendig fødselsnummer."
    }
}