package no.ssb.barn.xsd

import spock.lang.Specification

class OversendelseBarneverntjenesteTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelseBarneverntjenesteType()

        then:
        noExceptionThrown()
    }
}
