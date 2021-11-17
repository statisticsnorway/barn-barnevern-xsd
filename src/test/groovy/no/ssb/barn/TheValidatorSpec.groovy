package no.ssb.barn

import spock.lang.Specification

class TheValidatorSpec extends Specification {

    def "when create, receive validator instance"() {
        expect:
        TheValidator.create() instanceof TheValidator
    }

    def "when validate with valid params, receive JSON result"() {
        given:
        def sut = TheValidator.create()

        when:
        def result = sut.validate(1, "~xmlBody~")

        then:
        "{ hello: \"world\" }" == result
    }
}
