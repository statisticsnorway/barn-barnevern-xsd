package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class InvestigationProcessingTimePassedDueDateSpec extends Specification {

    @Subject
    InvestigationProcessingTimePassedDueDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationProcessingTimePassedDueDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.datoUttrekk = LocalDateTime.now()
        and:
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
        relation.fraType = relationFromType
        and:
        relation.tilId = investigationId as UUID
        and:
        relation.tilType = relationToType
        and:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        investigation.id = investigationId as UUID
        and:
        investigation.konklusjon = null
        and:
        investigation.utvidetFrist.innvilget = extendedDueDateGranted
        and:
        if (removeExtendedDueDate) investigation.utvidetFrist = null

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Undersøkelse skal konkluderes innen " + daysText + " dager etter melding sin startdato")
        }

        where:
        relationFromType    | relationToType           | messageId         | messageStartDate               | investigationId   | daysText  | extendedDueDateGranted | removeExtendedDueDate || errorExpected
        BegrepsType.MELDING | BegrepsType.VEDTAK       | UUID.randomUUID() | LocalDate.now()                | UUID.randomUUID() | ""        | null                   | false                 || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(97)  | UUID.randomUUID() | "7 + 90"  | null                   | true                  || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(97)  | UUID.randomUUID() | "7 + 90"  | "2"                    | false                 || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(97)  | UUID.randomUUID() | "7 + 90"  | "1"                    | false                 || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(98)  | UUID.randomUUID() | "7 + 90"  | null                   | true                  || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(98)  | UUID.randomUUID() | "7 + 90"  | null                   | false                 || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(98)  | UUID.randomUUID() | "7 + 90"  | "2"                    | false                 || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(98)  | UUID.randomUUID() | "7 + 90"  | "1"                    | false                 || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(187) | UUID.randomUUID() | "7 + 90"  | null                   | true                  || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(187) | UUID.randomUUID() | "7 + 90"  | null                   | false                 || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(187) | UUID.randomUUID() | "7 + 90"  | "2"                    | false                 || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(187) | UUID.randomUUID() | "7 + 90"  | "1"                    | false                 || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(188) | UUID.randomUUID() | "7 + 90"  | null                   | true                  || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(188) | UUID.randomUUID() | "7 + 90"  | "2"                    | false                 || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | UUID.randomUUID() | LocalDate.now().minusDays(188) | UUID.randomUUID() | "7 + 180" | "1"                    | false                 || true
    }

}
