package no.ssb.barn.util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.SocialSecurityIdUtils.FEMALE
import no.ssb.barn.util.SocialSecurityIdUtils.MALE
import no.ssb.barn.util.SocialSecurityIdUtils.getDateOfBirthFromSsn
import no.ssb.barn.util.SocialSecurityIdUtils.getGenderFromSsn
import java.time.LocalDate

class SocialSecurityIdUtilsTest : BehaviorSpec({

    given("getGenderFromSsn") {

        forAll(
            row("digit #9 is even, female", "01010112245", FEMALE),
            row("digit #9 is odd, male", "01010112345", MALE)
        ) { description, ssn, expectedGender ->

            `when`(description) {

                val gender = getGenderFromSsn(ssn)

                then("gender should be as expected") {
                    gender shouldBe expectedGender
                }
            }
        }
    }

    given("getDateOfBirthFromSsn") {

        forAll(
            row("01010112345", LocalDate.of(2001, 1, 1)),
            row("31121012345", LocalDate.of(2010, 12, 31))
        ) { ssn, expectedBirthDate ->

            `when`("getDateOfBirthFromSsn $ssn") {

                val birthDate = getDateOfBirthFromSsn(ssn)

                then("birthDate should be as expected") {
                    birthDate shouldBe expectedBirthDate
                }
            }
        }
    }
})
