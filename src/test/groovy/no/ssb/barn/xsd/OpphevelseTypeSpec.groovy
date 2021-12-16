package no.ssb.barn.xsd

import spock.lang.Specification

class OpphevelseTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OpphevelseType()

        then:
        noExceptionThrown()
    }
}
