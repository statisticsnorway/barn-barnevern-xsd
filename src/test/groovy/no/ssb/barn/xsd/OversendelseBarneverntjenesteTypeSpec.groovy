package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OversendelseBarneverntjenesteTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        when:
        new OversendelseBarneverntjenesteType(
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
