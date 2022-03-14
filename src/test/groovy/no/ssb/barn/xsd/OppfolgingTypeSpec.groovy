package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.ZonedDateTime

class OppfolgingTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OppfolgingType(UUID.randomUUID(), ZonedDateTime.now())

        then:
        noExceptionThrown()
    }
}
