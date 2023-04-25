package no.ssb.barn.util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.RandomUtils.generateRandomSSN
import no.ssb.barn.util.RandomUtils.generateRandomString
import no.ssb.barn.util.Shared.controlSumDigits2
import no.ssb.barn.util.Shared.modulo11
import java.time.LocalDate

class RandomUtilsTest : BehaviorSpec({

    Given("generateRandomString") {
        val stringLength = 20

        When("generateRandomString with length: $stringLength") {
            val randomString = generateRandomString(stringLength)

            Then("randomString should be as expected") {
                randomString.length shouldBe stringLength
                @Suppress("RegExpSimplifiable")
                "[a-zA-Z0-9]{$stringLength}".toRegex().matches(randomString).shouldBeTrue()
            }
        }
    }

    Given("generateRandomSSN") {
        val startInclusive = LocalDate.of(2000, 1, 1)
        val endExclusive = LocalDate.of(2021, 1, 1)

        (1..10).forEach {
            When("generateRandomSSN $it") {

                val socialSecurityId = generateRandomSSN(
                    startInclusive = startInclusive,
                    endExclusive = endExclusive
                )

                Then("socialSecurityId should be valid") {
                    modulo11(socialSecurityId, controlSumDigits2) shouldBe 0
                }
            }
        }
    }
})
