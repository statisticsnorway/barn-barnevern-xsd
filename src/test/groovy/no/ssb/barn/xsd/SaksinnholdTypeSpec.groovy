package no.ssb.barn.xsd

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class SaksinnholdTypeSpec extends Specification {

    @Unroll
    def "getCodes"() {
        expect:
        expectedNumberOfItems == SaksinnholdType.getCodes(date).size()

        where:
        date || expectedNumberOfItems
        LocalDate.of(2012, 1, 1) || 0
        LocalDate.of(2021, 1, 1) || 25
        LocalDate.of(2101, 1, 1) || 0
    }

    def "harPresisering"() {
        expect:
        expectedResult == SaksinnholdType.harPresisering(date)

        where:
        date || expectedResult
        LocalDate.of(2012, 1, 1) || false
        LocalDate.of(2021, 1, 1) || true
        LocalDate.of(2101, 1, 1) || false
    }

    def "getRandomCode"() {
        expect:
        expectedResult == ("" != SaksinnholdType.getRandomCode(date))

        where:
        date || expectedResult
        LocalDate.of(2012, 1, 1) || false
        LocalDate.of(2021, 1, 1) || true
        LocalDate.of(2101, 1, 1) || false
    }
}
