package no.ssb.barn.util

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.util.SocialSecurityIdUtils.getDateOfBirthFromSsn
import static no.ssb.barn.util.SocialSecurityIdUtils.getGenderFromSsn

class SocialSecurityIdUtilsSpec extends Specification {

    @Unroll
    def "getGenderFromSsn all scenarios"() {
        expect:
        expectedValue == getGenderFromSsn(ssn)

        where: "digit #9 is even, girl (2) else boy (1)"
        ssn           || expectedValue
        "01010112345" || SocialSecurityIdUtils.MALE
        "01010112245" || SocialSecurityIdUtils.FEMALE
    }

    @Unroll
    def "getDateOfBirthFromSsn all scenarios"() {
        expect:
        expectedValue == getDateOfBirthFromSsn(ssn)

        where:
        ssn           || expectedValue
        "01010112345" || LocalDate.of(2001, 1, 1)
        "31121012345" || LocalDate.of(2010, 12, 31)
    }
}
