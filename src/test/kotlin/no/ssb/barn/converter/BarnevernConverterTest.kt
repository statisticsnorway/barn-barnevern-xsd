package no.ssb.barn.converter

import com.fasterxml.jackson.core.JsonParseException
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.getResourceAsString
import no.ssb.barn.converter.BarnevernConverter.marshallInstance
import no.ssb.barn.converter.BarnevernConverter.unmarshallXml

class BarnevernConverterTest : BehaviorSpec({

    Given("unmarshallXml") {
        When("invalid XML") {
            val exception = shouldThrow<JsonParseException> {
                unmarshallXml("~someXml~")
            }

            Then("exception is as expected") {
                exception.message.shouldContain("Unexpected character")
            }
        }

        When("valid XML") {
            val barnevernType = shouldNotThrowAny {
                unmarshallXml(getResourceAsString("test01_file09_total.xml"))
            }

            Then("result should be non-null") {
                barnevernType.shouldNotBeNull()

                assertSoftly(barnevernType) {
                    avgiver.shouldNotBeNull()
                    datoUttrekk.shouldNotBeNull()
                    fagsystem.shouldNotBeNull()
                    sak.shouldNotBeNull()
                    sak.melding.shouldNotBeEmpty()
                }
            }
        }
    }

    Given("marshallXml") {
        val barnevernType = unmarshallXml(getResourceAsString("test01_file09_total.xml"))

        When("marshallXml with valid XML") {
            val xml = shouldNotThrowAny {
                marshallInstance(barnevernType)
            }

            Then("result should be non-null") {
                xml.shouldNotBeNull()
            }
        }
    }
})
