package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.measure.MeasureLegalBasisValidCode
import no.ssb.barn.xsd.LovhjemmelType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 12: Omsorgstiltak med sluttdato krever årsak til opphevelse

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder) og<br/>
tiltakets Lovhjemmel eller JmfrLovhjemmel sitt Kapittel er 4 og<br/>
Paragraf er 12<br/>
eller Paragraf er 8 og Ledd er 2 eller 3<br/>
når alder er 18 år eller større
så gi feilmelding "Individet er over 18 år skal dermed ikke ha omsorgstiltak"

Alvorlighetsgrad: ERROR
""")
class MeasureLegalBasisValidCodeSpec extends Specification {

    @Subject
    MeasureLegalBasisValidCode sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureLegalBasisValidCode()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.tiltak = List.of(sak.tiltak[0])
        and:
        sak.tiltak[0].lovhjemmel = new LovhjemmelType(
                "~lov~",
                kapittel,
                paragraf,
                List.of("~ledd~"),
                List.of("~punktum~")
        )

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
            assert reportEntries[0].errorText
                    .contains("er rapportert med den ugyldige koden 0")
        }

        where:
        kapittel | paragraf || errorExpected
        ""       | ""       || false
        ""       | ""       || false
        "1"      | ""       || false
        ""       | "1"      || false
        "01234"  | ""       || true
        ""       | "01234"  || true
        "01234"  | "01234"  || true
    }
}
