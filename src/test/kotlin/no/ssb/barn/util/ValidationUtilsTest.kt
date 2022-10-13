package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import no.ssb.barn.util.ValidationUtils.getSchemaValidator

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
})
