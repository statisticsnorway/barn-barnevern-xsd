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
        def message = context.rootObject.sak.melding[0]
        and:
        message.id = fromId ?: UUID.randomUUID()
        and:
        def relation = context.rootObject.sak.relasjon[0]
        and:
        relation.fraId = fromId ?: UUID.randomUUID()
        and:
        relation.fraType = fromType
        and:
        relation.tilId = toId ?: UUID.randomUUID()
        and:
        relation.tilType = toType
        and:
        def investigation = context.rootObject.sak.undersokelse[0]
        and:
        investigation.id = toId ?: UUID.randomUUID()

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
        fromId            | fromType                 | toId              | toType                   || errorExpected
        UUID.randomUUID() | BegrepsType.MELDING      | UUID.randomUUID() | BegrepsType.UNDERSOKELSE || false
        UUID.randomUUID() | BegrepsType.MELDING      | null              | BegrepsType.UNDERSOKELSE || true
        null              | BegrepsType.MELDING      | null              | BegrepsType.UNDERSOKELSE || true

        UUID.randomUUID() | BegrepsType.MELDING      | UUID.randomUUID() | BegrepsType.MELDING      || true
        null              | BegrepsType.MELDING      | UUID.randomUUID() | BegrepsType.MELDING      || true
        UUID.randomUUID() | BegrepsType.MELDING      | null              | BegrepsType.MELDING      || true
        null              | BegrepsType.MELDING      | null              | BegrepsType.MELDING      || true

        UUID.randomUUID() | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | BegrepsType.UNDERSOKELSE || true
        UUID.randomUUID() | BegrepsType.UNDERSOKELSE | null              | BegrepsType.UNDERSOKELSE || true
    }
}
