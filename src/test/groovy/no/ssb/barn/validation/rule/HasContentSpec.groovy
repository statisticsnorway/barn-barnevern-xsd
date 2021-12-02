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

class HasContentSpec extends Specification {

    @Subject
    HasContent sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new HasContent()
        context = getTestContext()
    }

    @Unroll
    def "Mangler Melding og Tiltak og Plan, feil forventes"() {
        given:
        def virksomhet = context.rootObject.sak.virksomhet[0]
        and:
        virksomhet.melding = messages
        and:
        virksomhet.tiltak = measures
        and:
        virksomhet.plan = plans

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
        null                       | null                      | null                    || true
        List.of(new MeldingType()) | null                      | null                    || false
        null                       | List.of(new TiltakType()) | null                    || false
        null                       | null                      | List.of(new PlanType()) || false
    }
}