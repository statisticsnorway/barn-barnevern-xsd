package no.ssb.barn.xsd

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class ArsakFraTypeSpec extends Specification {

    def "when constructor is called expect props to be populated"() {
        given:
        def code = ArsakFraType.getCodes(
                LocalDate.of(2022, 1, 1))[0].code
        and:
        def sut = new ArsakFraType(code, "~Presisering~")

        expect:
        code == sut.kode
        and:
        "~Presisering~" == sut.presisering
    }

    @Unroll
    def "getCodes receive number of expected items"() {
        expect:
        expectedNumberOfItems == ArsakFraType.getCodes(date).size()

        where:
        date                     || expectedNumberOfItems
        LocalDate.of(2021, 1, 1) || 0
        LocalDate.of(2022, 1, 1) || 18
        LocalDate.of(2022, 6, 1) || 18
        LocalDate.of(2101, 1, 1) || 0
    }
}
