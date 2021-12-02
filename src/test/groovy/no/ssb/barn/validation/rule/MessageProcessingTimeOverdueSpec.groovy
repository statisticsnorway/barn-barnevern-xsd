package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageProcessingTimeOverdueSpec extends Specification {

    @Subject
    MessageProcessingTimeOverdue sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageProcessingTimeOverdue()
        context = getTestContext()
    }

    @Ignore
    @Unroll
    def "Startdato suksess-scenarier, ingen feil forventes"() {
        given:
        if (resetConclusion) {
            context.rootObject.sak.virksomhet[0].melding[0].konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        resetConclusion | _
        false           | _
        false           | _
        true            | _
    }
}
