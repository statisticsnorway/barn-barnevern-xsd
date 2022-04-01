package no.ssb.barn.xsd

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class FlyttingTypeSpec extends Specification {

    def "when constructor is called without values, expect no errors"() {
        when:
        def sut = new FlyttingType(
                UUID.randomUUID(),
                null,
                LocalDate.now(),
                new ArsakFraType("kode", null),
                new FlyttingTilType("kode", null))

        then:
        noExceptionThrown()
        and:
        null != sut.id
        and:
        null == sut.migrertId
        and:
        null != sut.sluttDato
    }

    @Unroll
    def "getCodes receive number of expected items"() {
        expect:
        expectedNumberOfItems == FlyttingType.getCodes(date).size()

        where:
        date                       || expectedNumberOfItems
        LocalDate.of(2012, 12, 31) || 0
        LocalDate.of(2013, 1, 1)   || 8
        LocalDate.of(2013, 6, 1)   || 8
        LocalDate.of(2101, 1, 1)   || 0
    }
}
