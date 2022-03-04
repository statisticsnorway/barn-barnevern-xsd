package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.OpphevelseType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 8: Kontroll av kode og presisering av opphevelse

Gitt at man har et Tiltak der Opphevelse/Kode er 4
når presisering mangler
så gi feilmelding "Opphevelse (kode) mangler presisering."

Alvorlighetsgrad: ERROR
""")
class MeasureRepealClarificationRequiredSpec extends Specification {

    @Subject
    MeasureRepealClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureRepealClarificationRequired()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
            context.rootObject.sak.tiltak[0].opphevelse = (code == null)
                    ? null
                    : new OpphevelseType(code, clarification, ZonedDateTime.now(), null)

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("mangler presisering")
        }

        where:
        code | clarification   || errorExpected
        null | null            || false
        "1"  | null            || false
        "2"  | null            || false
        "3"  | null            || false
        "4"  | "~presisering~" || false
        "4"  | null            || true
        "4"  | ""              || true
    }
}
