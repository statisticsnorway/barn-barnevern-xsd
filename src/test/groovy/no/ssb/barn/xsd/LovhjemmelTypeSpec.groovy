package no.ssb.barn.xsd

import spock.lang.Specification

class LovhjemmelTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new LovhjemmelType("lov", "kapittel", "paragraf", ["ledd"], ["punktum"])

        then:
        noExceptionThrown()
    }
}
