package no.ssb.barn.generator

import no.ssb.barn.util.ValidationUtils
import spock.lang.Specification

import java.time.LocalDate

class RandomGeneratorSpec extends Specification {
    def "Should validate randomly generated norwegian social security numbers known as FNR"(){
        given:
        def startInclusive = LocalDate.of(2000, 1, 1)
        def endExclusive = LocalDate.of(2021, 1, 1)

        when:
        def ssn = RandomGenerator.generateRandomSSN(startInclusive, endExclusive)

        then:
        ValidationUtils.validateSSN(ssn)
    }
}
