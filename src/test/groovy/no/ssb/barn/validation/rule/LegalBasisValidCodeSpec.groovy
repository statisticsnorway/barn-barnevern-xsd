package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.LovhjemmelType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class LegalBasisValidCodeSpec extends Specification {

    @Subject
    LegalBasisValidCode sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new LegalBasisValidCode()
        context = getTestContext()
    }

    @Unroll
    def "test alle scenarier"() {
        given:
        def sak = context.rootObject.sak
        and:
        sak.virksomhet[0].tiltak = List.of(sak.virksomhet[0].tiltak[0])
        and:
        sak.virksomhet[0].tiltak[0].lovhjemmel = null
        and:
        if (createLegalBasis) {
            sak.virksomhet[0].tiltak[0].lovhjemmel = new LovhjemmelType(
                    "~lov~",
                    kapittel,
                    paragraf,
                    List.of("~ledd~"),
                    List.of("~punktum~")
            )
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
            assert reportEntries[0].errorText
                    .contains("er rapportert med den ugyldige koden 0")
        }

        where:
        createLegalBasis | kapittel | paragraf || errorExpected
        false            | ""       | ""       || false
        true             | ""       | ""       || false
        true             | "1"      | ""       || false
        true             | ""       | "1"      || false
        true             | "01234"  | ""       || true
        true             | ""       | "01234"  || true
        true             | "01234"  | "01234"  || true
    }
}
