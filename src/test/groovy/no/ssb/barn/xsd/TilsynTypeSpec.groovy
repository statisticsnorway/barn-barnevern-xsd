package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.ZonedDateTime

class TilsynTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new TilsynType(UUID.randomUUID(), ZonedDateTime.now())

        then:
        noExceptionThrown()
    }
}
