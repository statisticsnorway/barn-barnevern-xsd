package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageStartDateAfterOrEqualIndividStartDateSpec extends Specification {

    @Subject
    MessageStartDateAfterOrEqualIndividStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageStartDateAfterOrEqualIndividStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = individStartDate
        and:
        context.rootObject.sak.virksomhet[0].melding[0].startDato = messageStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected

        where:
        individStartDate              | messageStartDate              || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
