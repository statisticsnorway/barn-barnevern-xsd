package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.LovhjemmelType
import no.ssb.barn.xsd.OpphevelseType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 4: Omsorgstiltak med sluttdato krever årsak til opphevelse

Gitt at man har et Tiltak der SluttDato er satt og<br/>
tiltakets Lovhjemmel eller JmfrLovhjemmel sitt Kapittel er 4 og<br/>
Paragraf er 12<br/>
eller Paragraf er 8 og Ledd er 2 eller 3<br/>
når Opphevelse mangler  
så gi feilmeldingen "Omsorgstiltak med sluttdato krever årsak til opphevelse."

Alvorlighetsgrad: ERROR
""")
class MeasureCareMeasureWithEndDateRequiresReasonForRevocationSpec extends Specification {

    @Subject
    MeasureCareMeasureWithEndDateRequiresReasonForRevocation sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureCareMeasureWithEndDateRequiresReasonForRevocation()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def measure = context.rootObject.sak.tiltak[0]
        and:
        measure.startDato = ZonedDateTime.now().minusMonths(3)
        and:
        measure.lovhjemmel = new LovhjemmelType("BVL", chapter, paragraph, List.of(section), null)
        and:
        measure.jmfrLovhjemmel[0] = (chapter0 == null)
                ? null
                : new LovhjemmelType("BVL", chapter0, paragraph0, List.of(section0), null)
        and:
        measure.opphevelse = (revocationCode == null)
                ? null
                : new OpphevelseType(revocationCode, null, ZonedDateTime.now())

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
            assert reportEntries[0].errorText.contains("Omsorgstiltak med sluttdato krever årsak til opphevelse.")
        }

        where:
        chapter | paragraph | section | chapter0 | paragraph0 | section0 | revocationCode || errorExpected
        "4"     | "1"       | "1"     | null     | null       | null     | null           || false
        "4"     | "12"      | "1"     | null     | null       | null     | null           || true
        "4"     | "8"       | "1"     | null     | null       | null     | null           || false
        "4"     | "8"       | "2"     | null     | null       | null     | null           || true
        "4"     | "8"       | "3"     | null     | null       | null     | null           || true

        "4"     | "1"       | "1"     | null     | null       | null     | "1"            || false
        "4"     | "12"      | "1"     | null     | null       | null     | "1"            || false
        "4"     | "8"       | "1"     | null     | null       | null     | "1"            || false
        "4"     | "8"       | "2"     | null     | null       | null     | "1"            || false
        "4"     | "8"       | "3"     | null     | null       | null     | "1"            || false

        "4"     | "1"       | "1"     | "4"      | "1"        | "1"      | null           || false
        "4"     | "1"       | "1"     | "4"      | "12"       | "1"      | null           || true
        "4"     | "1"       | "1"     | "4"      | "8"        | "1"      | null           || false
        "4"     | "1"       | "1"     | "4"      | "8"        | "2"      | null           || true
        "4"     | "1"       | "1"     | "4"      | "8"        | "3"      | null           || true

        "4"     | "1"       | "1"     | "4"      | "1"        | "1"      | "1"            || false
        "4"     | "1"       | "1"     | "4"      | "12"       | "1"      | "1"            || false
        "4"     | "1"       | "1"     | "4"      | "8"        | "1"      | "1"            || false
        "4"     | "1"       | "1"     | "4"      | "8"        | "2"      | "1"            || false
        "4"     | "1"       | "1"     | "4"      | "8"        | "3"      | "1"            || false
    }
}
