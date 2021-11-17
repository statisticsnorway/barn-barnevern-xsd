package no.ssb.barn.validation2

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
        result.startsWith("{\"journalId\"")
        and:
        result.contains("warningLevel\":\"FATAL\"")
    }
}