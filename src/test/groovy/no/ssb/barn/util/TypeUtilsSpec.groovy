package no.ssb.barn.util

import no.ssb.barn.xsd.CodeListItem
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class TypeUtilsSpec extends Specification {

    def codeListItems = List.of(getCodeListItem())

    @Unroll
    def "getCodes receive number of expected items"() {
        expect:
        expectedNumberOfItems == TypeUtils.getCodes(date, codeListItems).size()

        where:
        date || expectedNumberOfItems
        LocalDate.of(2019, 1, 1) || 0
        LocalDate.of(2021, 1, 1) || 1
        LocalDate.of(2101, 1, 1) || 0
    }

    static def getCodeListItem() {
        new CodeListItem("~code~", "~description~",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1),
                "~changeDescription~")
    }
}
