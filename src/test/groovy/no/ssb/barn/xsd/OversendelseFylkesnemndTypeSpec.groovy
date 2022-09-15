package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OversendelseFylkesnemndTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelseFylkesnemndType(
                UUID.randomUUID(),
                LocalDate.now(),
                new LovhjemmelType(
                        "lov", "kapittel", "paragraf", ["ledd"], ["bokstav"], ["punktum"]
                ),
                [])

        then:
        noExceptionThrown()
    }
}
