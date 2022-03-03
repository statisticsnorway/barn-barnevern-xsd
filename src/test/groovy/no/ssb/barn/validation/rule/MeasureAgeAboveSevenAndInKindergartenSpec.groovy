package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.KategoriType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 5: Kontroll om barnet er over 7 år og er i barnehage

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 7 år og tiltakets kategori er '4.1' Barnehage<br/>
så gi feilmelding "Barnet er over 7 år og i barnehage."

Alvorlighetsgrad: Warning
""")
class MeasureAgeAboveSevenAndInKindergartenSpec extends Specification {

    @Subject
    MeasureAgeAboveSevenAndInKindergarten sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureAgeAboveSevenAndInKindergarten()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.fodselsnummer = getMockSocialSecurityNumber(age)
        and:
        sak.tiltak = List.of(sak.tiltak[0])
        and:
        sak.tiltak[0].kategori = new KategoriType(code, "~presisering~")

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
            assert reportEntries[0].errorText.contains("Barnet er over 7 år og i barnehage")
        }

        where:
        age | code     || errorExpected
        7   | "~kode~" || false
        8   | "~kode~" || false
        7   | "4.1"    || false
        7   | "4.1"    || false
        8   | "4.1"    || true
        8   | "4.2"    || false
    }
}
