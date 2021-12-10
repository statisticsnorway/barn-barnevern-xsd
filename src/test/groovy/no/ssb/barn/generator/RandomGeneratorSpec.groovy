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

    def "Should validate randomly generated String"(){
        given:
        def stringLength = 20

        when:
        def randomString = RandomGenerator.generateRandomString(stringLength)

        then:
        verifyAll {
            randomString.length() == stringLength
            randomString ==~ /[a-zA-Z0-9]{20}/

        }
    }

    def "Should create an Int within a certain range"(){
        given:
        def startInclusive = 0
        def endExclusive = 10

        when:
        def randomInt = RandomGenerator.generateRandomIntFromRange(startInclusive, endExclusive)

        then:
        startInclusive <= randomInt && randomInt < endExclusive
    }

    def "Should validate UUID against regex"(){
        given:
        def uuid = UUID.randomUUID()

        expect:
        uuid ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$/

    }
}
