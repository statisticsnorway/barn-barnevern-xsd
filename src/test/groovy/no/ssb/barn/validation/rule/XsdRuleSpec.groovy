package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class XsdRuleSpec extends Specification {

    XsdRule sut

    @SuppressWarnings('unused')
    def setup() {
        sut = new XsdRule("Barnevern.xsd")
    }

    def "when validate with valid XML receive no reportEntry"() {
        given:
        def context = new ValidationContext(
                UUID.randomUUID().toString(),
                getResourceAsString("test01_fil01.xml"))

        when:
        def reportEntry = sut.validate(context)

        then:
        null == reportEntry
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
