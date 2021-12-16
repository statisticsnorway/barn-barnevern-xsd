package no.ssb.barn.xsd

import spock.lang.Specification

class BegrepsTypeSpec extends Specification {

    def "when creating instance, no exceptions expected"() {
        when:
        def sut = BegrepsType.PLAN

        then:
        noExceptionThrown()
        and:
        "Plan" == sut.begrep
    }

    def "when fromValue with valid key, no exception is thrown"() {
        given:
        def sut = BegrepsType.VEDTAK

        when:
        sut.begrepsType("~begrep~")

        then:
        noExceptionThrown()
        and:
        "~begrep~" == sut.value()
        and:
        null != sut.fromValue("~begrep~")
    }

    def "when fromValue with invalid key, exception is thrown"() {
        given:
        def sut = BegrepsType.TILTAK

        when:
        sut.fromValue("~nonExisting~")

        then:
        IllegalArgumentException e = thrown()
    }
}
