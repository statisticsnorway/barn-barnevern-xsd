package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class FlyttingTypeSpec extends Specification {

    def "when constructor is called without values, expect no errors"() {
        when:
        def sut = new FlyttingType(
                UUID.randomUUID(),
                null,
                LocalDate.now(),
                new ArsakFraType("kode", null),
                new FlyttingTilType("kode", null))

        then:
        noExceptionThrown()
        and:
        null != sut.id
        and:
        null == sut.migrertId
        and:
        null != sut.sluttDato
    }
}
