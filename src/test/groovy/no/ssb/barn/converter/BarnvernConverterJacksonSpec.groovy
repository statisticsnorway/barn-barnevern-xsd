package no.ssb.barn.converter

import no.ssb.barn.testutil.TestDataProvider
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.XsdRule
import spock.lang.Specification

import java.time.ZonedDateTime

@SuppressWarnings('SpellCheckingInspection')
class BarnvernConverterJacksonSpec extends Specification {

    def "unmarshallXml using Jackson, expect valid instance"() {
        when: "unmarshalling XML"
        def barnevernInstance = BarnvernConverterJackson.unmarshallXml(
                TestDataProvider.getResourceAsString("test01_file09_total.xml"))

        then: "barnevernInstance should not be null"
        null != barnevernInstance

        and: "all top level fields should be set"
        verifyAll(barnevernInstance) {
            it.id == UUID.fromString("1b1907ef-2080-42c8-b704-3b33bf0b0982")
            it.forrigeId == "b8c7cd5a-376b-405c-9f0f-2cf1002752d9"
            it.datoUttrekk.toEpochSecond() == ZonedDateTime.parse("2021-01-01T13:22:00+01:00").toEpochSecond()
        }

        and: "all fagsystem fields should be set"
        verifyAll(barnevernInstance.fagsystem) {
            it.versjon == "1.0"
            it.leverandor == "SSB"
            it.navn == "OJJ's Manuell Touch"
        }

        and: "there should be one instance om MeldingType"
        verifyAll(barnevernInstance.sak) {
            it.melding.size() == 1
        }
    }

    def "when marshallXml using Jackson, XML is valid"() {
        given: "a deserialized BarnevernTypeJackson instance"
        def barnevernInstance = BarnvernConverterJackson.unmarshallXml(
                TestDataProvider.getResourceAsString("test01_file09_total.xml"))
        and: "an XSD validator"
        def xsdValidator = new XsdRule("Barnevern.xsd")

        when: "serializing instance to XML"
        def xml = BarnvernConverterJackson.marshallInstance(barnevernInstance)
        and: "validating XML against XSD"
        def validationResult = xsdValidator.validate(
                new ValidationContext("~messageId~", xml))

        then: "validation result should be null"
        null == validationResult
    }
}
