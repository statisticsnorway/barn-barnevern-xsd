package no.ssb.barn.validation.rule.cases

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.KategoriType
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.MeldingType
import no.ssb.barn.xsd.PlanType
import no.ssb.barn.xsd.TiltakType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Sak Kontroll 6: Klienten skal ha melding, plan eller tiltak

Gitt at man har en sak <br/>
når saken mangler melding, plan og tiltak<br/>
så gi feilmeldingen "Klienten har ingen meldinger, planer eller tiltak"

Alvorlighetsgrad: ERROR
""")
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
        def sak = context.rootObject.sak
        and:
        sak.melding.clear()
        and:
        sak.melding.addAll(messages)
        and:
        sak.tiltak.clear()
        and:
        sak.tiltak.addAll(measures)
        and:
        sak.plan.clear()
        and:
        sak.plan.addAll(plans)

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
            assert reportEntries[0].errorText.contains("Klienten har ingen meldinger, planer eller tiltak.")
        }

        where:
        messages              | measures             | plans              || errorExpected
        []                    | []                   | []                 || true
        [createMeldingType()] | []                   | []                 || false
        []                    | [createTiltakType()] | []                 || false
        []                    | []                   | [createPlanType()] || false
    }

    def createMeldingType() {
        new MeldingType(
                UUID.randomUUID(),
                null,
                LocalDate.now(),
                [],
                [],
                null
        )
    }

    def createTiltakType() {
        new TiltakType(
                UUID.randomUUID(),
                null,
                LocalDate.now(),
                new LovhjemmelType(
                        "lov", "kapittel", "paragraf", ["ledd"], ["punktum"]
                ),
                [],
                new KategoriType("1.1", null),
                [],
                [],
                null,
                null
        )
    }

    def createPlanType() {
        new PlanType(
                UUID.randomUUID(),
                null,
                LocalDate.now(),
                "1",
                [],
                null)
    }
}