package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class TilsynTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new TilsynType(UUID.randomUUID(), LocalDate.now())

        then:
        noExceptionThrown()
    }
}
