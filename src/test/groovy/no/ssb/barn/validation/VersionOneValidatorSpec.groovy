package no.ssb.barn.validation

import no.ssb.barn.framework.ValidationContext
import spock.lang.Specification

class VersionOneValidatorSpec extends Specification {

    VersionOneValidator sut

    @SuppressWarnings('unused')
    def setup(){
        sut = new VersionOneValidator()
    }

    def "when validate with valid XML receive report without entries"() {
        given:
        def context = new ValidationContext(
                getResourceAsText("test01_fil01.xml"))

        when:
        def report = sut.validate(context)

        then:
        0 == report.getReportEntries().size()
    }

    def "when validate with invalid XML receive report with one entry"() {
        given:
        def context = new ValidationContext(
                getResourceAsText("invalid.xml")
        )

        when:
        def report = sut.validate(context)

        then:
        1 == report.getReportEntries().size()
    }

    def getResourceAsText(String resourceName) {
        return new String(getClass().getClassLoader()
                .getResourceAsStream(resourceName).readAllBytes())
    }
}
