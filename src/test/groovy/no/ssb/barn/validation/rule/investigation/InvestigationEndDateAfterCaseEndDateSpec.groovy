package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseKonklusjonType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Undersøkelse der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når undersøkelsens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Undersøkelsens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class InvestigationEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    InvestigationEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        def investigation = context.rootObject.sak.undersokelse[0]
        and:
        if (resetConclusion) {
            investigation.konklusjon = null
        } else {
            investigation.konklusjon = new UndersokelseKonklusjonType(
                    investigationEndDate,
                    investigation.konklusjon.kode,
                    investigation.konklusjon.presisering)
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
            assert reportEntries[0].errorText.contains("er etter sakens")
        }

        where:
        caseEndDate     | investigationEndDate        | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
