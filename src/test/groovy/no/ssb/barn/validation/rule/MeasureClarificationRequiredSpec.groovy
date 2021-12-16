package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.KategoriType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

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
        sak.virksomhet[0].tiltak = List.of(sak.virksomhet[0].tiltak[0])
        and:
        sak.virksomhet[0].tiltak[0].kategori = null
        and:
        if (createKategori) {
            sak.virksomhet[0].tiltak[0].kategori = new KategoriType(
                    "~code~", clarification
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
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("(~code~) mangler presisering")
        }

        where:
        createKategori | clarification   || errorExpected
        false          | null            || false
        true           | null            || true
        true           | ""              || true
        true           | "~presisering~" || false
    }
}
