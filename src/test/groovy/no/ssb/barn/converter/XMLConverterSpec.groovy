package no.ssb.barn.converter

import no.ssb.barn.generator.RandomUtils
import no.ssb.barn.xsd.*
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class XMLConverterSpec extends Specification {
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
        def barnevernType = XMLConverter.xmlToBarnevernType(xmlString)

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
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
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
        def xmlString = XMLConverter.barnevernTypeToXml(barnevern)

        then:
        verifyAll(xmlString) {
            contains("SSB")
        }
    }
}
