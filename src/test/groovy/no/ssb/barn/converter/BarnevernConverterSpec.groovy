package no.ssb.barn.converter

import no.ssb.barn.generator.InitialMutationProvider
import no.ssb.barn.generator.RandomUtils
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.XsdRule
import no.ssb.barn.xsd.*
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.bind.UnmarshalException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

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
        def xml = getResourceAsString("test01_fil09.xml")

        when:
        def json = BarnevernConverter.unmarshallXmlToJson(xml)

        then:
        noExceptionThrown()
        and:
        null != json
    }

    def "when marshalling instance to XML, xml is valid"() {
        given:
        def instance = InitialMutationProvider.createInitialMutation(ZonedDateTime.now())

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
                <Barnevern xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" DatoUttrekk=\"2021-01-01T13:22:00+01:00\">
                    <Fagsystem Versjon=\"1.0\" Navn=\"OJJ's Manuell Touch\" Leverandor=\"SSB\"/>
                    <Avgiver Kommunenummer=\"3401\" Kommunenavn=\"Kongsvinger kommune\" Organisasjonsnummer=\"944117784\"/>
                    <Sak StartDato=\"2021-01-01\" Id=\"1\" Journalnummer=\"test1\" Fodselsnummer=\"02011088123\">
                        <Melding StartDato=\"2021-01-01T13:22:00+01:00\" Id=\"1\">
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

    def "should convert objects to XML, JAXB.marshal()"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2020, 2, 2, 10, 10, 10, 0)
        LocalDate date = LocalDate.of(2020, 2, 2)

        def melder = new MelderType(MelderType.getRandomCode(date), null)
        def saksinnhold = new SaksinnholdType(SaksinnholdType.getRandomCode(date), null)
        def melding = new MeldingType(
                UUID.randomUUID(),
                null,
                ZonedDateTime.of(datetime, ZoneId.systemDefault()),
                List.of(melder),
                List.of(saksinnhold),
                null
        )

        def sak = new SakType(
                UUID.randomUUID(),
                null,
                ZonedDateTime.of(datetime, ZoneId.systemDefault()),
                null,
                RandomUtils.generateRandomString(9),
                "02011088123",
                null,
                LocalDate.now().minusYears(2),
                "1",
                null,
                List.of(melding),
                List<UndersokelseType>.of(),
                List<PlanType>.of(),
                List<TiltakType>.of(),
                List<VedtakType>.of(),
                List<EttervernType>.of(),
                List<OversendelseBarneverntjenesteType>.of(),
                List<RelasjonType>.of()
        )
        def avgiver = RandomUtils.generateRandomAvgiverType()
        def fagsystem = new FagsystemType(
                "SSB", "OJJ's automatiske touch", "0.0.1"
        )
        def barnevern = new BarnevernType(
                UUID.randomUUID(),
                null,
                ZonedDateTime.of(datetime, ZoneId.systemDefault()),
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
