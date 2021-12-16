package no.ssb.barn.xsd

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class UndersokelseUtvidetFristTypeSpec extends Specification {

    def "when constructor is called without values, expect no errors"() {
        when:
        new UndersokelseUtvidetFristType()

        then:
        noExceptionThrown()
    }

    @Unroll
    def "getCodes receive number of expected items"() {
        expect:
        expectedNumberOfItems == UndersokelseUtvidetFristType.getInnvilget(date).size()

        where:
        date                     || expectedNumberOfItems
        LocalDate.of(2021, 1, 1) || 0
        LocalDate.of(2022, 1, 1) || 2
        LocalDate.of(2022, 6, 1) || 2
        LocalDate.of(2101, 1, 1) || 0
    }
}
