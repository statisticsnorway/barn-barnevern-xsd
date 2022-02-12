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
Undersøkelse Kontroll 2c: SluttDato er etter virksomhetens SluttDato

Gitt at man har en Undersøkelse der Konklusjon/SluttDato finnes og i virksomhet der SluttDato finnes<br/>
når undersøkelsens SluttDato er etter virksomhetens SluttDato<br/>
så gi feilmeldingen "Undersøkelsens sluttdato {Konklusjon/SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class InvestigationEndDateAfterBusinessEndDateSpec extends Specification {

    @Subject
    InvestigationEndDateAfterBusinessEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationEndDateAfterBusinessEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].sluttDato = businessEndDate
        and:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        if (resetConclusion) {
            investigation.konklusjon = null
        } else {
            investigation.konklusjon.sluttDato = investigationEndDate
        }

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
            assert reportEntries[0].errorText.contains("er etter virksomhetens")
        }

        where:
        businessEndDate | investigationEndDate        | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
