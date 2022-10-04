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

    given("unmarshallXml") {

        `when`("invalid XML") {

            val exception = shouldThrow<JsonParseException> {
                unmarshallXml("~someXml~")
            }

            then("exception is as expected") {
                exception.message shouldContain "Unexpected character"
            }
        }

        `when`("valid XML") {

            val barnevernType = shouldNotThrowAny {
                unmarshallXml(getResourceAsString("test01_file09_total.xml"))
            }

            then("result should be non-null") {
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

    given("marshallXml") {

        val barnevernType = unmarshallXml(getResourceAsString("test01_file09_total.xml"))

        `when`("marshallXml") {

            val xml = shouldNotThrowAny {
                marshallInstance(barnevernType)
            }

            then("result should be non-null") {
                xml.shouldNotBeNull()
            }
        }
    }
})
