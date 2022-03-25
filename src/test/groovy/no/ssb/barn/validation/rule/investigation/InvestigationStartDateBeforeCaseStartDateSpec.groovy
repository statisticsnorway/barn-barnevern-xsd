package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Undersøkelse der StartDato finnes og sak der StartDato finnes<br/>
når undersøkelsens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato } er før sakens startdato {StartDato }"

Alvorlighetsgrad: ERROR
""")
class InvestigationStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    InvestigationStartDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = caseStartDate
        and:
        context.rootObject.sak.undersokelse[0].startDato = investigationStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er før sakens startdato")
        }

        where:
        caseStartDate                     | investigationStartDate             || errorExpected
        ZonedDateTime.now().minusYears(1) | ZonedDateTime.now()                || false
        ZonedDateTime.now()               | ZonedDateTime.now().plusSeconds(1) || false
        ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1)  || true
    }
}
