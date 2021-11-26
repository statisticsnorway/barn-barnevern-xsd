package no.ssb.barn.validation

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class TheValidatorSpec extends Specification {

    @Shared
    TheValidator sut

    @SuppressWarnings('unused')
    def setupSpec() {
        sut = TheValidator.create()
    }

    def "when create, receive validator instance"() {
        expect:
        TheValidator.create() instanceof TheValidator
    }

    def "when validate with invalid params, receive JSON result"() {
        when:
        def result = sut.validate(
                UUID.randomUUID().toString(), 1, "~xmlBody~")

        then:
        result.startsWith("{\"messageId\"")
        and:
        result.contains("warningLevel\":\"FATAL\"")
    }

    def "when validate with valid params, receive JSON result"() {
        when:
        def result = sut.validate(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("test01_fil01.xml"))

        then:
        result.startsWith("{\"messageId\"")
        and:
        !result.contains("warningLevel")
    }

    @Unroll("test repeated #i time")
    def "when validate multiple times with invalid params, receive JSON result"() {
        when:
        def result = sut.validate(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("barnevern_with_errors.xml"))

        then:
        noExceptionThrown()
        and:
        null != result
        and:
        result.startsWith("{\"messageId\"")
        and:
        result.contains("warningLevel\":\"FATAL\"")

        where:
        i << (1..10)
    }
}
