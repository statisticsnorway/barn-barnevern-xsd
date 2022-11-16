package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class UndersokelseTypeTest : BehaviorSpec({

    given("misc UndersokelseType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(buildBarnevernXml(
                    "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                            "StartDato=\"2022-11-14\"/>").toStreamSource())
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Undersokelse StartDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Undersokelse'."
            ),
            row(
                "empty Id",
                "<Undersokelse Id=\"\" StartDato=\"2022-11-14\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),
            row(
                "invalid Id",
                "<Undersokelse Id=\"42\" StartDato=\"2022-11-14\"/>",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"2022-11-14\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"2022-11-14\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Undersokelse'."
            ),
            row(
                "empty StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'date'."
            ),
            row(
                "invalid StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\"/>",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'date'."
            )
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})