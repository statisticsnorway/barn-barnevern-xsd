package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDateTime

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
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = businessEndDate
        and:
        def investigation = context.rootObject.sak.undersokelse[0]
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
        businessEndDate     | investigationEndDate            | resetConclusion || errorExpected
        LocalDateTime.now() | LocalDateTime.now()             | false           || false
        LocalDateTime.now() | LocalDateTime.now().plusDays(1) | false           || true
        LocalDateTime.now() | LocalDateTime.now().plusDays(1) | true            || false
    }
}
