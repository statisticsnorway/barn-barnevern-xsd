package no.ssb.barn.converter

import spock.lang.Specification
import spock.lang.Subject

class UuidAdapterSpec extends Specification {

    @Subject
    UuidAdapter sut

    UUID uuid

    def setup() {
        sut = new UuidAdapter()
        uuid = UUID.randomUUID()
    }

    def "marshal"() {
        expect:
        uuid.toString() == sut.marshal(uuid)
    }

    def "unmarshal"() {
        expect:
        uuid == sut.unmarshal(uuid.toString())
    }
}
