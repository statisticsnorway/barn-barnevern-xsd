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
}
