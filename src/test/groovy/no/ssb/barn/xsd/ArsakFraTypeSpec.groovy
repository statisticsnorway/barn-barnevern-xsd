package no.ssb.barn.xsd

import spock.lang.Specification

class ArsakFraTypeSpec extends Specification {

    def "when constructor is called without presisering, expect no errors"() {
        when:
        new ArsakFraType("1.1.1", null)

        then:
        noExceptionThrown()
    }

    def "when constructor is called with presisering, expect props to be populated"() {
        given:
        def sut = new ArsakFraType("1.1.1", "~Presisering~")

        expect:
        "1.1.1" == sut.kode
        and:
        "~Presisering~" == sut.presisering
    }
}
