# Kravspesifikasjon for validering

## Innhold
- [Definisjoner](#definisjoner)
- Validéringer
  - [Filbeskrivelse](#filbeskrivelse)
  - [Avgiver](#avgiver)
  - [Sak](#sak)
  - [Melding](#melding)
    - [Melder](#melder)
    - [Saksinnhold](#saksinnhold)
  - [Undersøkelse](#undersokelse)
  - [Vedtak](#vedtak)
  - [Tiltak](#tiltak)
  - [Plan](#plan)
  - [Ettervern](#ettervern)
  - [Flytting](#flytting)
  - [Oversendelse til fylkesnemnd](#oversendelse-til-fylkesnemnd)


## <a name="definisjoner">Definisjoner</a>


### <a name="omsorgstiltak">Omsorgstiltak</a>
Et Tiltak er et Omsorgsstiltak dersom en av følgende:
- Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 12
- Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 8 og Lovhjemmel/Ledd er én av 2 eller 3
- Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 8 og én av JmfrLovhjemmel/Kapittel = 4 og JmfrLovhjemmel/Paragraf = 12


### <a name="plasseringstiltak">Plasseringstiltak</a>
Et Tiltak er et plasseringstiltak dersom Kategori/@Kode er en av følgende koder:<br/>
1.1, 1.2, 1.99, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.99 eller 8.2<br/>



## <a name="filbeskrivelse">Filbeskrivelse</a>

### Validéring av innhold mot filbeskrivelse

Gitt at man har en fil med innhold som man skal validere mot filbeskrivelsen<br/>
når validering av filen mot filbeskrivelsen feiler<br/>
så gi feilmeldingen "Innholdet er feil i forhold til filbeskrivelsen / XSD"

Alvorlighetsgrad: FATAL

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/XsdRuleSpec.groovy) 

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/XsdRule.kt)


## <a name="avgiver">Avgiver</a>

### Avgiver Kontroll 1: Bydelsnummer og bydelsnavn

Gitt at man har en Avgiver der Organisasjonsnummer er en av 958935420 (Oslo), 964338531 (Bergen) eller 942110464 (Trondheim)<br/>
når Bydelsnummer eller Bydelsnavn mangler utfylling<br/>
så gi feilmeldingen "Bydelsnummer og/eller Bydelsnavn skal være utfylt"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/ReporterUrbanDistrictNumberAndNameSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/ReporterUrbanDistrictNumberAndName.kt)




## <a name="sak">Sak</a>

### Sak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Sak der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Sakens startdato {StartDato} er etter sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseEndDateAfterStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseEndDateAfterStartDate.kt)



### Sak Kontroll 3: Fødselsnummer og DUFnummer

Gitt at man har en Sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Feil i fødselsnummer. Kan ikke identifisere klienten."

Gitt at man har en Sak<br/>
når fødselsnummer mangler og DUF-nummer mangler <br/>
så gi feilmeldingen "DUFnummer mangler. Kan ikke identifisere klienten."

Gitt at man har en Sak<br/>
når fødselsnummer og DUF-nummer mangler <br/>
så gi feilmeldingen "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere klienten."

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseSocialSecurityIdAndDufSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseSocialSecurityIdAndDuf.kt)



### Sak Kontroll 6: Klienten skal ha melding, plan eller tiltak

Gitt at man har en Sak <br/>
når saken mangler melding, plan og tiltak<br/>
så gi feilmeldingen "Klienten har ingen meldinger, planer eller tiltak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseHasContentSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseHasContent.kt)



### Sak Kontroll 7: Klient over 25 år og skal avsluttes i barnevernet

Gitt at man har en Sak med datoUttrekk og fødselsdato<br/>
når datoUttrekk minus fødselsdato er lik 25 år eller større<br/>
så gi feilmeldingen "Klienten er over 25 år og skal avsluttes som klient"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseAgeAboveTwentyFiveSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseAgeAboveTwentyFive.kt)




### Sak Kontroll 8: Klient over 18 år skal ha tiltak

Gitt at man har en Sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 18 eller større og tiltak mangler <br/>
så gi feilmeldingen "Klienten er over 18 år og skal dermed ha tiltak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseAgeAboveEighteenAndMeasuresSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseAgeAboveEighteenAndMeasures.kt)





## <a name="melding">Melding</a> 

### Melding Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Melding der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageStartDateAfterEndDate.kt)



### Melding Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Melding der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når meldingens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Meldingens sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageEndDateAfterCaseEndDate.kt)



### Melding Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Melding med StartDato og i sak med StartDato<br/>
når meldingens StartDato er før sakens StartDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageStartDateBeforeCaseStartDate.kt)



### Melding Kontroll 3: Fristoverskridelse på behandlingstid

Gitt at man har en Melding der StartDato og Konklusjon/SluttDato finnes<br/>
når Konklusjon/SluttDato er mer enn 7 dager etter StartDato<br/>
så gi feilmeldingen "Fristoverskridelse på behandlingstid for melding, ({StartDato} -> {Konklusjon/SluttDato})"

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageProcessingTimeOverdueSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageProcessingTimeOverdue.kt)



### Melding Kontroll 4: Konkludert melding mangler melder

Gitt at man har en Melding<br/>
når Konklusjon finnes og 1 eller flere Melder(e) mangler<br/>
så gi feilmeldingen "Konkludert melding mangler melder(e)"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageMissingReportersSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageMissingReporters.kt)



### Melding Kontroll 5: Konkludert melding mangler saksinnhold

Gitt at man har en Melding<br/>
når Konklusjon finnes og 1 eller flere Saksinnhold mangler<br/>
så gi feilmeldingen "Konkludert melding mangler saksinnhold"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageMissingCaseContentSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageMissingCaseContent.kt)



### <a name="melder">Melder</a> 

#### Melder Kontroll 2: Mangler Presisering

Gitt at man har en Melder der Kode er 22 (= Andre offentlige instanser)<br/>
når Melder mangler Presisering<br/>
så gi feilmeldingen "Melder med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageReporterMissingClarificationSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageReporterMissingClarification.kt)



### <a name="saksinnhold">Saksinnhold</a> 

#### Saksinnhold Kontroll 2: Mangler Presisering

Gitt at man har et Saksinnhold der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Saksinnhold mangler Presisering<br/>
så gi feilmeldingen "Saksinnhold med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageReporterMissingClarificationSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageReporterMissingClarification.kt)



## <a name="undersokelse">Undersøkelse</a> 

### Undersøkelse Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Undersøkelse der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationStartDateAfterEndDate.kt)



### Undersøkelse Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Undersøkelse der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når undersøkelsens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Undersøkelsens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationEndDateAfterCaseEndDate.kt)



### Undersøkelse Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Undersøkelse der StartDato finnes og sak der StartDato finnes<br/>
når undersøkelsens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationStartDateBeforeCaseStartDate.kt)



### Undersøkelse Kontroll 3: Vedtaksgrunnlag mangler presisering

Gitt at man har et Vedtaksgrunnlag der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Vedtaksgrunnlag mangler Presisering<br/>
så gi feilmeldingen "Vedtaksgrunnlag med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationDecisionMissingClarificationSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationDecisionMissingClarification.kt)



### Undersøkelse Kontroll 7: Konkludert undersøkelse mangler vedtaksgrunnlag

Gitt at man har en Undersøkelse der Konklusjon finnes og Konklusjon sin Kode er 1 eller 2<br/>
når Vedtaksgrunnlag mangler<br/>
så gi feilmeldingen "Undersøkelse konkludert med kode {Konklusjon/Kode} mangler vedtaksgrunnlag"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationConcludedMissingDecisionSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationConcludedMissingDecision.kt)



### Undersøkelse Kontroll 11: Fristoverskridelse på behandlingstid i forhold til melding sin startdato

[TODO] trenger en gjennomgang da denne må referere til 3/6 måneder stedet for 90/180 dager

Gitt at man har en ukonkludert Undersøkelse med en Relasjon til en Melding<br/> 
der relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType<br/>
og undersøkelse sin Konklusjon/SluttDato mangler <br/>

når Datouttrekk er mer enn 7 + 90 dager etter Melding sin StartDato <br/> 
og UtvidetFrist sin Invilget enten mangler eller er lik 2 (= "Nei"), <br/> 
så gi feilmeldingen "Undersøkelse skal konkluderes innen 7 + 90 dager etter melding sin startdato"

når Datouttrekk er mer enn 7 + 180 dager etter Melding sin StartDato <br/>
så gi feilmeldingen "Undersøkelse skal konkluderes innen 7 + 180 dager etter melding sin startdato"

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationProcessingTimePassedDueDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationProcessingTimePassedDueDate.kt)



### Undersøkelse Kontroll 20: Undersøkelse skal ha relasjon fra melding

Gitt at man har en Undersøkelse, en Relasjon og en Melding<br/>
når en relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType mangler<br/>
så gi feilmeldingen "Undersøkelse mangler en relasjon fra melding"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationRelatedFromMessageSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationRelatedFromMessage.kt)






## <a name="vedtak">Vedtak</a> 

### Vedtak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Vedtak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStartDateAfterEndDate.kt)



### Vedtak Kontroll 2c: SluttDato mot sakens SluttDato

Gitt at man har et Vedtak der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når vedtakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Vedtakets sluttdato {Konklusjon/SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionEndDateAfterCaseEndDate.kt)



### Vedtak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Vedtak der StartDato finnes og sak der StartDato finnes<br/>
når vedtakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDate.kt)





