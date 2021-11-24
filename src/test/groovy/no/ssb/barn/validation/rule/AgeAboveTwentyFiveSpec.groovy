package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject

import java.time.Year

class AgeAboveTwentyFiveSpec extends Specification {

    @Subject
    AgeAboveTwentyFive sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new AgeAboveTwentyFive()
        context = TestDataProvider.getTestContext()
    }

    def "individ under 25, ingen feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = TestDataProvider.getMockSocialSecurityNumber(24)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ over 25 aar, feil forventes"() {
        given:
        def twoDigitBirthYear = Year.now().minusYears(2019)
        and:
        context.rootObject.sak.fodselsnummer = "0101" + twoDigitBirthYear + "88123"
        and:
        context.rootObject.sak.virksomhet = List.of()

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
