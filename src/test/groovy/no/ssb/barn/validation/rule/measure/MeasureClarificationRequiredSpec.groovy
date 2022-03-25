package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.KategoriType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 7: Kontroll om presisering av tiltakskategori

Gitt at man har et Tiltak der Kategori/Kode er en følgende koder:<br/>
1.99, 2.99, 3.7, 3.99, 4.99, 5.99, 6.99, 7.99 eller 8.99
når presisering mangler
så gi feilmelding "Tiltakskategori (kode) mangler presisering."

Alvorlighetsgrad: ERROR
""")
class MeasureClarificationRequiredSpec extends Specification {

    @Subject
    MeasureClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureClarificationRequired()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.tiltak[0].kategori = new KategoriType(code, clarification)

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
            assert reportEntries[0].errorText.contains("mangler presisering")
        }

        where:
        code   | clarification   || errorExpected
        "1.1"  | null            || false

        "1.99" | ""              || true
        "1.99" | null            || true
        "2.99" | null            || true
        "3.7"  | null            || true
        "3.99" | null            || true
        "4.99" | null            || true
        "5.99" | null            || true
        "6.99" | null            || true
        "7.99" | null            || true
        "8.99" | null            || true

        "1.99" | "~presisering~" || false
        "2.99" | "~presisering~" || false
        "3.7"  | "~presisering~" || false
        "3.99" | "~presisering~" || false
        "4.99" | "~presisering~" || false
        "5.99" | "~presisering~" || false
        "6.99" | "~presisering~" || false
        "7.99" | "~presisering~" || false
        "8.99" | "~presisering~" || false

    }
}