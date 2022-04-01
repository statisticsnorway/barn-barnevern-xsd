package no.ssb.barn.xsd

import spock.lang.Specification

import java.time.LocalDate

class OversendelsePrivatKravKonklusjonTypeSpec extends Specification {

    def "when constructor no exceptions expected"() {
        given:
        def date = LocalDate.now()

        when:
        def sut = new OversendelsePrivatKravKonklusjonType(date)

        then:
        noExceptionThrown()
        and:
        date == sut.sluttDato
    }
}
