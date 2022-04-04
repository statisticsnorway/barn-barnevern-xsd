package no.ssb.barn.xsd

import no.ssb.barn.util.RandomUtils
import spock.lang.Specification

class OpphevelseTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OpphevelseType("1", null)

        then:
        noExceptionThrown()
    }
}