### Vedtak Kontroll 2f: Krav sin StartDato er etter SluttDato

Gitt at man har et Vedtak der Krav finnes StartDato og Konklusjon/SluttDato finnes<br/>
når for hvert krav validér at kravets StartDato er etter SluttDato <br/>
så gi feilmeldingen "Kravets startdato {StartDato} er etter sluttdato {Krav/Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDate.kt)




## <a name="tiltak">Tiltak</a> 

### Tiltak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Tiltak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureStartDateAfterEndDate.kt)



### Tiltak Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Tiltak der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når tiltakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Tiltakets sluttdato {Konklusjon/SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageEndDateAfterCaseEndDate.kt)



### Tiltak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Tiltak der StartDato finnes og sak der StartDato finnes<br/>
når tiltakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureStartDateBeforeCaseStartDate.kt)


### Tiltak Kontroll 5: Barnet er over 7 år og er i barnehage

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 7 år og tiltakets kategori er '4.1' Barnehage<br/>
så gi feilmelding "Barnet er over 7 år og i barnehage."

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureAgeAboveSevenAndInKindergartenSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureAgeAboveSevenAndInKindergarten.kt)


### Tiltak Kontroll 6: Barnet er over 11 år og i SFO

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 11 år og tiltakets kategori er '4.2' SFO/AKS<br/>
så gi feilmelding "Barnet er over 11 år og i SFO"

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureAgeAboveElevenAndInSfoSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureAgeAboveElevenAndInSfo.kt)


