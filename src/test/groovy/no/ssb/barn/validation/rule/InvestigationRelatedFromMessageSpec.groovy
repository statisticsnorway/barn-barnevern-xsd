package no.ssb.barn.validation.rule


import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 10: Undersøkelse skal ha relasjon til melding

Gitt at man har en Undersøkelse, en Relasjon og en Melding<br/>
når en relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType mangler<br/>
så gi feilmeldingen "Undersøkelse mangler en relasjon til melding"

Alvorlighetsgrad: ERROR
""")
class InvestigationRelatedFromMessageSpec extends Specification {

    @Subject
    InvestigationRelatedFromMessage sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationRelatedFromMessage()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.id = messageId ?: UUID.randomUUID()
        and:
        def relation = context.rootObject.sak.virksomhet[0].relasjon[0]
        and:
        relation.fraId = messageId ?: UUID.randomUUID()
        and:
        relation.fraType = BegrepsType.MELDING
        and:
        relation.tilId = investigationId
        and:
        relation.tilType = BegrepsType.UNDERSOKELSE
        and:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        investigation.id = investigationId

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Undersøkelse mangler en relasjon til melding")
        }

        where:
        messageId         | investigationId   || errorExpected
        UUID.randomUUID() | UUID.randomUUID() || false
        null              | UUID.randomUUID() || true
    }
}
