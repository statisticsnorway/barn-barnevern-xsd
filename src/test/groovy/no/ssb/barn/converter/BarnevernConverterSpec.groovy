package no.ssb.barn.converter

import com.fasterxml.jackson.core.JsonParseException
import spock.lang.Specification
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class BarnevernConverterSpec extends Specification {

    def "when unmarshalling invalid XML, receive exception"(){
        when:
        BarnevernConverter.unmarshallXml("~someXml~")

        then:
        //noinspection GroovyUnusedAssignment
        JsonParseException e = thrown()
    }

    @Unroll("test01_fil0 #i _total.xml")
    def "when unmarshalling valid XML, receive populated instance"() {
        given:
        def xml = getResourceAsString("test01_file0" + i + "_total.xml")

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
        barnevernType.sak.melding.any()
        and:
        !barnevernType.sak.ettervern.any()

        where:
        i << (1..9)
    }

    def VALIDATION_REPORT_JSON = '{ "someValue": 1, "myList": [4, 8, 15, 16, 23, 42] }'

    def "when unmarshalling instance to map, map is not null"() {
        given:
        def xml = getResourceAsString("test01_file09_total.xml")

        when:
        def map = BarnevernConverter.unmarshallXmlAndValidationReportToMap(xml, VALIDATION_REPORT_JSON)

        then:
        noExceptionThrown()
        and:
        null != map
    }

    def "when unmarshalling instance to json, json is not null"() {
        given:
        def xml = getResourceAsString("test01_file09_total.xml")

        when:
        def json = BarnevernConverter.unmarshallXmlToJson(xml)

        then:
        noExceptionThrown()
        and:
        null != json
    }

    def "should convert XML to objects, JAXB.unmarshal()"() {
        given:
        def xmlString = """<?xml version=\"1.0\" encoding=\"UTF-8\" ?>
                <Barnevern Id="8ed88ce5-07cc-40ff-8e3d-b8d9a2421de6" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" DatoUttrekk=\"2021-01-01T13:22:00+01:00\">
                    <Fagsystem Versjon=\"1.0\" Navn=\"OJJ's Manuell Touch\" Leverandor=\"SSB\"/>
                    <Avgiver Kommunenummer=\"3401\" Kommunenavn=\"Kongsvinger kommune\" Organisasjonsnummer=\"944117784\"/>
                    <Sak StartDato=\"2021-01-01\" Id=\"7317a453-2d65-465a-9589-519311d66579\" Journalnummer=\"test1\" Fodselsnummer=\"02011088123\" Fodseldato=\"2010-01-02\" Kjonn=\"1\">
                        <Melding StartDato=\"2021-01-01\" Id=\"7317a453-2d65-465a-9589-519311d66579\">
                            <Melder Kode=\"1\"/>
                            <Saksinnhold Kode=\"1\"/>
                        </Melding>
                    </Sak>
                </Barnevern>"""

        when:
        def barnevernType = BarnevernConverter.unmarshallXml(xmlString)

        then:
        verifyAll(barnevernType) {
            getDatoUttrekk().getYear() == 2021
            getFagsystem().getLeverandor().equalsIgnoreCase("SSB")
            getAvgiver().getOrganisasjonsnummer().equalsIgnoreCase("944117784")
        }
    }
}
