package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.Shared.TWO_DIGIT_YEAR_DATE_TIME_FORMATTER
import no.ssb.barn.util.SocialSecurityIdUtils.FEMALE
import no.ssb.barn.util.SocialSecurityIdUtils.INVALID_SSN_FORMAT_MSG
import no.ssb.barn.util.SocialSecurityIdUtils.MALE
import no.ssb.barn.util.SocialSecurityIdUtils.extractDateOfBirthFromSocialSecurityId
import no.ssb.barn.util.SocialSecurityIdUtils.extractGenderFromSocialSecurityId
import java.time.LocalDate
import java.time.format.DateTimeParseException

class SocialSecurityIdUtilsTest : BehaviorSpec({

    Given("extractGenderFromSocialSecurityId") {
        forAll(
            row("digit #9 is even, female", "01010112245", FEMALE),
            row("digit #9 is odd, male", "01010112345", MALE)
        ) { description, socialSecurityId, expectedGender ->
            When(description) {
                val gender = extractGenderFromSocialSecurityId(socialSecurityId)

                Then("gender should be as expected") {
                    gender.shouldBe(expectedGender)
                }
            }
        }

        When("invalid social security id") {
            forAll(
                row("invalid social security id", "a".repeat(11)),
                row("too short social security id", "a".repeat(11)),
            ) { description, socialSecurityId ->
                When(description) {
                    val thrown = shouldThrow<IllegalArgumentException> {
                        extractGenderFromSocialSecurityId(socialSecurityId)
                    }

                    Then("exception should be as expected") {
                        thrown.message.shouldBe(INVALID_SSN_FORMAT_MSG)
                    }
                }
            }
        }
    }

    Given("extractDateOfBirthFromSocialSecurityId") {
        val tomorrow = LocalDate.now().plusDays(1)

        forAll(
            row(
                "date of birth at the beginning of the year",
                "01010112345", LocalDate.of(2001, 1, 1)
            ),
            row(
                "date of birth at the end of the year",
                "31121012345", LocalDate.of(2010, 12, 31)
            ),
            row(
                "date of birth in the future, expect 100 years to be subtracted",
                "${TWO_DIGIT_YEAR_DATE_TIME_FORMATTER.format(tomorrow)}12345", tomorrow.minusYears(100)
            )
        ) { description, socialSecurityId, expectedDateOfBirth ->
            When("getDateOfBirthFromSsn $description, $socialSecurityId") {
                val dateOfBirth = extractDateOfBirthFromSocialSecurityId(socialSecurityId)

                Then("dateOfBirth should be as expected") {
                    dateOfBirth.shouldBe(expectedDateOfBirth)
                }
            }
        }

        When("social security id on wrong format") {
            forAll(
                row("social security id is letters only", "a".repeat(11)),
                row("too short social security id", "1".repeat(5)),
            ) { description, socialSecurityId ->
                When(description) {
                    val thrown = shouldThrow<IllegalArgumentException> {
                        extractDateOfBirthFromSocialSecurityId(socialSecurityId)
                    }

                    Then("exception should be as expected") {
                        thrown.message.shouldBe(INVALID_SSN_FORMAT_MSG)
                    }
                }
            }
        }

        When("social security id with invalid date of birth") {
            When("invalid social security id") {
                val thrown = shouldThrow<DateTimeParseException> {
                    extractDateOfBirthFromSocialSecurityId("9".repeat(11))
                }

                Then("exception should be as expected") {
                    thrown.message.shouldBe(
                        "Text '999999' could not be parsed: Invalid value for " +
                                "MonthOfYear (valid values 1 - 12): 99"
                    )
                }
            }
        }
    }
})
