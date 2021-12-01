package no.ssb.barn.validation.rule

import com.google.gson.Gson
import no.ssb.barn.framework.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageReporterContainsClarificationSpec extends Specification {

    @Subject
    MessageReporterContainsClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageReporterContainsClarification()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.konklusjon.sluttDato = messageEndDate
        and:
        message.melder[0].kode = code
        and:
        message.melder[0].presisering = clarification

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected){
            System.out.println(new Gson().toJson(reportEntries[0]))
        }

        where:
        messageEndDate  | code | clarification     || errorExpected
        LocalDate.now() | "22" | "~clarification~" || false
        LocalDate.now() | "22" | ""                || true
        LocalDate.now() | "21" | ""                || false
    }
}
