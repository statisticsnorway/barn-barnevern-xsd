package no.ssb.barn.converter

import com.fasterxml.jackson.core.JsonParseException
import no.ssb.barn.util.RandomUtils
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.XsdRule
import no.ssb.barn.xsd.*
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString
import static no.ssb.barn.util.RandomUtils.generateRandomString
import static no.ssb.barn.util.SocialSecurityIdUtils.getDateOfBirthFromSsn
import static no.ssb.barn.util.SocialSecurityIdUtils.getGenderFromSsn

class BarnevernConverterSpec extends Specification {

    def "when unmarshallXml with invalid XML, receive exception"() {
        when:
        BarnevernConverter.unmarshallXml("~someXml~")

        then:
        //noinspection GroovyUnusedAssignment
        JsonParseException e = thrown()
    }

    @Unroll("test01_fil0 #i _total.xml")
    def "when unmarshallXml, receive populated instance"() {
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

    def "when unmarshallXmlAndValidationReportToMap, map is not null"() {
        given:
        def xml = getResourceAsString("test01_file09_total.xml")

        when:
        def map = BarnevernConverter.unmarshallXmlAndValidationReportToMap(xml, VALIDATION_REPORT_JSON)

        then:
        noExceptionThrown()
        and:
        null != map
    }

    def "when unmarshallXmlToJson, json is not null"() {
        given:
        def xml = getResourceAsString("test01_file09_total.xml")

        when:
        def json = BarnevernConverter.unmarshallXmlToJson(xml)

        then:
        noExceptionThrown()
        and:
        null != json
    }

    def "when unmarshallXml, expect valid instance"() {
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
            it.datoUttrekk.year == 2021
            it.fagsystem.leverandor == "SSB"
            it.avgiver.organisasjonsnummer == "944117784"
        }
    }

    def "when marshalling with MeldingType instance without MeldingKonklusjonType, XML is valid"() {
        given: "XML generated from a programmatically created BarnevernType"
        def xml = BarnevernConverter.marshallInstance(createBarnevernType())
        and: "a validation context"
        def context = new ValidationContext(UUID.randomUUID().toString(), xml)
        and: "a validation rule for performing the XML validation"
        def xsdRule = new XsdRule("Barnevern.xsd")

        when: "validating the XML"
        def result = xsdRule.validate(context)

        then: "there should be no errors"
        result == null
    }

    def createBarnevernType() {
        def currentDate = ZonedDateTime.now()

        def socialSecurityId = RandomUtils.generateRandomSSN(
                LocalDate.now().minusYears(20),
                LocalDate.now().minusYears(1))

        def melding = new MeldingType(
                UUID.randomUUID(),
                null,
                currentDate.toLocalDate(),
                [],
                [],
                null)

        def sak = new SakType(
                UUID.randomUUID(),
                null,
                currentDate.toLocalDate(),
                null,
                generateRandomString(20),
                socialSecurityId,
                null,
                getDateOfBirthFromSsn(socialSecurityId) as LocalDate,
                getGenderFromSsn(socialSecurityId) as String,
                null,
                [melding], [], [], [], [], [], [], [], [], []
        )

        return new BarnevernType(
                UUID.randomUUID(),
                null,
                ZonedDateTime.now(),
                new FagsystemType("Visma", "Flyt Barnevern", "1.0.0"),
                new AvgiverType(
                        "123456789",
                        "0301",
                        "OSLO",
                        null,
                        null),
                sak
        )
    }
}
