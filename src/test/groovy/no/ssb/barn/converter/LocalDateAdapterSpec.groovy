package no.ssb.barn.converter

import spock.lang.Specification

import java.time.LocalDate

class LocalDateAdapterSpec extends Specification {

    def date = LocalDate.of(2020, 1, 31)
    def dateString = "2020-01-31"

    def "Marshal"() {
        expect:
        date == new LocalDateAdapter().unmarshal(dateString)
    }

    def "Unmarshal"() {
        expect:
        dateString == new LocalDateAdapter().marshal(date)
    }
}
