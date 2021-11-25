package no.ssb.barn.deserialize

import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Unroll

class BarnevernDeserializerSpec extends Specification {

    @Unroll("test01_fil0 #i .xml")
    def "when unmarshalling valid XML, receive populated instance"() {
        given:
        def xml = TestDataProvider.getResourceAsString("test01_fil0" + i + ".xml")

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

        where:
        i << (1..9)
    }
}
