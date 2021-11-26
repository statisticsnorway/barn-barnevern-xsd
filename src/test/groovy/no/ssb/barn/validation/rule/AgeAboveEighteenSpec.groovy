package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber

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
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(19)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ under 18 aar og tiltak mangler, ingen feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(16)
        and:
        context.rootObject.sak.virksomhet = List.of(context.rootObject.sak.virksomhet[0])
        and:
        context.rootObject.sak.virksomhet[0].tiltak = List.of()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "individ over 18 aar og tiltak mangler, feil forventes"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(19)
        and:
        context.rootObject.sak.virksomhet[0].tiltak = List.of()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
