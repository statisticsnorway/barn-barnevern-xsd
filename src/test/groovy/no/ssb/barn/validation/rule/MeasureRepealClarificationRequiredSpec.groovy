package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.OpphevelseType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

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
        if (createRepeal) {
            context.rootObject.sak.virksomhet[0].tiltak[0].opphevelse =
                    new OpphevelseType("~kode~", clarification)
        }

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
        createRepeal | clarification   || errorExpected
        false        | null            || false
        true         | null            || true
        true         | ""              || true
        true         | "~presisering~" || false
    }
}
