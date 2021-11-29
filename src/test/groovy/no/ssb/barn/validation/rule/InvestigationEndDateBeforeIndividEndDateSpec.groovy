package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class InvestigationEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    InvestigationEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationEndDateBeforeIndividEndDate()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = individEndDate
        and:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        investigation.konklusjon.sluttDato = investigationEndDate
        and:
        if (removeConclusion) {
            investigation.konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter individets sluttdato")
        }

        where:
        individEndDate                | investigationEndDate          | removeConclusion || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false            || true
        LocalDate.now().minusYears(1) | LocalDate.now()               | true             || false
        LocalDate.now()               | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false            || false
    }
}
