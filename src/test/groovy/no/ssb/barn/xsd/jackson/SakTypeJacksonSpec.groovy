package no.ssb.barn.xsd.jackson

import spock.lang.Specification

import java.time.LocalDate

class SakTypeJacksonSpec extends Specification {

    def dateInTest = LocalDate.now()
    def uuidInTest = UUID.randomUUID()

    def "tests all properties"() {
        when: "constructing an instance with all fields set"
        def sut = new SakTypeJackson(
                uuidInTest,
                "~migrertId~",
                dateInTest,
                null,
                "~journalnummer~",
                "12345612345",
                "123456123456",
                dateInTest,
                "1",
                true
        )

        then: "all fields should have value"
        verifyAll(sut) {
            it.id == uuidInTest
            it.migrertId == "~migrertId~"
            it.startDato == dateInTest
            it.journalnummer == "~journalnummer~"
            it.fodselsnummer == "12345612345"
            it.duFnummer == "123456123456"
            it.fodseldato == dateInTest
            it.kjonn == "1"
            it.avsluttet
        }
    }
}
