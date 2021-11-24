package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject

import java.time.Year

class AgeAboveEighteenSpec extends Specification {

    @Subject
    AgeAboveEighteen sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new AgeAboveEighteen()
        context = TestDataProvider.getTestContext()
    }

    def "individ over 18 aar og tiltak finnes, ingen feil forventes"() {
        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ over 18 aar og tiltak mangler, feil forventes"() {
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
