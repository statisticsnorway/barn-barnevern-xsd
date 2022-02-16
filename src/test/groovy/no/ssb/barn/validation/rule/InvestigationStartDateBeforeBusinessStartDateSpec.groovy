package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 2e: StartDato er før virksomhetens StartDato

Gitt at man har en Undersøkelse der StartDato finnes og virksomhet der StartDato finnes<br/>
når undersøkelsens StartDato er før virksomhetens StartDato <br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato } er før virksomhetens startdato {StartDato }"

Alvorlighetsgrad: ERROR
""")
class InvestigationStartDateBeforeBusinessStartDateSpec extends Specification {

    @Subject
    InvestigationStartDateBeforeBusinessStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationStartDateBeforeBusinessStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].startDato = businessStartDate
        and:
        context.rootObject.sak.virksomhet[0].undersokelse[0].startDato = investigationStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er før virksomhetens startdato")
        }

        where:
        businessStartDate             | investigationStartDate              || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
