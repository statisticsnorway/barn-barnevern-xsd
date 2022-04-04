package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OversendelsePrivatKravTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelsePrivatKravType(
                UUID.randomUUID(), LocalDate.now(), null
        )

        then:
        noExceptionThrown()
    }
}
