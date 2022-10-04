package no.ssb.barn.util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.RandomUtils.generateRandomSSN
import no.ssb.barn.util.RandomUtils.generateRandomString
import no.ssb.barn.util.ValidationUtils.validateSSN
import java.time.LocalDate

class RandomUtilsTest : BehaviorSpec({

    given("generateRandomString") {

        val stringLength = 20

        `when`("generateRandomString with length: $stringLength") {

            val randomString = generateRandomString(stringLength)

            then("randomString should be as expected") {
                randomString.length shouldBe stringLength
                @Suppress("RegExpSimplifiable")
                "[a-zA-Z0-9]{$stringLength}".toRegex().matches(randomString).shouldBeTrue()
            }
        }
    }

    given("generateRandomSSN") {

        val startInclusive = LocalDate.of(2000, 1, 1)
        val endExclusive = LocalDate.of(2021, 1, 1)

        `when`("generateRandomSSN") {

            val socialSecurityId = generateRandomSSN(startInclusive, endExclusive)

            then("socialSecurityId should be valid") {
                validateSSN(socialSecurityId).shouldBeTrue()
            }
        }
    }
})
