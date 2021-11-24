package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class EndDateRequiredWhenCaseClosedSpec extends Specification {

    @Subject
    EndDateRequiredWhenCaseClosed sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new EndDateRequiredWhenCaseClosed()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "sak med sluttdato, ingen feil forventes"() {
        given:
        context.rootObject.sak.sluttDato = LocalDate.now()
        and:
        context.rootObject.sak.avsluttet = caseClosed

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        caseClosed | _
        true | _
        false | _
    }

    def "avsluttet sak uten sluttdato, feil forventes"() {
        given:
        context.rootObject.sak.sluttDato = null
        and:
        context.rootObject.sak.avsluttet = true

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
