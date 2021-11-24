package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Ignore
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
        given:
        context.rootObject.sak.fodselsnummer = TestDataProvider.getMockSocialSecurityNumber(19)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    @Ignore("TODO fix me when Tiltak exists")
    def "individ under 18 aar og tiltak mangler, ingen feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = TestDataProvider.getMockSocialSecurityNumber(17)
        and:
        context.rootObject.sak.virksomhet = List.of()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ over 18 aar og tiltak mangler, feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = TestDataProvider.getMockSocialSecurityNumber(19)
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
