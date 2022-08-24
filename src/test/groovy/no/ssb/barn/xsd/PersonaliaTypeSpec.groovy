package no.ssb.barn.xsd


import spock.lang.Specification

import java.time.LocalDate

class PersonaliaTypeSpec extends Specification {
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
        def personalia = new PersonaliaType(
                id,
                startDate,
                ssn,
                birthDate,
                gender,
                municipalityOfResidens,
                dufNumber
        )

        then:
        noExceptionThrown()
        and:
        personalia.id == id
        and:
        personalia.startDato == startDate
        and:
        personalia.fodselsnummer == ssn
        and:
        personalia.fodseldato == birthDate
        and:
        personalia.kjonn == gender
        and:
        personalia.bostedskommunenummer == municipalityOfResidens
        and:
        personalia.duFnummer == dufNumber

        where:
        id                | startDate       | ssn           | birthDate                | gender | municipalityOfResidens | dufNumber
        UUID.randomUUID() | LocalDate.now() | "01012012345" | LocalDate.of(2020, 1, 1) | "1"    | null                   | null
        UUID.randomUUID() | LocalDate.now() | "01012012345" | LocalDate.of(2020, 1, 1) | "1"    | "3401"                 | "202012345684"
    }
}
