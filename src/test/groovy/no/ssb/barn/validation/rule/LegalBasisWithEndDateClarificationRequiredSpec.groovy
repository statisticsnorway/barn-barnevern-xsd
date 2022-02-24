package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.OpphevelseType
import no.ssb.barn.xsd.TiltakKonklusjonType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class LegalBasisWithEndDateClarificationRequiredSpec extends Specification {

    @Subject
    LegalBasisWithEndDateClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new LegalBasisWithEndDateClarificationRequired()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def measure = context.rootObject.sak.virksomhet[0].tiltak[0]
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
        and:
        measure.konklusjon = null
        and:
        if (createKonklusjon) {
            measure.konklusjon = new TiltakKonklusjonType(LocalDate.now())
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
        createOpphevelse | createKonklusjon | kapittel | paragraf | ledd | kode  | presisering     || errorExpected

        // START test where tiltak.erOmsorgsTiltak() == true
        false            | false            | "4"      | "12"     | null | "N/A" | "N/A"           || false
        false            | true             | "4"      | "12"     | null | "N/A" | "N/A"           || false
        true             | true             | "4"      | "12"     | null | "N/A" | "N/A"           || false
        true             | true             | "4"      | "12"     | null | "4"   | "~presisering~" || false
        true             | true             | "4"      | "12"     | null | "4"   | ""              || true
        true             | true             | "4"      | "12"     | null | "4"   | null            || true
        true             | false            | "4"      | "12"     | null | "N/A" | "N/A"           || false

        true             | true             | "4"      | "12"     | null | "5"   | null            || false
        true             | true             | "4"      | "12"     | null | "5"   | ""              || false
        true             | false            | "4"      | "12"     | null | "5"   | "~presisering~" || false

        true             | true             | "4"      | "8"      | "2"  | "4"   | null            || true
        true             | true             | "4"      | "8"      | "2"  | "4"   | ""              || true

        true             | true             | "4"      | "8"      | "3"  | "4"   | null            || true
        true             | true             | "4"      | "8"      | "3"  | "4"   | ""              || true
        // END test where tiltak.erOmsorgsTiltak() == true


        false            | false            | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        false            | false            | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        true             | false            | "N/A"    | "N/A"    | null | "N/A" | "N/A"           || false
        true             | true             | "N/A"    | "N/A"    | null | "42"  | "N/A"           || false
        true             | true             | "N/A"    | "N/A"    | null | "4"   | "~presisering~" || false

        true             | true             | "42"     | "N/A"    | null | "4"   | null            || false
        true             | true             | "42"     | "N/A"    | null | "4"   | ""              || false

        true             | true             | "4"      | "N/A"    | null | "4"   | null            || false
        true             | true             | "4"      | "N/A"    | null | "4"   | ""              || false

        true             | true             | "4"      | "42"     | null | "4"   | null            || false
        true             | true             | "4"      | "42"     | null | "4"   | ""              || false

        true             | true             | "4"      | "8"      | null | "4"   | null            || false
        true             | true             | "4"      | "8"      | null | "4"   | ""              || false

        true             | true             | "4"      | "8"      | "42" | "4"   | null            || false
        true             | true             | "4"      | "8"      | "42" | "4"   | ""              || false
    }
}
