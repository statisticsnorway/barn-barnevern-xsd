package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.LovhjemmelType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 13: Individ er over 18 år og har omsorgtiltak

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder) og<br/>
tiltakets Lovhjemmel eller JmfrLovhjemmel sitt Kapittel er 4 og<br/>
Paragraf er 12<br/>
eller Paragraf er 8 og Ledd er 2 eller 3<br/>
når alder er 18 år eller større
så gi feilmelding "Individet er over 18 år skal dermed ikke ha omsorgstiltak"

Alvorlighetsgrad: ERROR
""")
class MeasureLegalBasisAgeAboveEighteenNoMeasureSpec extends Specification {

    @Subject
    MeasureLegalBasisAgeAboveEighteenNoMeasure sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureLegalBasisAgeAboveEighteenNoMeasure()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.fodselsnummer = getMockSocialSecurityNumber(age)
        and:
        sak.tiltak = [sak.tiltak[0]]
        and:
        sak.tiltak[0].lovhjemmel = new LovhjemmelType(
                "~lov~",
                kapittel,
                paragraf,
                ledd != null
                        ? [ledd]
                        : [] as List<String>,
                [])


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
            assert reportEntries[0].errorText.contains("år og skal dermed ikke ha omsorgstiltak")
        }

        where:
        age | kapittel | paragraf | ledd || errorExpected
        17  | ""       | ""       | null || false
        18  | ""       | ""       | null || false
        18  | ""       | ""       | null || false
        18  | "4"      | "12"     | null || true
        18  | "4"      | "8"      | null || false
        18  | "4"      | "8"      | "2"  || true
        18  | "4"      | "8"      | "3"  || true
        18  | "4"      | "8"      | "4"  || false
        18  | "4"      | "7"      | null || false
        18  | "4"      | "7"      | "2"  || false
        18  | "4"      | "7"      | "3"  || false
        18  | "4"      | "7"      | "4"  || false
    }
}
