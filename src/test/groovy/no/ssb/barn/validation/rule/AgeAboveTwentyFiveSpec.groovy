package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import spock.lang.Specification
import spock.lang.Subject

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class AgeAboveTwentyFiveSpec extends Specification {

    @Subject
    AgeAboveTwentyFive sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new AgeAboveTwentyFive()
        context = getTestContext()
    }

    def "individ under 25, ingen feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(24)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ over 25 aar, feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(27)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null != reportEntries
        and:
        1 == reportEntries.size()
    }
}
