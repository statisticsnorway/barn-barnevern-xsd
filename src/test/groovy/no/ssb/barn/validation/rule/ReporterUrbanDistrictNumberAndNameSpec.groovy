package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.AvgiverType
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.FagsystemType
import no.ssb.barn.xsd.SakType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Avgiver Kontroll 1: Bydelsnummer og bydelsnavn

Gitt at man har en virksomhet der Organisasjonsnummer er en av 958935420 (Oslo), 964338531 (Bergen), 942110464 (Trondheim) eller 964965226 (Stavanger)<br/>
når Bydelsnummer og Bydelsnavn mangler utfylling<br/>
så gi feilmeldingen "Virksomhetens Bydelsnummer og Bydelsnavn skal være utfylt"

Alvorlighetsgrad: ERROR
""")
class ReporterUrbanDistrictNumberAndNameSpec extends Specification {

    @Subject
    ReporterUrbanDistrictNumberAndName sut

    @SuppressWarnings('unused')
    def setup() {
        sut = new ReporterUrbanDistrictNumberAndName()
    }

    @Unroll("businessId: #businessId, urbanDistrictId: #urbanDistrictId, urbanDistrictName: #urbanDistrictName")
    def "Test av alle scenarier"() {
        given:
        def context = getTestContext(createBarnevernType(
                businessId,
                urbanDistrictId,
                urbanDistrictName))

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Bydelsnummer og/eller Bydelsnavn skal være utfylt")
        }

        where:
        businessId                                              | urbanDistrictId | urbanDistrictName || errorExpected
        "999999999"                                             | null            | null              || false

        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | null            | null              || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | ""              | ""                || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | null            | ""                || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | null            | "42"              || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | ""              | "42"              || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | ""              | null              || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | "42"            | null              || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | "42"            | ""                || true
        ReporterUrbanDistrictNumberAndName.OSLO_COMPANY_ID      | "01"            | "Name"            || false

        ReporterUrbanDistrictNumberAndName.BERGEN_COMPANY_ID    | "01"            | "Name"            || false
        ReporterUrbanDistrictNumberAndName.TRONDHEIM_COMPANY_ID | "01"            | "Name"            || false
    }

    def createBarnevernType(businessId, urbanDistrictId, urbanDistrictName) {
        new BarnevernType(
                UUID.randomUUID(),
                null,
                ZonedDateTime.now(),
                new FagsystemType("levarandør", "navn", "versjon"),
                createAvgiverType(businessId, urbanDistrictId, urbanDistrictName),
                new SakType(
                        UUID.randomUUID(),
                        null,
                        LocalDate.now(),
                        null,
                        "journalnummer",
                        "12345612345",
                        null,
                        LocalDate.now().minusYears(1),
                        "1",
                        null,
                        [], [], [], [], [], [], [], [], [], []
                )
        )
    }

    def createAvgiverType(businessId, urbanDistrictId, urbanDistrictName) {
        new AvgiverType(businessId, "0301", "OSLO", urbanDistrictId, urbanDistrictName)
    }
}