### Tiltak Kontroll 7: Kontroll om presisering av tiltakskategori

Gitt at man har et Tiltak der Kategori/Kode er en følgende koder:
1.99, 2.99, 3.7, 3.99, 4.99, 5.99, 6.99, 7.99 eller 8.99<br/>
når presisering mangler<br/>
så gi feilmelding "Tiltakskategori (kode) mangler presisering."

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureClarificationRequiredSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureClarificationRequired.kt)


### Tiltak Kontroll 8: Kontroll av kode og presisering av opphevelse

Gitt at man har et Tiltak der Opphevelse/Kode er 4<br/>
når presisering mangler<br/>
så gi feilmelding "Opphevelse (kode) mangler presisering."

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureRepealClarificationRequiredSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureRepealClarificationRequired.kt)



### Tiltak Kontroll 9: Flere plasseringstiltak er oppgitt i samme tidsperiode

Gitt at man har 2 eller flere [Plasseringstiltak](#plasseringstiltak)<br/>
og for de plasseringstiltakene der SluttDato mangler så brukes DatoUttrekk i stedet<br/>
når plasseringstiltak1 overlapper plasseringstiltak2 med mer enn 90 dager<br/>
så gi feilmelding "Flere plasseringstiltak i samme periode (PeriodeStartDato - PeriodeSluttDato). Plasseringstiltak kan ikke overlappe med mer enn 90 dager."

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureMultipleAllocationsWithinPeriodSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureMultipleAllocationsWithinPeriod.kt)



#### Tiltak Kontroll 12: Omsorgstiltak med sluttdato krever årsak til opphevelse

