package no.ssb.barn.converter

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.generator.RandomUtils
import no.ssb.barn.generator.InitialMutationProvider
import no.ssb.barn.validation.rule.XsdRule
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.FagsystemType
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.MeldingType
import no.ssb.barn.xsd.SakType
import no.ssb.barn.xsd.SaksinnholdType
import no.ssb.barn.xsd.VirksomhetType
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.bind.UnmarshalException
import java.time.LocalDate
import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class BarnevernConverterSpec extends Specification {

    def xsdRule = new XsdRule("Barnevern.xsd")

    def "when unmarshalling invalid XML, receive exception"(){
        when:
        BarnevernConverter.unmarshallXml("~someXml~")

        then:
        //noinspection GroovyUnusedAssignment
        UnmarshalException e = thrown()
    }

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

    def VALIDATION_REPORT_JSON = '{ "someValue": 1, "myList": [4, 8, 15, 16, 23, 42] }'

    def "when unmarshalling instance to map, map is not null"() {
        given:
        def xml = getResourceAsString("test01_fil09.xml")

        when:
        def map = BarnevernConverter.unmarshallXmlAndValidationReportToMap(xml, VALIDATION_REPORT_JSON)

        then:
        noExceptionThrown()
        and:
        null != map
    }

    def "when marshalling instance to XML, xml is valid"() {
        given:
        def instance = InitialMutationProvider.createInitialMutation(LocalDate.now())

        when:
        def xml = BarnevernConverter.marshallInstance(instance)

        then:
        noExceptionThrown()
        and:
        null != xml
        and:
        null == xsdRule.validate(new ValidationContext("N/A", xml))
    }

    def "should convert XML to objects, JAXB.unmarshal()"() {
        given:
        def xmlString = """<?xml version=\"1.0\" encoding=\"UTF-8\" ?>
                <Barnevern xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" DatoUttrekk=\"2021-01-01T13:22:00\">
                    <Fagsystem Versjon=\"1.0\" Navn=\"OJJ's Manuell Touch\" Leverandor=\"SSB\"/>
                    <Avgiver Kommunenummer=\"3401\" Kommunenavn=\"Kongsvinger kommune\" Organisasjonsnummer=\"944117784\"/>
                    <Sak StartDato=\"2021-01-01\" Id=\"1\" Journalnummer=\"test1\" Fodselsnummer=\"02011088123\">
                        <Virksomhet StartDato=\"2021-01-01\" Organisasjonsnummer=\"944117784\">
                            <Melding StartDato=\"2021-01-01\" Id=\"1\">
                                <Melder Kode=\"1\"/>
                                <Saksinnhold Kode=\"1\"/>
                            </Melding>
                        </Virksomhet>
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

    def "should convert objects to XML, JAXB.marshal()"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2020, 2, 2, 10, 10, 10, 0)
        LocalDate date = LocalDate.of(2020, 2, 2)

        def melder = new MelderType(MelderType.getRandomCode(date), null)
        def saksinnhold = new SaksinnholdType(SaksinnholdType.getRandomCode(date), null)
        def melding = new MeldingType(
                UUID.randomUUID(),
                null,
                date,
                List.of(melder),
                List.of(saksinnhold),
                null
        )
        def virksomhet = new VirksomhetType(
                date,
                null,
                "944117784",
                null,
                null,
                null,
                List.of(melding),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        )
        def sak = new SakType(
                UUID.randomUUID(),
                null,
                date,
                null,
                RandomUtils.generateRandomString(9),
                "02011088123",
                null,
                null,
                List.of(virksomhet)
        )
        def avgiver = RandomUtils.generateRandomAvgiverType()
        def fagsystem = new FagsystemType(
                "SSB", "OJJ's automatiske touch", "0.0.1"
        )
        def barnevern = new BarnevernType(
                datetime,
                fagsystem,
                avgiver,
                sak
        )

        when:
        def xmlString = BarnevernConverter.marshallInstance(barnevern)

        then:
        verifyAll(xmlString) {
            contains("SSB")
        }
    }
}
