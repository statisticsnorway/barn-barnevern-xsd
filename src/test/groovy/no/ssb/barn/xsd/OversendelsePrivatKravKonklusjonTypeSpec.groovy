package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OversendelsePrivatKravKonklusjonTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelsePrivatKravKonklusjonType(LocalDate.now())

        then:
        noExceptionThrown()
    }
}
