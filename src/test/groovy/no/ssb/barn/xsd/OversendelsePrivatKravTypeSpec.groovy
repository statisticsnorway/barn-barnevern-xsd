package no.ssb.barn.xsd

import spock.lang.Specification

class OversendelsePrivatKravTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelsePrivatKravType()

        then:
        noExceptionThrown()
    }
}
