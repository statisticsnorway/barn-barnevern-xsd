package no.ssb.barn.converter

import spock.lang.Specification

import java.time.LocalDate

class LocalDateAdapterSpec extends Specification {

    def date = LocalDate.of(2020, 1, 31)
    def dateString = "2020-01-31"

    def "marshal with valid date, expect serialized date"() {
        expect:
        dateString == new LocalDateAdapter().marshal(date)
    }

    def "marshal with null, expect null"() {
        expect:
        null == new LocalDateAdapter().marshal(null)
    }

    def "unmarshal with valid date string, expect deserialized date"() {
        expect:
        date == new LocalDateAdapter().unmarshal(dateString)
    }
}
