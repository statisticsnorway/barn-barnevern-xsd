package no.ssb.barn.converter

import spock.lang.Specification

import java.time.LocalDateTime

class LocalDateTimeAdapterSpec extends Specification {

    def dateTime = LocalDateTime.of(2020, 1, 31, 23, 59, 1)
    def dateTimeString = "2020-01-31T23:59:01"

    def "Marshal"() {
        expect:
        dateTime == new LocalDateTimeAdapter().unmarshal(dateTimeString)
    }

    def "Unmarshal"() {
        expect:
        dateTimeString == new LocalDateTimeAdapter().marshal(dateTime)
    }
}
