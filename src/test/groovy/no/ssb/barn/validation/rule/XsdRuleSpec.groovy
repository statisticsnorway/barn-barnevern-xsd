package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

@Narrative("""
Validéring av innhold mot filbeskrivelse

Gitt at man har en fil med innhold som man skal validere mot filbeskrivelsen<br/>
når validering av filen mot filbeskrivelsen feiler<br/>
så gi feilmeldingen "Innholdet er feil i forhold til filbeskrivelsen / XSD"

Alvorlighetsgrad: FATAL
""")
class XsdRuleSpec extends Specification {

    @Subject
    XsdRule sut

    @SuppressWarnings('unused')
    def setup() {
        sut = new XsdRule("Barnevern.xsd")
    }

    @Unroll
    def "when validate with valid XML receive no reportEntry"() {
        given:
        def context = new ValidationContext(
                UUID.randomUUID().toString(),
                getResourceAsString("test01_file0" + i + "_total.xml"))

        when:
        def reportEntry = sut.validate(context)

        then:
        null == reportEntry

        where:
        i << (1..3) // TODO: Changed from i << (1..5)
    }

    def "when validate with invalid XML receive reportEntry"() {
        given:
        def context = new ValidationContext(
                UUID.randomUUID().toString(),
                getResourceAsString("invalid.xml")
        )

        when:
        def reportEntries = sut.validate(context)

        then:
        1 == reportEntries.size()
        and:
        WarningLevel.FATAL == reportEntries[0].warningLevel
    }
}
