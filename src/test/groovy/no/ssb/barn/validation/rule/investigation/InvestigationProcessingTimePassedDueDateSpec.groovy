package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.BegrepsType
import no.ssb.barn.xsd.RelasjonType
import no.ssb.barn.xsd.UndersokelseKonklusjonType
import no.ssb.barn.xsd.UndersokelseUtvidetFristType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZonedDateTime

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
        context.rootObject.datoUttrekk = ZonedDateTime.now()
        and:
        def messageId = UUID.randomUUID()
        and:
        def investigationId = UUID.randomUUID()
        and:
        context.rootObject.sak.relasjon[0] = new RelasjonType(
                UUID.randomUUID(),
                messageId,
                relationFromType,
                investigationId,
                relationToType
        )
        if (resetMessageId) messageId = UUID.randomUUID()
        and:
        def message = context.rootObject.sak.melding[0]
        and:
        message.id = messageId
        and:
        message.startDato = messageStartDate
        and:
        if (resetInvestigationId) investigationId = UUID.randomUUID()
        and:
        def investigation = context.rootObject.sak.undersokelse[0]
        and:
        investigation.id = investigationId

        and:
        investigation.konklusjon = new UndersokelseKonklusjonType(
                LocalDate.now().minusDays(1),
                investigation.konklusjon.kode,
                investigation.konklusjon.presisering)
        and:
        if (removeExtendedDueDate) {
            investigation.utvidetFrist = null
        } else {
            investigation.utvidetFrist =
                    new UndersokelseUtvidetFristType(LocalDate.now(), extendedDueDateGranted)
        }
        and:
        if (removeConclusion) investigation.konklusjon = null

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
        relationFromType    | relationToType           | resetMessageId | messageStartDate               | resetInvestigationId | daysText  | extendedDueDateGranted | removeExtendedDueDate | removeConclusion || errorExpected
        BegrepsType.VEDTAK  | BegrepsType.TILTAK       | false          | LocalDate.now()                | false                | "N/A"     | null                   | false                 | true             || false
        BegrepsType.VEDTAK  | BegrepsType.TILTAK       | true           | LocalDate.now()                | true                 | "N/A"     | null                   | false                 | true             || false
        BegrepsType.MELDING | BegrepsType.VEDTAK       | false          | LocalDate.now()                | false                | "N/A"     | null                   | false                 | true             || false
        BegrepsType.VEDTAK  | BegrepsType.UNDERSOKELSE | true           | LocalDate.now().minusDays(97)  | false                | "N/A"     | null                   | true                  | false            || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | true           | LocalDate.now().minusDays(97)  | false                | "N/A"     | null                   | true                  | false            || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(97)  | true                 | "N/A"     | null                   | true                  | false            || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(97)  | false                | "N/A"     | null                   | true                  | false            || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(96)  | false                | "N/A"     | null                   | true                  | true             || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(97)  | false                | "N/A"     | "3"                    | false                 | true             || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(97)  | false                | "N/A"     | "1"                    | false                 | true             || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(98)  | false                | "7 + 90"  | null                   | true                  | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(98)  | false                | "7 + 90"  | null                   | false                 | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(98)  | false                | "7 + 90"  | "2"                    | false                 | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(98)  | false                | "N/A"     | "1"                    | false                 | true             || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(187) | false                | "7 + 90"  | null                   | true                  | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(187) | false                | "7 + 90"  | null                   | false                 | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(187) | false                | "7 + 90"  | "2"                    | false                 | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(186) | false                | "N/A"     | "1"                    | false                 | true             || false
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(188) | false                | "7 + 90"  | null                   | true                  | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(188) | false                | "7 + 90"  | "2"                    | false                 | true             || true
        BegrepsType.MELDING | BegrepsType.UNDERSOKELSE | false          | LocalDate.now().minusDays(188) | false                | "7 + 180" | "1"                    | false                 | true             || true
    }
}
