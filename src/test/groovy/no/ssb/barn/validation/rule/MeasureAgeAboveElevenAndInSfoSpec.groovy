package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.testutil.TestDataProvider
import no.ssb.barn.xsd.KategoriType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber

class MeasureAgeAboveElevenAndInSfoSpec extends Specification {

    @Subject
    MeasureAgeAboveElevenAndInSfo sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureAgeAboveElevenAndInSfo()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.fodselsnummer = getMockSocialSecurityNumber(age)
        and:
        sak.virksomhet[0].tiltak = List.of(sak.virksomhet[0].tiltak[0])
        and:
        sak.virksomhet[0].tiltak[0].kategori = null
        and:
        if (createKategori) {
            sak.virksomhet[0].tiltak[0].kategori = new KategoriType(
                    code, "~presisering~"
            )
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
            assert reportEntries[0].errorText.contains("Barnet er over 11 år og i SFO")
        }

        where:
        age | createKategori | code     || errorExpected
        11   | false          | ""       || false
        12   | false          | ""       || false
        11   | true           | "~kode~" || false
        12   | true           | "~kode~" || false
        11   | true           | "4.2"    || false
        11   | true           | "4.2"    || false
        12   | true           | "4.2"    || true
        12   | true           | "4.3"    || false
    }
}
