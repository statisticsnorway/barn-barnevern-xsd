package no.ssb.barn.converter

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class LocalDateTimeAdapterSpec extends Specification {

    @Shared
    def dateTime = ZonedDateTime.of(LocalDateTime.of(2020, 1, 31, 23, 59, 1), ZoneId.of("+01:00"))

    @Shared
    def dateTimeString = "2020-01-31T23:59:01+01:00"

    def "marshal with valid datetime, expect serialized datetime"() {
        expect:
        dateTimeString == new LocalDateTimeAdapter().marshal(dateTime)
    }

    def "marshal with null, expect null"() {
        expect:
        null == new LocalDateTimeAdapter().marshal(null)
    }

    @Unroll
    def "unmarshal all scenarios"() {
        expect:
        expected == new LocalDateTimeAdapter().unmarshal(currDateTimeString)

        where:
        currDateTimeString || expected
        dateTimeString      | dateTime
        ""                  | null
        null                | null
    }
}
