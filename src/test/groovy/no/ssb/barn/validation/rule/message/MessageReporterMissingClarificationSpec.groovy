package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.message.MessageReporterMissingClarification
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melder Kontroll 2: Mangler Presisering

Gitt at man har en Melder der Kode er 22 (= Andre offentlige instanser)<br/>
når Melder mangler Presisering<br/>
så gi feilmeldingen "Melder med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR
""")
class MessageReporterMissingClarificationSpec extends Specification {

    @Subject
    MessageReporterMissingClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageReporterMissingClarification()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def reporter = context.rootObject.sak.melding[0].melder[0]
        and:
        reporter.kode = code
        and:
        reporter.presisering = clarification

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
            assert reportEntries[0].errorText.contains("Melder med kode")
        }

        where:
        code  | clarification   || errorExpected
        "N/A" | "N/A"           || false
        "N/A" | "N/A"           || false
        "1"   | "N/A"           || false
        "22"  | "~presisering~" || false
        "22"  | ""              || true
        "22"  | null            || true
    }
}
