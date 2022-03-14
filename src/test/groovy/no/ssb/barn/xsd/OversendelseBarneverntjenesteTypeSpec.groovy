package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.ZonedDateTime

class OversendelseBarneverntjenesteTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelseBarneverntjenesteType(
                UUID.randomUUID(), ZonedDateTime.now(), new LovhjemmelType(), List.of())

        then:
        noExceptionThrown()
    }
}
