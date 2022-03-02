package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

class InitialMutationProviderSpec extends Specification {

    @Unroll
    def "when calling createInitialMutation receive valid instance"() {
        when:
        def instance = InitialMutationProvider.createInitialMutation(LocalDateTime.now())

        then:
        noExceptionThrown()
        and:
        def xml = BarnevernConverter.marshallInstance(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)

        where:
        i << (1..10)
    }
}
