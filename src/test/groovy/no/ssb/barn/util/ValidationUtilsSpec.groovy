package no.ssb.barn.util

import no.ssb.barn.xsd.*
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.transform.stream.StreamSource
import java.time.LocalDate
import java.time.Year
import java.time.ZonedDateTime

class ValidationUtilsSpec extends Specification {

    @Unroll
    def "minDate receive min date"() {
        expect:
        ValidationUtils.getMinDate(first, second) == (expectFirstToBeReturned ? first : second)

        where:
        first                     | second                    || expectFirstToBeReturned
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 12, 2) || true
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 11, 2) || false
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 12, 1) || false
    }

    @Unroll
    def "maxDate receive max date"() {
        expect:
        ValidationUtils.getMaxDate(first, second) == (expectFirstToBeReturned ? first : second)

        where:
        first                     | second                    || expectFirstToBeReturned
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 12, 2) || false
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 11, 2) || true
        LocalDate.of(2021, 12, 1) | LocalDate.of(2021, 12, 1) || false
    }

    @Unroll
    def "areOverlappingWithAtLeastThreeMonths all scenarios"() {
        expect:
        expectedResult == ValidationUtils.areOverlappingWithAtLeastThreeMonths(first, second, ZonedDateTime.now())

        where:
        first                                          | second                                         || expectedResult
        createMeasure(createDate(0), createDate(1))    | createMeasure(createDate(2), createDate(3))    || false
        createMeasure(createDate(2), createDate(3))    | createMeasure(createDate(0), createDate(1))    || false

        // first.start <= second.endInclusive
        createMeasure(createDate(0), createDate(4))    | createMeasure(createDate(0), createDate(3))    || true
        createMeasure(createDate(0), createDate(4))    | createMeasure(createDate(-3), createDate(0))   || false

        // second.start <= first.endInclusive
        createMeasure(createDate(-2), createDate(1))   | createMeasure(createDate(0), createDate(3))    || false
        createMeasure(createDate(-3), createDate(0))   | createMeasure(createDate(0), createDate(3))    || false

        createMeasure(createDate(0), createDate(3))    | createMeasure(createDate(0), createDate(3))    || true
        createMeasure(createDate(0), createDate(4))    | createMeasure(createDate(1), createDate(4))    || true
        createMeasure(createDate(-1), createDate(3))   | createMeasure(createDate(0), createDate(4))    || true
        createMeasure(createDate(-2), createDate(2))   | createMeasure(createDate(1), createDate(4))    || false
    }

    def "Should find sources from classpath, filename = #filename, result = #result"() {
        expect:
        (getSourceFromClasspath(filename) != null) == result

        where:
        filename                     || result
        "/Barnevern.xsd"             || true
        "/test01_file01_changes.xml" || true
        "/test01_file02_changes.xml" || true
        "/invalid.xml"               || true
        "/nonExistentFile"           || false
    }

    def "Should validate norwegian social security numbers (fnr), ssn = #ssn, result = #result"() {
        expect:
        ValidationUtils.validateSSN(ssn) == result

        where:
        ssn           | type  || result
        "05011399292" | "fnr" || true
        "41011088188" | "dnr" || true
        "01020304050" | "fnr" || false
        "ABCDEFGHIJK" | "???" || false
        "123456"      | "???" || false
        "24101219220" | "fnr" || true
        "01000000040" | "fnr" || false
    }

    def "Should validate foreign security numbers (duf-nummer), duf = #duf, result = #result"() {
        expect:
        ValidationUtils.validateDUF(duf) == result

        where:
        duf            || result
        "201017238203" || true
        "200816832910" || true
        "201012345678" || false
        "ABCDEFGHIJKL" || false
        "123456"       || false
        "241012192200" || false
        "010000000400" || false
    }

    def "Should validate norwegian social security numbers (fnr), fnr = #fnr, result = #result"() {
        expect:
        ValidationUtils.validateFNR(fnr) == result

        where:
        fnr           | type  || result
        "05011399292" | "fnr" || true
        "41011088188" | "dnr" || true
        "05011300100" | "fnr" || true
        "05011300200" | "fnr" || true
        "05011355555" | "fnr" || true
        "05011399999" | "fnr" || true
        "24101219220" | "fnr" || true

        "01020304050" | "fnr" || false
        "ABCDEFGHIJK" | "???" || false
        "123456"      | "???" || false
        "01000000040" | "fnr" || false
    }

    def "Should validate convert the first digit of the dnr to valid date (dnr), dnr = #dnr, result = #result"() {
        expect:
        ValidationUtils.dnr2fnr(dnr) == result

        where:
        dnr           | type  || result
        "05011399292" | "fnr" || "050113"
        "41011088188" | "dnr" || "010110"
    }

    @Unroll
    def "getAge all scenarios"() {
        expect:
        expectedAge == ValidationUtils.getAge(fnr)

        where:
        fnr             | type  || expectedAge
        "05011399292"   | "fnr" || Year.now().value - 2013
        "41011088188"   | "dnr" || -1
        "01020304050"   | "fnr" || Year.now().value - 2003
        "a".repeat(11)  | "???" || -1
        null            | "???" || -2
        getSsnByAge(99) | "fnr" || -1
    }

    // util stuff from here

    static def createMeasure(LocalDate start, LocalDate end) {
        new TiltakType(
                UUID.randomUUID(),
                null,
                start,
                new LovhjemmelType("lov", "kapittel", "paragraf", ["ledd"], ["punktum"]),
                List.of(),
                new KategoriType("1", null),
                List.of(),
                List.of(),
                new OpphevelseType(RandomUtils.generateRandomString(10), null),
                new TiltakKonklusjonType(end)
        )
    }

    static def createDate(monthsAhead) {
        LocalDate.now().plusMonths(monthsAhead)
    }

    static def getSsnByAge(int age) {
        RandomUtils.generateRandomSSN(
                LocalDate.of(Year.now().minusYears(age).value, 1, 1),
                LocalDate.of(Year.now().minusYears(age - 1).value, 1, 1),
        )
    }

    def getSourceFromClasspath(String path) {
        try {
            return new StreamSource(getClass().getResourceAsStream(path))
        } catch (Exception ignored) {
            return null
        }
    }
}
