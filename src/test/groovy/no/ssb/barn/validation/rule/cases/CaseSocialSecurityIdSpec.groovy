package no.ssb.barn.validation.rule.cases

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.cases.CaseSocialSecurityId
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Sak Kontroll 11: Fødselsnummer

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Klienten har ufullstendig fødselsnummer. Korriger fødselsnummer."

Alvorlighetsgrad: Warning
""")
class CaseSocialSecurityIdSpec extends Specification {

    @Subject
    CaseSocialSecurityId sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new CaseSocialSecurityId()
        context = getTestContext()
    }

    @Unroll
    def "Tester alle personnr-scenarier"() {
        given:
        context.rootObject.sak.fodselsnummer = socialSecurityId

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == expectedError
        and:
        if (expectedError) {
            assert 1 == reportEntries.size()
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Korriger fødselsnummer.")
        } else {
            assert null == reportEntries
        }

        where:
        socialSecurityId || expectedError
        "02011088123"    || false
        "12345655555"    || true
        "12345612345"    || true
        null             || true
        ""               || true
        " "              || true
    }
}