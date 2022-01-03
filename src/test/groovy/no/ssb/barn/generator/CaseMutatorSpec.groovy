package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.VersionOneValidator
import no.ssb.barn.xsd.BarnevernType
import spock.lang.Specification

import java.time.LocalDate

class CaseMutatorSpec extends Specification {

    def versionOneValidator = new VersionOneValidator()

    def "when calling mutate with valid instance expect changed state"() {
        given:
        def caseEntry
        and:
        def iterations = 0

        when:
        do {
            caseEntry = createCaseEntry(currentState)
            CaseMutator.mutate(caseEntry)
        } while (caseEntry.state != expectedNewState && ++iterations < 20)

        then:
        noExceptionThrown()
        and:
        expectedNewState == caseEntry.state || iterations >= 20

        where:
        currentState                         || expectedNewState
        BarnevernState.MESSAGE               || BarnevernState.CASE_CLOSED
        BarnevernState.MESSAGE               || BarnevernState.INVESTIGATION_STARTED
        BarnevernState.MESSAGE               || BarnevernState.DECISION

        BarnevernState.INVESTIGATION_STARTED || BarnevernState.INVESTIGATION_ENDED
        BarnevernState.INVESTIGATION_STARTED || BarnevernState.MEASURE

        BarnevernState.INVESTIGATION_ENDED   || BarnevernState.MEASURE
        BarnevernState.INVESTIGATION_ENDED   || BarnevernState.DECISION

        BarnevernState.PLAN                  || BarnevernState.PLAN

        BarnevernState.MEASURE               || BarnevernState.PLAN
        BarnevernState.MEASURE               || BarnevernState.DECISION
        BarnevernState.MEASURE               || BarnevernState.AFTERCARE

        BarnevernState.DECISION              || BarnevernState.MEASURE
        BarnevernState.DECISION              || BarnevernState.DECISION
        BarnevernState.DECISION              || BarnevernState.AFTERCARE

        BarnevernState.AFTERCARE             || BarnevernState.MEASURE
        BarnevernState.AFTERCARE             || BarnevernState.DECISION
    }

    def "fromMessageToInvestigation expect one instance of UndersokelseType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MESSAGE)
        and: "make sure instance does not contain any UndersokelseType instances"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())

        when:
        CaseMutator.fromMessageToInvestigationStarted(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.undersokelse.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromMessageToDecision expect one instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MESSAGE)
        and: "make sure instance does not contain any VedtakType instances"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())

        when:
        CaseMutator.fromMessageToDecision(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromInvestigationToMeasure expect one instance of TiltakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.INVESTIGATION_STARTED)
        and: "make sure we have UndersokelseType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())
        and: "make sure we don't have TiltakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())

        when:
        CaseMutator.fromInvestigationEndedToMeasure(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.tiltak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromInvestigationToDecision expect one instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.INVESTIGATION_STARTED)
        and: "make sure we have UndersokelseType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())
        and: "make sure we don't have VedtakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())

        when:
        CaseMutator.fromInvestigationEndedToDecision(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromMeasureToPlan expect one instance of PlanType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MEASURE)
        and: "make sure we have TiltakType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())
        and: "make sure we don't have PlanType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.plan.any())

        when:
        CaseMutator.fromMeasureToPlan(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.plan.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromMeasureToDecision expect one instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MEASURE)
        and: "make sure we have TiltakType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())
        and: "make sure we don't have VedtakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())

        when:
        CaseMutator.fromMeasureToDecision(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromMeasureToAfterCare expect one instance of EttervernType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MEASURE)
        and: "make sure we have TiltakType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())
        and: "make sure we don't have EttervernType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.ettervern.any())

        when:
        CaseMutator.fromMeasureToAfterCare(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.ettervern.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromDecisionToMeasure expect one instance of TiltakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.DECISION)
        and: "make sure we have VedtakType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())
        and: "make sure we don't have TiltakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())

        when:
        CaseMutator.fromDecisionToMeasure(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.tiltak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromDecisionToAnotherDecision expect one instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.DECISION)
        and: "make sure we have a single VedtakType instance"
        assert 1 == caseEntry.barnevern.sak.virksomhet.count(it -> it.vedtak.any())

        when:
        CaseMutator.fromDecisionToAnotherDecision(caseEntry)

        then:
        2 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromDecisionToAfterCare expect one instance of EttervernType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.DECISION)
        and: "make sure we have VedtakType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())
        and: "make sure we don't have EttervernType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.ettervern.any())

        when:
        CaseMutator.fromDecisionToAfterCare(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.ettervern.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromAfterCareToMeasure expect one instance of TiltakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.AFTERCARE)
        and: "make sure we have EttervernType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.ettervern.any())
        and: "make sure we don't have TiltakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())

        when:
        CaseMutator.fromAfterCareToMeasure(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.tiltak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    def "fromAfterCareToDecision expect one more instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.AFTERCARE)
        and: "make sure we have EttervernType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.ettervern.any())
        and: "count existing number of VedtakType instances"
        def initialNumberOfDecisions = caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()

        when:
        CaseMutator.fromAfterCareToDecision(caseEntry)

        then:
        initialNumberOfDecisions + 1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
        and:
        isValid(caseEntry.barnevern)
    }

    // util stuff from here

    static def createCaseEntry(BarnevernState state) {
        def instance = new CaseEntry(
                UUID.randomUUID(),
                InitialMutationProvider.createInitialMutation(LocalDate.now()),
                LocalDate.now(),
                1,
                state)

        switch (state) {
            case BarnevernState.MESSAGE:
                break

            case BarnevernState.INVESTIGATION_STARTED:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                break

            case BarnevernState.INVESTIGATION_ENDED:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                CaseMutator.fromInvestigationStartedToEnded(instance)
                break

            case BarnevernState.PLAN:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                CaseMutator.fromInvestigationStartedToEnded(instance)
                CaseMutator.fromInvestigationEndedToMeasure(instance)
                CaseMutator.fromMeasureToPlan(instance)
                break

            case BarnevernState.MEASURE:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                CaseMutator.fromInvestigationStartedToEnded(instance)
                CaseMutator.fromInvestigationEndedToMeasure(instance)
                break

            case BarnevernState.DECISION:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                CaseMutator.fromInvestigationStartedToEnded(instance)
                CaseMutator.fromInvestigationEndedToDecision(instance)
                break

            case BarnevernState.AFTERCARE:
                CaseMutator.fromMessageToInvestigationStarted(instance)
                CaseMutator.fromInvestigationStartedToEnded(instance)
                CaseMutator.fromInvestigationEndedToDecision(instance)
                CaseMutator.fromDecisionToAfterCare(instance)
        }

        instance
    }

    def isValid(BarnevernType barnevernType) {
        def res = versionOneValidator.validate(
                new ValidationContext(
                        "~messageId~",
                        BarnevernConverter.marshallInstance(barnevernType)))

        return res.severity != WarningLevel.FATAL
    }
}
