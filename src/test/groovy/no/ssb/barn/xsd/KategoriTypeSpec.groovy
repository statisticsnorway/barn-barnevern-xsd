package no.ssb.barn.xsd

import spock.lang.Specification

class KategoriTypeSpec extends Specification {

    def "when constructor is called without values, expect no errors"() {
        when:
        def sut = new KategoriType("1", null)

        then:
        noExceptionThrown()
        and:
        null != sut.kode
        and:
        null == sut.presisering
    }
}
