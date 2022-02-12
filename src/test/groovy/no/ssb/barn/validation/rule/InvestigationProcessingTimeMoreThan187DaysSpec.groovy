package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 12: Fristoverskridelse på behandlingstid på mer enn 187 dager etter melding sin startdato

Gitt at man har en konkludert Undersøkelse med en Relasjon til en Melding
der relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType,<br/>
og undersøkelse sin Konklusjon/SluttDato er satt
når Undersøkelse sin Konklusjon/SluttDato er mer enn 7 + 180 dager etter Melding sin StartDato <br/>
så gi feilmeldingen "Undersøkelse skal konkluderes innen 7 + 180 dager etter melding sin startdato"

Alvorlighetsgrad: Warning
""")
class InvestigationProcessingTimeMoreThan187DaysSpec extends Specification {

    @Subject
    InvestigationProcessingTimeMoreThan187Days sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationProcessingTimeMoreThan187Days()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.id = messageId as UUID
        and:
        message.startDato = messageStartDate
        and:
        def relation = context.rootObject.sak.virksomhet[0].relasjon[0]
        and:
        relation.fraId = messageId as UUID
        and:
        relation.fraType = BegrepsType.MELDING
        and:
        relation.tilId = investigationId as UUID
        and:
        relation.tilType = BegrepsType.UNDERSOKELSE
        and:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        investigation.id = investigationId as UUID
        and:
        investigation.konklusjon.sluttDato = investigationEndDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Undersøkelse skal konkluderes innen 7 + 180 dager etter melding sin startdato")
        }

        where:
        messageId         | messageStartDate | investigationId   | investigationEndDate          || errorExpected
        UUID.randomUUID() | LocalDate.now()  | UUID.randomUUID() | LocalDate.now().plusDays(187) || false
        UUID.randomUUID() | LocalDate.now()  | UUID.randomUUID() | LocalDate.now().plusDays(188) || true

    }
}
