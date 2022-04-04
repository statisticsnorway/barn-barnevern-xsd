package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.OversendelsePrivatKravKonklusjonType
import no.ssb.barn.xsd.OversendelsePrivatKravType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Vedtak Kontroll 2f: Krav sin StartDato er etter SluttDato

Gitt at man har et Vedtak der Krav finnes StartDato og Konklusjon/SluttDato finnes<br/>
når for hvert krav validér at kravets StartDato er etter SluttDato <br/>
så gi feilmeldingen "Kravets startdato {StartDato} er etter sluttdato {Krav/Konklusjon/SluttDato}

Alvorlighetsgrad: ERROR
""")
class DecisionRequirementStartDateAfterEndDateSpec extends Specification {

    @Subject
    DecisionRequirementStartDateAfterEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new DecisionRequirementStartDateAfterEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.vedtak.first().krav[0] =
            createOversendelsePrivatKravType(requirementStartDate, requirementEndDate, resetConclusion)

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter sluttdato")
        }

        where:
        requirementStartDate          | requirementEndDate            | resetConclusion || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false           || false
        LocalDate.now()               | LocalDate.now()               | false           || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false           || true
        LocalDate.now()               | LocalDate.now().minusYears(1) | true            || false
    }

    def createOversendelsePrivatKravType(startDate, endDate, resetConclusion) {

        def conclusion = resetConclusion
                ? null
                : new OversendelsePrivatKravKonklusjonType(endDate)

        new OversendelsePrivatKravType(
                UUID.randomUUID(),
                startDate,
                conclusion
        )
    }
}
