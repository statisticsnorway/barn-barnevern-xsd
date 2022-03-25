package no.ssb.barn.validation.rule.measure

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.rule.measure.MeasureAgeAboveElevenAndInSfo
import no.ssb.barn.xsd.KategoriType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 6: Barnet er over 11 år og i SFO

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 11 år og tiltakets kategori er '4.2' SFO/AKS<br/>
så gi feilmelding "Barnet er over 11 år og i SFO"

Alvorlighetsgrad: Warning
""")
class MeasureAgeAboveElevenAndInSfoSpec extends Specification {

    @Subject
    MeasureAgeAboveElevenAndInSfo sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureAgeAboveElevenAndInSfo()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given: "det er en sak "
        def sak = context.rootObject.sak
        and: "som har tiltak med kategorikode"
        sak.tiltak[0].kategori = new KategoriType(code, "~presisering~")
        and: "som har et fødselnummer (her generert basert på en gitt alder)"
        sak.fodselsnummer = getMockSocialSecurityNumber(age)

        when: "barnets alder er større enn 11 skal det sjekkes at kategori er '4.2' SFO/Aktivitetsskole"
        def reportEntries = sut.validate(context)

        then: "at ingen unntak er kastet"
        noExceptionThrown()
        and: "at antall feil er som forventet"
        (reportEntries != null) == errorExpected
        and: "hvis det forventes feil"
        if (errorExpected) {
            and: "at antall feil er 1"
            assert 1 == reportEntries.size()
            and: "at riktig alvorlighetsgrad er satt"
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            and: "at feilmeldingsteksten inneholder en gitt tekst"
            assert reportEntries[0].errorText.contains("Barnet er over 11 år og i SFO")
        }

        where:
        age  | code     || errorExpected
        11   | "~kode~" || false
        12   | "~kode~" || false
        11   | "4.2"    || false
        11   | "4.2"    || false
        12   | "4.2"    || true
        12   | "4.3"    || false
    }
}
