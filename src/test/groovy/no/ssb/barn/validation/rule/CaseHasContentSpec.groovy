package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType
import no.ssb.barn.xsd.PlanType
import no.ssb.barn.xsd.TiltakType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class CaseHasContentSpec extends Specification {

    @Subject
    CaseHasContent sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new CaseHasContent()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def virksomhet = context.rootObject.sak.virksomhet[0]
        and:
        virksomhet.melding = messages as List<MeldingType>
        and:
        virksomhet.tiltak = measures as List<TiltakType>
        and:
        virksomhet.plan = plans as List<PlanType>

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
            assert reportEntries[0].errorText.contains("Individet har ingen meldinger, planer eller")
        }

        where:
        messages                   | measures                  | plans                   || errorExpected
        List.of()                  | List.of()                 | List.of()               || true
        List.of(new MeldingType()) | List.of()                 | List.of()               || false
        List.of()                  | List.of(new TiltakType()) | List.of()               || false
        List.of()                  | List.of()                 | List.of(new PlanType()) || false
    }
}