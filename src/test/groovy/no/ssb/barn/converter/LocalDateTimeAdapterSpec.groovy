package no.ssb.barn.converter

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class LocalDateTimeAdapterSpec extends Specification {

    def dateTime = ZonedDateTime.of(LocalDateTime.of(2020, 1, 31, 23, 59, 1), ZoneId.of("+01:00"))
    def dateTimeString = "2020-01-31T23:59:01+01:00"

    def "marshal with valid datetime, expect serialized datetime"() {
        expect:
        dateTimeString == new LocalDateTimeAdapter().marshal(dateTime)
    }

    def "marshal with null, expect null"() {
        expect:
        null == new LocalDateTimeAdapter().marshal(null)
    }

    def "unmarshal with valid datetime string, expect deserialized datetime"() {
        expect:
        dateTime == new LocalDateTimeAdapter().unmarshal(dateTimeString)
    }

    def "unmarshal with null, expect null"() {
        expect:
        null == new LocalDateTimeAdapter().unmarshal(null)
    }
}