Gitt at man har et [Omsorgstiltak](#omsorgstiltak) med Konklusjon/SluttDato<br/>
når Opphevelse/Kode mangler
så gi feilmelding "Omsorgstiltak med sluttdato krever årsak til opphevelse"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureLegalBasisWithEndDateClarificationRequiredSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureLegalBasisWithEndDateClarificationRequired.kt)



#### Tiltak Kontroll 13: Individ er over 18 år og har omsorgtiltak

Gitt at man har UttrekkDato, en Sak med fødselsdato og et [Omsorgstiltak](#omsorgstiltak)<br/>
når UttrekkDato er 18 år eller mer etter fødselsdato<br/>
så gi feilmelding "Individet er over 18 år skal dermed ikke ha omsorgstiltak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureLegalBasisAgeAboveEighteenNoMeasureSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureLegalBasisAgeAboveEighteenNoMeasure.kt)



#### Tiltak Kontroll 14: Lovhjemmel er fylt ut med tallet 0

Gitt at det er en sak med tiltak <br/>
når Lovhjemmel sin kapittel, paragraf, ledd eller punktum er utfylt med 0
så gi feilmelding "Ingen Lovhjemmel sin kapittel, paragraf, ledd eller punktum kan være utfylt med 0"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureLegalBasisValidCodeSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureLegalBasisValidCode.kt)



#### [TODO] Tiltak Kontroll 20: Tiltak skal ha relasjon fra Vedtak

Gitt at det er et Tiltak, en Relasjon og et Vedtak <br/>
når en relasjon som inneholder vedtak/Id i sin FraId, "Vedtak" i sin FraType, tiltak/Id i sin TilId og "Tiltak" i sin TilType mangler<br/>
så gi feilmelding "Tiltak skal ha relasjon fra Vedtak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/CHANGE_HERE_Spec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/CHANGE_HERE.kt)




## <a name="plan">Plan</a> 

### Plan Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Plan der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Planens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/plan/PlanStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/plan/PlanStartDateAfterEndDate.kt)



### Plan Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Plan der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når planens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Planens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/plan/PlanEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/plan/PlanEndDateAfterCaseEndDate.kt)



### Plan Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Plan der StartDato finnes og sak der StartDato finnes<br/>
når planens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Planens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/plan/PlanStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/plan/PlanStartDateBeforeCaseStartDate.kt)


### Plan Kontroll 2f: UtfortDato er etter sakens SluttDato

Gitt at man har en Plan der Evaluering/UtfortDato finnes og Konklusjon/SluttDato finnes<br/>
når UtfortDato er etter SluttDato<br/>
så gi feilmeldingen "Utført evaluering {Evaluering/UtfortDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/plan/PlanEvaluationDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/plan/PlanEvaluationDateAfterCaseEndDate.kt)



### Plan Kontroll 2g: UtfortDato er før sakens StartDato

Gitt at man har en Plan der Evaluering/UtfortDato finnes<br/>
når UtfortDato er før StartDato<br/>
så gi feilmeldingen "Utført evaluering {Evaluering/UtfortDato} er før startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/plan/PlanEvaluationDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/plan/PlanEvaluationDateBeforeCaseStartDate.kt)




## <a name="ettervern">Ettervern</a> 

### [TODO] Ettervern Kontroll 2a: TilbudSendtDato er etter SluttDato

Gitt at man har et Ettervern der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Ettervernets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareStartDateAfterEndDate.kt)



### [TODO] Ettervern Kontroll 2c: SluttDato mot sakens SluttDato
### Plan Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Ettervern der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når planens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Ettervern sin sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareEndDateAfterCaseEndDate.kt)



### [TODO] Ettervern Kontroll 2e: StartDato mot sakens StartDato


## <a name="flytting">Flytting</a> 

### [TODO] Plan Kontroll 2c: SluttDato mot sakens SluttDato

### [TODO] Plan Kontroll 2f: SluttDato mot sakens StartDato


## <a name="oversendelse-til-fylkesnemnd">Oversendelse til fylkesnemnd</a> 

### [TODO] Plan Kontroll 2c: StartDato mot sakens SluttDato

### [TODO] Plan Kontroll 2e: StartDato mot sakens StartDato
