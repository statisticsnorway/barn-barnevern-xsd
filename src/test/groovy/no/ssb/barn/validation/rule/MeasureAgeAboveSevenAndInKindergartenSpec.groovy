package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.KategoriType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

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
            assert reportEntries[0].errorText.contains("Barnet er over 7 år og i barnehage")
        }

        where:
        age | createKategori | code     || errorExpected
        7   | false          | ""       || false
        8   | false          | ""       || false
        7   | true           | "~kode~" || false
        8   | true           | "~kode~" || false
        7   | true           | "4.1"    || false
        7   | true           | "4.1"    || false
        8   | true           | "4.1"    || true
        8   | true           | "4.2"    || false
    }
}
