package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class EndDateAfterStartDateSpec extends Specification {

    @Subject
    EndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new EndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll("startDate: #startDate, endDate #endDate")
    def "Suksess-scenarier, ingen feil forventes"() {
        given:
        context.rootObject.sak.startDato = startDate
        and:
        context.rootObject.sak.sluttDato = endDate

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        startDate                     | endDate
        LocalDate.now().minusYears(1) | LocalDate.now()
        LocalDate.now()               | null
    }

    def "Sluttdato før startdato, feil forventes"() {
        given:
        context.rootObject.sak.startDato = LocalDate.now()
        and:
        context.rootObject.sak.sluttDato = LocalDate.now().minusYears(1)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
