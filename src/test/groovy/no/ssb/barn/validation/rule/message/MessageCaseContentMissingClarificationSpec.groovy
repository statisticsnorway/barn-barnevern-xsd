package no.ssb.barn.validation.rule.message

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Saksinnhold Kontroll 2: Mangler Presisering

Gitt at man har et Saksinnhold der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Saksinnhold mangler Presisering<br/>
så gi feilmeldingen "Saksinnhold med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR
""")
class MessageCaseContentMissingClarificationSpec extends Specification {
    @Subject
    MessageCaseContentMissingClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageCaseContentMissingClarification()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def saksinnhold = context.rootObject.sak.melding[0].saksinnhold[0]
        and:
        saksinnhold.kode = code
        and:
        saksinnhold.presisering = clarification

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
            assert reportEntries[0].errorText.contains("mangler presisering")
        }

        where:
        code | clarification   || errorExpected
        null | "N/A"           || false
        ""   | "N/A"           || false
        "42" | "N/A"           || false

        "18" | null            || true
        "18" | ""              || true
        "19" | null            || true
        "19" | ""              || true

        "18" | "~presisering~" || false
        "19" | "~presisering~" || false
    }
}
