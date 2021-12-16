package no.ssb.barn.xsd

import spock.lang.Specification

class TilsynTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new TilsynType()

        then:
        noExceptionThrown()
    }
}
