package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.RandomUtils.INVALID_DATE_RANGE_MESSAGE
import no.ssb.barn.util.RandomUtils.INVALID_LEN_MESSAGE
import no.ssb.barn.util.RandomUtils.controlSumDigits1
import no.ssb.barn.util.RandomUtils.controlSumDigits2
import no.ssb.barn.util.RandomUtils.generateRandomSocialSecurityId
import no.ssb.barn.util.RandomUtils.generateRandomString
import no.ssb.barn.util.RandomUtils.modulo11
import java.time.LocalDate

class RandomUtilsTest : BehaviorSpec({

    Given("generateRandomString") {
        val expectedStringLength = 20

        When("generateRandomString with length: $expectedStringLength") {
            val randomString = generateRandomString(expectedStringLength)

            Then("randomString should be as expected") {
                randomString.length.shouldBe(expectedStringLength)
                @Suppress("RegExpSimplifiable")
                "[a-zA-Z0-9]{$expectedStringLength}".toRegex().matches(randomString).shouldBeTrue()
            }
        }

        forAll(
            row(
                "generateRandomString zero length",
                0
            ),
            row(
                "generateRandomString with negative length",
                -1
            )
        ) { description, length ->
            When(description) {
                val thrown = shouldThrow<java.lang.IllegalArgumentException> {
                    generateRandomString(length)
                }

                Then("randomString should be empty") {
                    thrown.message.shouldBe(INVALID_LEN_MESSAGE)
                }
            }
        }
    }

    Given("generateRandomSocialSecurityId") {
        (1..10).forEach {
            When("generateRandomSocialSecurityId run #$it") {
                val socialSecurityId = generateRandomSocialSecurityId(
                    startInclusive = LocalDate.now(),
                    endExclusive = LocalDate.now().plusDays(1)
                )

                Then("socialSecurityId should be valid") {
                    modulo11(
                        socialSecurityIdToCheck = socialSecurityId,
                        controlDigits = controlSumDigits2
                    ).shouldBe(0)
                }
            }
        }

        forAll(
            row(
                "generateRandomSocialSecurityId with startInclusive = endExclusive",
                LocalDate.now(),
                LocalDate.now()
            ),
            row(
                "generateRandomSocialSecurityId with startInclusive after endExclusive",
                LocalDate.now().plusDays(1),
                LocalDate.now()
            )
        ) { description, startDate, endDate ->
            When(description) {
                val thrown = shouldThrow<IllegalArgumentException> {
                    generateRandomSocialSecurityId(startDate, endDate)
                }

                Then("exception message should be as expected") {
                    thrown.message.shouldBe(INVALID_DATE_RANGE_MESSAGE)
                }
            }
        }
    }

    Given("modulo11") {
        forAll(
            row("123", controlSumDigits1, -1),
            row("a".repeat(10), controlSumDigits1, -1),

            row("0501139929", controlSumDigits1, 0),
            row("4101108818", controlSumDigits1, 0),
            row("0102030405", controlSumDigits1, 6),

            row("05011399292", controlSumDigits2, 0),
            row("41011088188", controlSumDigits2, 0),
            row("01020304050", controlSumDigits2, 8)
        ) { socialSecurityId, controlDigits, expectedResidue ->
            When("modulo11 $socialSecurityId $controlDigits $expectedResidue") {
                val residue = shouldNotThrowAny {
                    modulo11(
                        socialSecurityIdToCheck = socialSecurityId,
                        controlDigits = controlDigits
                    )
                }

                Then("residue should be as expected") {
                    residue.shouldBe(expectedResidue)
                }
            }
        }
    }
})
