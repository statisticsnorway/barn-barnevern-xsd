package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class OversendelsePrivatKravKonklusjonTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        given:
        def date = LocalDateTime.now()

        when:
        def sut = new OversendelsePrivatKravKonklusjonType(date)

        then:
        noExceptionThrown()
        and:
        date == sut.sluttDato
    }
}
