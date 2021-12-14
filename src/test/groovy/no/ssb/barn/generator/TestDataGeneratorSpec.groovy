package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Subject

class TestDataGeneratorSpec extends Specification {

    @Subject
    TestDataGenerator sut

    def "when calling createInitialMutation receive instance"() {
        given:
        sut = new TestDataGenerator()

        when:
        def instance = sut.createInitialMutation()

        then:
        def xml = BarnevernConverter.marshallXml(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)
        System.out.println(BarnevernConverter.unmarshallXml(xml))
    }
}
