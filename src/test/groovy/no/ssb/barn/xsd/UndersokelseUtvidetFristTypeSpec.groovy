package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class UndersokelseUtvidetFristTypeSpec extends Specification {

    def "when constructor is called with values, expect no errors"() {
        when:
        new UndersokelseUtvidetFristType(LocalDate.now(), null)

        then:
        noExceptionThrown()
    }
}
