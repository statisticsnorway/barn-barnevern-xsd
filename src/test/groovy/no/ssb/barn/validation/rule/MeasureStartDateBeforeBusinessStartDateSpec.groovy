package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 2e: StartDato er før virksomhetens StartDato

Gitt at man har et Tiltak der StartDato finnes og virksomhet der StartDato finnes<br/>
når tiltakets StartDato er før virksomhetens StartDato <br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er før virksomhetens startdato {StartDato}"

Alvorlighetsgrad: ERROR
""")
class MeasureStartDateBeforeBusinessStartDateSpec extends Specification {

    @Subject
    MeasureStartDateBeforeBusinessStartDate sut
    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureStartDateBeforeBusinessStartDate()
        context = getTestContext()
    }

    @Unroll
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = businessStartDate
        and:
        context.rootObject.sak.tiltak[0].startDato = measureStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected

        where:
        businessStartDate                 | measureStartDate                  || errorExpected
        LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || true
    }
}
