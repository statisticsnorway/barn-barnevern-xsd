package no.ssb.barn.converter

import no.ssb.barn.generator.TestDataGenerator
import spock.lang.Specification
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class BarnevernConverterSpec extends Specification {

    @Unroll("test01_fil0 #i .xml")
    def "when unmarshalling valid XML, receive populated instance"() {
        given:
        def xml = getResourceAsString("test01_fil0" + i + ".xml")

        when:
        def barnevernType = BarnevernConverter.unmarshallXml(xml)

        then:
        noExceptionThrown()
        and:
        null != barnevernType
        and:
        null != barnevernType.avgiver
        and:
        null != barnevernType.datoUttrekk
        and:
        null != barnevernType.fagsystem
        and:
        null != barnevernType.sak
        and:
        barnevernType.sak.virksomhet.any()
        and:
        barnevernType.sak.virksomhet[0].melding.any()
        and:
        !barnevernType.sak.virksomhet[0].ettervern.any()

        where:
        i << (1..9)
    }

    def "when marshalling instance to XML, xml is valid"() {
        given:
        def instance = new TestDataGenerator().createInitialMutation()

        when:
        def xml = BarnevernConverter.marshallXml(instance)

        then:
        noExceptionThrown()
        and:
        null != xml
    }
}
