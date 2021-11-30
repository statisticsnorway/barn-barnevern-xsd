package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.testutil.TestDataProvider
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.OpphevelseType
import no.ssb.barn.xsd.TiltakKonklusjonType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class LegalBasisWithEndDateClarificationRequiredSpec extends Specification {

    @Subject
    LegalBasisWithEndDateClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new LegalBasisWithEndDateClarificationRequired()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def virksomhet = context.rootObject.sak.virksomhet[0]
        and:
        virksomhet.tiltak = List.of(virksomhet.tiltak[0])
        and:
        virksomhet.tiltak[0].lovhjemmel = null
        and:
        virksomhet.tiltak[0].konklusjon = null
        and:
        virksomhet.tiltak[0].opphevelse = null
        and:
        if (createLovhjemmel) {
            virksomhet.tiltak[0].lovhjemmel = new LovhjemmelType(
                    "~lov~", kapittel, paragraf, List.of(ledd), List.of())
        }
        and:
        if (createOpphevelse) {
            virksomhet.tiltak[0].opphevelse = new OpphevelseType(kode, presisering)
        }
        and:
        if (createKonklusjon) {
            virksomhet.tiltak[0].konklusjon = new TiltakKonklusjonType(LocalDate.now())
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
        createLovhjemmel | createOpphevelse | createKonklusjon | kapittel | paragraf | ledd | kode | presisering     || errorExpected
        false            | false            | false            | ""       | ""       | ""   | ""   | ""              || false
        false            | false            | true             | ""       | ""       | ""   | ""   | ""              || false
        false            | true             | true             | ""       | ""       | ""   | ""   | ""              || false
        false            | true             | true             | ""       | ""       | ""   | "4"  | "~presisering~" || false
        false            | true             | true             | ""       | ""       | ""   | "4"  | ""              || false
        true             | true             | true             | ""       | ""       | ""   | "4"  | "~presisering~" || false
        true             | true             | true             | ""       | ""       | ""   | "4"  | ""              || false
        true             | true             | true             | "4"      | "12"     | ""   | "4"  | ""              || true
        true             | true             | true             | "4"      | "8"      | ""   | "4"  | "~presisering~" || false
        true             | true             | true             | "4"      | "8"      | "2"  | "4"  | ""              || true
        true             | true             | true             | "4"      | "8"      | "3"  | "4"  | ""              || true
        true             | true             | true             | "4"      | "8"      | "4"  | "4"  | ""              || false
    }
}
