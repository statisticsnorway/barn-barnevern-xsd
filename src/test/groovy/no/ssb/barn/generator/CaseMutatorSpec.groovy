package no.ssb.barn.generator

import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate

class CaseMutatorSpec extends Specification {

    def initialMutationProvider = new InitialMutationProvider()

    def "when calling mutate with valid instance expect changed state"() {
        given:
        def caseEntry = createCaseEntry(currentState)

        when:
        CaseMutator.mutate(caseEntry)

        then:
        noExceptionThrown()
        and:
        expectedNewStates.contains(caseEntry.state)

        where:
        currentState                 || expectedNewStates
        BarnevernState.MESSAGE       || Set.of(BarnevernState.INVESTIGATION, BarnevernState.DECISION)
        BarnevernState.INVESTIGATION || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION)
        BarnevernState.MEASURE       || Set.of(BarnevernState.PLAN, BarnevernState.DECISION, BarnevernState.AFTERCARE)
        BarnevernState.DECISION      || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION, BarnevernState.AFTERCARE)
        BarnevernState.AFTERCARE     || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION)
    }

    def "fromMessageToInvestigation expect one instance of UndersokelseType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.MESSAGE)
        and: "make sure instance does not contain any UndersokelseType instances"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())

        when:
        CaseMutator.fromMessageToInvestigation(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.undersokelse.size())
                .sum()
    }

    @Ignore("Implement me")
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
    }

    @Ignore("Implement me")
    def "fromInvestigationToMeasure expect one instance of TiltakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.INVESTIGATION)
        and: "make sure we have UndersokelseType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())
        and: "make sure we don't have TiltakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.tiltak.any())

        when:
        CaseMutator.fromInvestigationToMeasure(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.tiltak.size())
                .sum()
    }

    @Ignore("Implement me")
    def "fromInvestigationToDecision expect one instance of VedtakType"() {
        given:
        def caseEntry = createCaseEntry(BarnevernState.INVESTIGATION)
        and: "make sure we have UndersokelseType instance"
        assert caseEntry.barnevern.sak.virksomhet.any(it -> it.undersokelse.any())
        and: "make sure we don't have VedtakType instance"
        assert !caseEntry.barnevern.sak.virksomhet.any(it -> it.vedtak.any())

        when:
        CaseMutator.fromInvestigationToDecision(caseEntry)

        then:
        1 == caseEntry.barnevern.sak.virksomhet.stream()
                .mapToInt(it -> it.vedtak.size())
                .sum()
    }

    @Ignore("Implement me")
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
    }

    @Ignore("Implement me")
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
    }

    @Ignore("Implement me")
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
    }

    @Ignore("Implement me")
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
    }

    @Ignore("Implement me")
    def "fromDecisionToAnotherDecision"() {
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
    }

    @Ignore("Implement me")
    def "fromDecisionToAfterCare"() {
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
    }

    @Ignore("Implement me")
    def "FromAfterCareToMeasure"() {
    }

    @Ignore("Implement me")
    def "FromAfterCareToDecision"() {
    }

    def createCaseEntry(BarnevernState state) {
        def instance = new CaseEntry(
                UUID.randomUUID(),
                initialMutationProvider.createInitialMutation(LocalDate.now()),
                LocalDate.now(),
                1,
                state)

        switch (state) {
            case BarnevernState.MESSAGE:
                break

            case BarnevernState.INVESTIGATION:
                CaseMutator.fromMessageToInvestigation(instance)
                break

            case BarnevernState.MEASURE:
                CaseMutator.fromMessageToInvestigation(instance)
                CaseMutator.fromInvestigationToMeasure(instance)
                break

            case BarnevernState.DECISION:
                CaseMutator.fromMessageToInvestigation(instance)
                CaseMutator.fromInvestigationToDecision(instance)
                break
        }

        instance
    }
}
