package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class InitialMutationProviderSpec extends Specification {

    @Unroll
    def "when calling createInitialMutation receive valid instance"() {
        given:
        def sut = new InitialMutationProvider(xmlResource)

        when:
        def instance = sut.createInitialMutation(LocalDate.now())

        then:
        noExceptionThrown()
        and:
        def xml = BarnevernConverter.marshallInstance(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)

        where:
        xmlResource                                | _
        "/initial_mutation.xml"                    | _
        "/initial_mutation_oslo.xml"               | _
        "/initial_mutation_without_virksomhet.xml" | _
        "/initial_mutation_without_melding.xml"    | _
    }
}
