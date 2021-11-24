package no.ssb.barn.deserialize

import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Unroll

class BarnevernDeserializerSpec extends Specification {

    @Unroll("test01_fil0 #i .xml")
    def "when unmarshalling valid XML, receive populated instance"() {
        given:
        def xmlStream = TestDataProvider.getResourceAsStream("test01_fil0" + i + ".xml")

        when:
        def barnevernType = BarnevernDeserializer.unmarshallXml(xmlStream)

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

        where:
        i << (1..9)
    }

    def "when unmarshalling valid XML as String, receive populated instance"() {
        given:
        def xml = TestDataProvider.getResourceAsString("test01_fil01.xml")

        when:
        def barnevernType = BarnevernDeserializer.unmarshallXml(xml)

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
    }

    def "when unmarshalling valid XML with XSD, receive populated instance"() {
        given:
        def xsdStream = TestDataProvider.getResourceAsStream("Barnevern.xsd")
        and:
        def xmlStream = TestDataProvider.getResourceAsStream("test01_fil01.xml")

        when:
        def barnevernType = BarnevernDeserializer.unmarshallXmlWithSchema(xsdStream, xmlStream)

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
    }
}
