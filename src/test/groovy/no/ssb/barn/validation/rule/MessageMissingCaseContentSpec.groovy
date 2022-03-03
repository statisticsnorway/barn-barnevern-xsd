package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 5: Konkludert melding mangler saksinnhold

Gitt at man har en melding
når Konklusjon finnes og 1 eller flere Saksinnhold mangler
så gi feilmeldingen "Konkludert melding mangler saksinnhold"

Alvorlighetsgrad: ERROR
""")
class MessageMissingCaseContentSpec extends Specification {

    @Subject
    MessageMissingCaseContent sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageMissingCaseContent()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def melding = context.rootObject.sak.melding[0]
        and:
        if (setConclusion) {
            melding.konklusjon.kode = "1"
        } else {
            melding.konklusjon = null
        }
        and:
        if (setCaseContent) {
            melding.saksinnhold.add(new SaksinnholdType())
        } else {
            melding.saksinnhold.clear()
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
            assert reportEntries[0].errorText.contains("Konkludert melding mangler saksinnhold.")
        }

        where:
        setConclusion | setCaseContent || errorExpected
        false         | false          || false
        false         | true           || false
        true          | false          || true
        true          | true           || false

    }
}
