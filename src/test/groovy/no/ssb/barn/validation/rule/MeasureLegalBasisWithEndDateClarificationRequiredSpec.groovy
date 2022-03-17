package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.OpphevelseType
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
class MeasureLegalBasisWithEndDateClarificationRequiredSpec extends Specification {

    @Subject
    MeasureLegalBasisWithEndDateClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureLegalBasisWithEndDateClarificationRequired()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def measure = context.rootObject.sak.tiltak[0]
        and:
        measure.lovhjemmel = new LovhjemmelType(
                "~lov~",
                kapittel,
                paragraf,
                ledd != null ? List.of(ledd) : List.of() as List<String>,
                List.of())
        and:
        measure.opphevelse = null
        and:
        if (createOpphevelse) {
            measure.opphevelse = new OpphevelseType(kode, presisering)
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
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Opphevelse av omsorgstiltak")
        }

        where:
        createOpphevelse | kapittel | paragraf | ledd | kode  | presisering     || errorExpected

        // START test where tiltak.erOmsorgsTiltak() == true
        false            | "4"      | "12"     | null | "N/A" | "N/A"           || false
        false            | "4"      | "12"     | null | "N/A" | "N/A"           || false
        true             | "4"      | "12"     | null | "N/A" | "N/A"           || false
        true             | "4"      | "12"     | null | "4"   | "~presisering~" || false
        true             | "4"      | "12"     | null | "4"   | ""              || true
        true             | "4"      | "12"     | null | "4"   | null            || true
        true             | "4"      | "12"     | null | "N/A" | "N/A"           || false

        true             | "4"      | "12"     | null | "5"   | null            || false
        true             | "4"      | "12"     | null | "5"   | ""              || false
        true             | "4"      | "12"     | null | "5"   | "~presisering~" || false

        true             | "4"      | "8"      | "2"  | "4"   | null            || true
        true             | "4"      | "8"      | "2"  | "4"   | ""              || true

        true             | "4"      | "8"      | "3"  | "4"   | null            || true
        true             | "4"      | "8"      | "3"  | "4"   | ""              || true
        // END test where tiltak.erOmsorgsTiltak() == true


        false            | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        false            | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        true             | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        true             | "N/A"    | "N/A"    | null | "42"  | "N/A"           || false
        true             | "N/A"    | "N/A"    | null | "4"   | "~presisering~" || false

        true             | "42"     | "N/A"    | null | "4"   | null            || false
        true             | "42"     | "N/A"    | null | "4"   | ""              || false

        true             | "4"      | "N/A"    | null | "4"   | null            || false
        true             | "4"      | "N/A"    | null | "4"   | ""              || false

        true             | "4"      | "42"     | null | "4"   | null            || false
        true             | "4"      | "42"     | null | "4"   | ""              || false

        true             | "4"      | "8"      | null | "4"   | null            || false
        true             | "4"      | "8"      | null | "4"   | ""              || false

        true             | "4"      | "8"      | "42" | "4"   | null            || false
        true             | "4"      | "8"      | "42" | "4"   | ""              || false
    }
}
