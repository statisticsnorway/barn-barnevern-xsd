package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class PersonaliaTypeSpec  extends Specification {
    def "when constructor is called without values, expect exception"() {
        when:
        new PersonaliaType(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )

        then:
        thrown(NullPointerException)
    }

    def "when constructor is called with mandatory values, expect no errors"() {
        when:
        new PersonaliaType(
                UUID.randomUUID(),
                LocalDate.now(),
                "01012012345",
                LocalDate.of(2020, 1, 1),
                "1",
                null,
                null
        )

        then:
        noExceptionThrown()
    }
}
