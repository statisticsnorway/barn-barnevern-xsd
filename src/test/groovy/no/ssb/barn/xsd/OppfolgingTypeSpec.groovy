package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OppfolgingTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OppfolgingType(UUID.randomUUID(), LocalDate.now())

        then:
        noExceptionThrown()
    }
}
