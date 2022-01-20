package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Virksomhet Kontroll 2b: StartDato er etter SluttDato

Gitt at man har en virksomhet der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Virksomhetens startdato {StartDato} er etter sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class BusinessEndDateAfterStartDateSpec extends Specification {

    @Subject
    BusinessEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new BusinessEndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll("startDate: #startDate, endDate #endDate")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].startDato = startDate
        and:
        context.rootObject.sak.virksomhet[0].sluttDato = endDate

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter sluttdato")
        }

        where:
        startDate                     | endDate                       || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | null                          || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
