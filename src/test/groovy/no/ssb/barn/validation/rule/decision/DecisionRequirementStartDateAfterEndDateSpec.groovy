package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

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
        def requirement = context.rootObject.sak.vedtak.first().krav.first()
        and:
        requirement.startDato = requirementStartDate
        and:
        requirement.konklusjon.sluttDato = requirementEndDate
        and:
        if (removeConclusion) {
            requirement.konklusjon = null
        }

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
        requirementStartDate              | requirementEndDate                | removeConclusion || errorExpected
        ZonedDateTime.now().minusYears(1) | ZonedDateTime.now()               | false            || false
        ZonedDateTime.now()               | ZonedDateTime.now()               | false            || false
        ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1) | false            || true
        ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1) | true             || false
    }
}
