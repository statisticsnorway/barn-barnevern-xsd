package no.ssb.barn.generator

import no.ssb.barn.util.ValidationUtils
import spock.lang.Specification

import java.time.LocalDate

class RandomUtilsSpec extends Specification {
    def "Should validate randomly generated norwegian social security numbers known as FNR"() {
        given:
        def startInclusive = LocalDate.of(2000, 1, 1)
        and:
        def endExclusive = LocalDate.of(2021, 1, 1)

        when:
        def ssn = RandomUtils.generateRandomSSN(startInclusive, endExclusive)

        then:
        ValidationUtils.validateSSN(ssn)
    }

    def "Should validate randomly generated String"() {
        given:
        def stringLength = 20

        when:
        def randomString = RandomUtils.generateRandomString(stringLength)

        then:
        verifyAll {
            randomString.length() == stringLength
            randomString ==~ /[a-zA-Z0-9]{20}/
        }
    }

    def "Should create an Int within a certain range"() {
        given:
        def startInclusive = 0
        and:
        def endExclusive = 10

        when:
        def randomInt = RandomUtils.generateRandomIntFromRange(startInclusive, endExclusive)

        then:
        startInclusive <= randomInt && randomInt < endExclusive
    }

    def "Should validate UUID against regex"() {
        expect:
        UUID.randomUUID() ==~ /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$/
    }

    def "generateRandomInt receive int between boundaries"() {
        expect:
        RandomUtils.generateRandomInt() >= 1
        and:
        RandomUtils.generateRandomInt() <= 100_000
    }
}
