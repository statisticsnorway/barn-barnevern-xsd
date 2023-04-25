package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.Shared.controlSumDigits1
import no.ssb.barn.util.Shared.controlSumDigits2
import no.ssb.barn.util.Shared.modulo11

class SharedTest : BehaviorSpec({

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
        ) { ssn, controlDigits, expectedResidue ->

            When("modulo11 $ssn $controlDigits $expectedResidue") {
                val residue = shouldNotThrowAny {
                    modulo11(ssn, controlDigits)
                }

                Then("isValid should be as expected") {
                    residue shouldBe expectedResidue
                }
            }
        }
    }
})
