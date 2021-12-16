package no.ssb.barn.xsd

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class KategoriTypeSpec extends Specification {

    def "when constructor is called without values, expect no errors"() {
        when:
        def sut = new KategoriType()

        then:
        noExceptionThrown()
        and:
        null != sut.kode
        and:
        null == sut.presisering
    }

    @Unroll
    def "getCodes receive number of expected items"() {
        expect:
        expectedNumberOfItems == KategoriType.getCodes(date).size()

        where:
        date                       || expectedNumberOfItems
        LocalDate.of(2012, 12, 31) || 0
        LocalDate.of(2013, 1, 1)   || 49
        LocalDate.of(2013, 6, 1)   || 49
        LocalDate.of(2101, 1, 1)   || 0
    }
}
