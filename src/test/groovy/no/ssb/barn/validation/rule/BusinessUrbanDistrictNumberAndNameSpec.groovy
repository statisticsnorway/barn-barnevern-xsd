package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Virksomhet Kontroll 3: Bydelsnummer og bydelsnavn

Gitt at man har en virksomhet der Organisasjonsnummer er en av 958935420 (Oslo), 964338531 (Bergen), 942110464 (Trondheim) eller 964965226 (Stavanger)<br/>
når Bydelsnummer og Bydelsnavn mangler utfylling<br/>
så gi feilmeldingen "Virksomhetens Bydelsnummer og Bydelsnavn skal være utfylt"

Alvorlighetsgrad: ERROR
""")
class BusinessUrbanDistrictNumberAndNameSpec extends Specification {

    @Subject
    BusinessUrbanDistrictNumberAndName sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new BusinessUrbanDistrictNumberAndName()
        context = getTestContext()
    }

    @Unroll("businessId: #businessId, urbanDistrictId: #urbanDistrictId, urbanDistrictName: #urbanDistrictName")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].organisasjonsnummer = businessId
        and:
        context.rootObject.sak.virksomhet[0].bydelsnummer = urbanDistrictId
        and:
        context.rootObject.sak.virksomhet[0].bydelsnavn = urbanDistrictName

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
            assert reportEntries[0].errorText.contains("Virksomhetens Bydelsnummer og Bydelsnavn skal være utfylt")
        }

        where:
        businessId  | urbanDistrictId | urbanDistrictName || errorExpected
        "999999999" | null            | null              || false
        "958935420" | null            | null              || true
        "958935420" | ""              | ""                || true
        "958935420" | "01"            | "Name"            || false
        "964338531" | null            | null              || true
        "964338531" | "01"            | "Name"            || false
        "942110464" | null            | null              || true
        "942110464" | "01"            | "Name"            || false
        "964965226" | null            | null              || true
        "964965226" | "01"            | "Name"            || false
    }
}
