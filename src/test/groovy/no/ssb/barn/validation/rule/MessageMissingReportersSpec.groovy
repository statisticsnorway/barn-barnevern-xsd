package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MelderType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 4: Konkludert melding inneholder melder

Gitt at man har en melding 
når Konklusjon finnes og 1 eller flere Melder(e) mangler
så gi feilmeldingen "Konkludert melding mangler melder(e)"

Alvorlighetsgrad: ERROR
""")
class MessageMissingReportersSpec extends Specification {

    @Subject
    MessageMissingReporters sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageMissingReporters()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        if (setConclusion) {
            melding.konklusjon.kode = "1"
        } else {
            melding.konklusjon = null
        }
        and:
        if (setReporters) {
            melding.melder.add(new MelderType())
        } else {
            melding.melder.clear()
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
            assert reportEntries[0].errorText.contains("Konkludert melding mangler melder(e)")
        }

        where:
        setConclusion | setReporters || errorExpected
        false         | false        || false
        false         | true         || false
        true          | false        || true
        true          | true         || false
    }
}
