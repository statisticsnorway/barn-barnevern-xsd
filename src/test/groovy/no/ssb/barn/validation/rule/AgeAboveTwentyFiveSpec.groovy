package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getMockSocialSecurityNumber
import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class AgeAboveTwentyFiveSpec extends Specification {

    @Subject
    AgeAboveTwentyFive sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new AgeAboveTwentyFive()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.fodselsnummer = getMockSocialSecurityNumber(age)

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
            assert reportEntries[0].errorText.contains("Individet er $age år og skal avsluttes som klient")
        }

        where:
        age || errorExpected
        25  || true
        26  || true
        24  || false
        1   || false
    }
}
