package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.RandomUtils.generateRandomSSN
import no.ssb.barn.util.Shared.controlSumDigits1
import no.ssb.barn.util.Shared.controlSumDigits2
import no.ssb.barn.util.ValidationUtils.dnr2fnr
import no.ssb.barn.util.ValidationUtils.getAge
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import no.ssb.barn.util.ValidationUtils.modulo11
import no.ssb.barn.util.ValidationUtils.validateDUF
import no.ssb.barn.util.ValidationUtils.validateFNR
import no.ssb.barn.util.ValidationUtils.validateSSN
import java.time.LocalDate
import java.time.Year

class ValidationUtilsTest : BehaviorSpec({

    given("getSchemaValidator") {
        `when`("getSchemaValidator") {

            val schemaValidator = shouldNotThrowAny {
                getSchemaValidator()
            }

            then("schemaValidator should not be null") {
                schemaValidator.shouldNotBeNull()
            }
        }
    }

    given("validateSSN") {
        forAll(
            row("05011399292", "fnr", true),
            row("41011088188", "dnr", true),
            row("01020304050", "fnr", false),
            row("ABCDEFGHIJK", "N/A", false),
            row("123456", "N/A", false),
            row("24101219220", "fnr", true),
            row("01000000040", "fnr", false)
        ) { ssn, type, expectedValue ->

            `when`("validateSSN $ssn $type") {

                val isValid = shouldNotThrowAny {
                    validateSSN(ssn)
                }

                then("isValid should be as expected") {
                    isValid shouldBe expectedValue
                }
            }
        }
    }

    given("validateFNR") {
        forAll(
            row("05011399292", "fnr", true),
            row("41011088188", "dnr", true),
            row("05011300100", "fnr", true),
            row("05011300200", "fnr", true),
            row("05011399999", "fnr", true),
            row("24101219220", "fnr", true),

            row("01020304050", "fnr", false),
            row("ABCDEFGHIJK", "N/A", false),
            row("123456", "N/A", false),
            row("01000000040", "fnr", false),
            row("05011355555", "fnr", false)
        ) { ssn, type, expectedValue ->

            `when`("validateFNR $ssn $type") {

                val isValid = shouldNotThrowAny {
                    validateFNR(ssn)
                }

                then("isValid should be as expected") {
                    isValid shouldBe expectedValue
                }
            }
        }
    }

    given("validateDUF") {
        forAll(
            row("201017238203", true),
            row("200816832910", true),
            row("201012345678", false),
            row("ABCDEFGHIJKL", false),
            row("123456", false),
            row("241012192200", false),
            row("010000000400", false)
        ) { ssn, expectedValue ->

            `when`("validateDUF $ssn") {

                val isValid = shouldNotThrowAny {
                    validateDUF(ssn)
                }

                then("isValid should be as expected") {
                    isValid shouldBe expectedValue
                }
            }
        }
    }

    given("dnr2fnr") {
        forAll(
            row("05011399292", "fnr", "050113"),
            row("41011088188", "dnr", "010110")
        ) { ssn, type, expectedDateString ->

            `when`("dnr2fnr $ssn $type") {

                val dateString = shouldNotThrowAny {
                    dnr2fnr(ssn)
                }

                then("dateString should be as expected") {
                    dateString shouldBe expectedDateString
                }
            }
        }
    }

    given("modulo11") {
        forAll(
            row("0501139929", controlSumDigits1, 0),
            row("4101108818", controlSumDigits1, 0),
            row("0102030405", controlSumDigits1, 6),

            row("05011399292", controlSumDigits2, 0),
            row("41011088188", controlSumDigits2, 0),
            row("01020304050", controlSumDigits2, 8)
        ) { ssn, controlDigits, expectedResidue ->

            `when`("modulo11 $ssn $controlDigits $expectedResidue") {

                val residue = shouldNotThrowAny {
                    modulo11(ssn, controlDigits)
                }

                then("isValid should be as expected") {
                    residue shouldBe expectedResidue
                }
            }
        }
    }

    given("getAge") {
        forAll(
            row("05011399292", "fnr", Year.now().value - 2013),
            row("41011088188", "dnr", -1),
            row("01020304050", "fnr", Year.now().value - 2003),
            row("a".repeat(11), "N/A", -1),
            row(null, "N/A", -2),
            row(getSsnByAge(99), "fnr", -1),
        ) { ssn, type, expectedAge ->

            `when`("getAge $ssn $type $expectedAge") {

                val age = shouldNotThrowAny {
                    getAge(ssn)
                }

                then("isValid should be as expected") {
                    age shouldBe expectedAge
                }
            }
        }
    }
}) {
    companion object {
        fun getSsnByAge(age: Long) = generateRandomSSN(
            LocalDate.of(Year.now().minusYears(age).value, 1, 1),
            LocalDate.of(Year.now().minusYears(age - 1).value, 1, 1),
        )
    }
}
