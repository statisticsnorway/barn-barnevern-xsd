package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Gitt at man har har et barn og utleder alder<br/>
når alder er 18 eller større og tiltak mangler <br/>
så skal man ved feil gi feilmeldingen "Klienten er over 18 år og skal dermed ha tiltak"

Alvorlighetsgrad: ERROR
""")
class CaseAgeAboveEighteenAndMeasuresSpec extends Specification {

    @Subject
    CaseAgeAboveEighteenAndMeasures sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new CaseAgeAboveEighteenAndMeasures()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(age)
        and:
        if (resetMeasures) {
            context.rootObject.sak.virksomhet[0].tiltak = List.of()
        }

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
            assert reportEntries[0].errorText.contains("Klienten er over 18 år og skal dermed ha tiltak")
        }

        where:
        resetMeasures | age || errorExpected
        true          | 17  || false
        true          | 18  || true
        true          | 19  || true
        false         | 17  || false
        false         | 18  || false
        false         | 19  || false
    }
}
