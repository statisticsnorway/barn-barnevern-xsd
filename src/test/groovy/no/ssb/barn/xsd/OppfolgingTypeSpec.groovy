package no.ssb.barn.xsd

import spock.lang.Specification

class OppfolgingTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OppfolgingType()

        then:
        noExceptionThrown()
    }
}
