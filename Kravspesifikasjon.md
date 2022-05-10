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


### <a name="relasjon">Relasjon</a>
En Relasjon beskriver en kobling mellom to begrep. For eksempel fra Vedtak til Tiltak vil Relasjon inneholder vedtaket sin Id i sin FraId, "Vedtak" i sin FraType, tiltakets Id i sin TilId og "Tiltak" i sin TilType




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



### [TODO] Sak Kontroll 12: Kontroll av Fødselsnummer

Gitt at man har en Sak med fødselsnummer<br/>
når fødselsnummer ikke oppfyller noen følgende definisjoner:<br/>
* Fødselsnummer på [https://www.udi.no/ord-og-begreper/fodselsnummer/](https://www.udi.no/ord-og-begreper/fodselsnummer/),
* D-nummer på [https://www.udi.no/ord-og-begreper/d-nummer/](https://www.udi.no/ord-og-begreper/d-nummer/),
* fødselsdato (DDMMÅÅ) + 00100,
* fødselsdato (DDMMÅÅ) + 00200,
* termindato (DDMMÅÅ) + 99999

så gi feilmeldingen "Ugyldig Fødselsnummer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/Case12SocialSecurityIdSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/Case12SocialSecurityId.kt)



### [TODO] Sak Kontroll 13: Kontroll av Fødselsnummer og Fødselsdato

Gitt at man har en Sak med fødselsnummer og fødselsdato<br/>
når datodelen i fødselsnummer (de 6 første karakterene) viser til en annen dato en fødselsdato<br/>
så gi feilmeldingen "Fødselsnummer og Fødselsdato viser til forskjellige datoer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/Case13SocialSecurityIdVsBirthdateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/Case13SocialSecurityIdVsBirthdate.kt)



### [TODO] Sak Kontroll 14: Kontroll av Fødselsnummer og Kjønn

Gitt at man har en Sak med fødselsnummer og kjønn<br/>
når de 5 siste karakterene i fødselsnummer er ulik 99999 og den 9. karakteren i fødselsnummer modulus 2 er lik 0 er forskjellig fra koden for kjønn<br/>
så gi feilmeldingen "Fødselsnummer og Kjønn viser til forskjellige kjønn"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/Case14SocialSecurityIdVsGenderSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/Case14SocialSecurityIdVsGender.kt)



### [TODO] Sak Kontroll 15: Kontroll av Fødselsnummer, fødselsdato og StartDato

Gitt at man har en Sak med fødselsnummer, fødselsdato og startdato<br/>
når startdato er før fødselsdato og de 5 siste karakterene i fødselsnummer er ulik 99999<br/>
så gi feilmeldingen "Feil i Fødselsnummer for ufødt barn"

Gitt at man har en Sak med fødselsnummer, startdato og datouttrekk<br/>
når startdato er etter fødselsdato og de 5 siste karakterene i fødselsnummer er lik 99999<br/>
så gi feilmeldingen "Feil i Fødselsnummer for født barn"


Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/Case15SocialSecurityIdAndBirthDateAndStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/Case15SocialSecurityIdAndBirthDateAndStartDate.kt)



### [TODO] Sak Kontroll 16: Kontroll av Fødselsdato og StartDato

Gitt at man har en Sak med fødselsdato og startdato<br/>
når startdato er mer enn 9 måneder før fødselsdato<br/>
så gi feilmeldingen "Sakens startdato {StartDato} er mer enn 9 måneder før sakens fødselsdato"



Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/Case16BirthDateAndStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/Case16BirthDateAndStartDate.kt)



### [TODO] Sak Kontroll 1x: Kontroll av DUF-nummer

Gitt at man har en Sak med utfylt DUF-nummer<br/>
når DUF-nummer ikke oppfyller noen følgende definisjoner:<br/>
* DUF-nummer [https://www.udi.no/ord-og-begreper/duf-nummer/](https://www.udi.no/ord-og-begreper/duf-nummer/)

så gi feilmeldingen "Ugyldig DUF-nummer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/cases/CaseSocialSecurityIdSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/cases/CaseSocialSecurityId.kt)





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


### [TODO] Melding Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Meldinger<br/>
når sammenligner meldingene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere meldinger med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/message/MessageDuplicateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/message/MessageDuplicate.kt)



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


### [TODO] Undersøkelse Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Undersøkelser<br/>
når sammenligner Undersøkelsene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere Undersøkelser med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationDuplicateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationDuplicate.kt)



### Undersøkelse Kontroll 20: Undersøkelse skal ha relasjon fra melding

Gitt at man har en Undersøkelse, en Relasjon og en Melding<br/>
når en relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType mangler<br/>
så gi feilmeldingen "Undersøkelse mangler en relasjon fra melding"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationRelatedFromMessageSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationRelatedFromMessage.kt)



### [TODO] Undersøkelse Kontroll 21: Konkludert Undersøkelse skal ha relasjon til Vedtak

Gitt at man har en Undersøkelse, en Relasjon og et Vedtak<br/>
når Undersøkelse sin Konklusjon finnes og Konklusjon sin Kode er 1 eller 2 <br/>
og en relasjon som inneholder undersøkelse/Id i sin FraId, "Undersokelse" i sin FraType, vedtak/Id i sin TilId og "Vedtak" i sin TilType mangler<br/>
så gi feilmeldingen "Konkludert Undersøkelse mangler en relasjon til vedtak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/investigation/InvestigationRelatedToDecisionSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/investigation/InvestigationRelatedToDecision.kt)





## <a name="vedtak">Vedtak</a> 

### Vedtak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Vedtak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStartDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStartDateAfterEndDate.kt)



### Vedtak Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Vedtak der Konklusjon/SluttDato finnes og i en Sak der SluttDato finnes<br/>
når vedtakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Vedtakets sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionEndDateAfterCaseEndDate.kt)



### Vedtak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Vedtak der StartDato finnes og en Sak der StartDato finnes<br/>
når vedtakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStartDateBeforeCaseStartDate.kt)



### [TODO] Vedtak Kontroll 2f: Krav sin StartDato er etter SluttDato

Gitt at man har et Vedtak der Krav finnes StartDato og Konklusjon/SluttDato finnes<br/>
når for hvert krav validér at kravets StartDato er etter SluttDato <br/>
så gi feilmeldingen "Kravets startdato {StartDato} er etter sluttdato {Krav/Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionDemandStartDateBeforeDecisionStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionDemandStartDateBeforeDecisionStartDate.kt)



### [TODO] Vedtak Kontroll 2g: Krav sin SluttDato er etter vedtakets SluttDato

Gitt at man har et Vedtak der SluttDato finnes og Vedtaket har Krav der Krav sin Konklusjon/SluttDato finnes<br/>
når kravets SluttDato er etter vedtakets SluttDato<br/>
så gi feilmeldingen "Kravets sluttdato {Krav/Konklusjon/SluttDato} er etter Vedtakets sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionDemandEndDateAfterDecisionEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionDemandEndDateAfterDecisionEndDate.kt)



### [TODO] Vedtak Kontroll 2h: Krav sin StartDato er før vedtakets StartDato

Gitt at man har et Vedtak der StartDato finnes og Vedtaket har Krav der StartDato finnes<br/>
når kravets StartDato er før vedtakets StartDato <br/>
så gi feilmeldingen "Kravets startdato {Krav/StartDato} er før vedtakets startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionDemandStartDateBeforeDecisionStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionDemandStartDateBeforeDecisionStartDate.kt)



### [TODO] Vedtak Kontroll 2i: Status sin EndretDato er etter vedtakets SluttDato

Gitt at man har et Vedtak der SluttDato finnes og Vedtaket har Status der Status sin EndretDato finnes<br/>
når statusens EndretDato er etter vedtakets SluttDato<br/>
så gi feilmeldingen "Status sin endretdato {Status/EndretDato} er etter Vedtakets sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStatusChangeDateAfterDecisionEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStatusChangeDateAfterDecisionEndDate.kt)



### [TODO] Vedtak Kontroll 2j: Status sin StartDato er før vedtakets StartDato

Gitt at man har et Vedtak der StartDato finnes og Vedtaket har Status der Status sin EndretDato finnes<br/>
når statusens EndretDato er før vedtakets StartDato <br/>
så gi feilmeldingen "Kravets startdato {Krav/StartDato} er før vedtakets startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionStatusChangeStartDateBeforeDecisionStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionStatusChangeStartDateBeforeDecisionStartDate.kt)



### [TODO] Vedtak Kontroll 3: Avsluttet vedtak skal ha SluttDato

Gitt at man har et Vedtak der Status = 3 eller 4<br/>
når vedtakets status = 3 og Konklusjon/SluttDato mangler<br/>
så gi feilmeldingen "Vedtak med status 3 = 'Utgår / Bortfalt etter BVL' mangler sluttdato"<br/>

når vedtakets status = 4 og Konklusjon/SluttDato mangler<br/>
så gi feilmeldingen "Vedtak med status 4 = 'Avslått / Avsluttet' mangler sluttdato"


Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionRelationToForwardingSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionRelationToForwarding.kt)



### [TODO] Vedtak Kontroll 4: Lovhjemmel refererer til feil BarneVernLov

Gitt at man har et Vedtak der Vedtak/LovhjemmelType/Lov og eventuelt Vedtak/JmfrLovhjemmelType/Lov er utfylt<br/>
når Lov er utfylt med noe annet enn BVL1992 eller BVL2021<br/>
så gir feilmeldingen “Lovhjemlene under Vedtak innholder annen verdi for Lov enn BVL1992 eller BVL2021“


Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionLawVersionSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionLawVersion.kt)



### [TODO] Vedtak Kontroll 20: Vedtak med status 2 = 'Begjæring oversendt nemnd' skal ha relasjon til OversendelseFylkesnemnd

Gitt at man har et Vedtak der Status = 2<br/>
når Relasjon mellom Vedtak og OversendelseFylkesnemnd mangler <br/>
så gi feilmeldingen "Vedtak med status 2 = 'Begjæring oversendt nemnd' skal ha relasjon til OversendelseFylkesnemnd"

når Relasjon mellom Vedtak og OversendelseFylkesnemnd finnes, men OversendelseFylkesnemnd mangler <br/>
så gi feilmeldingen "OversendelseFylkesnemndVedtak skal finnes for Vedtak med status 2 = 'Begjæring oversendt nemnd'"


Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/decision/DecisionRelationToForwardingSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/decision/DecisionRelationToForwarding.kt)








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
så gi feilmeldingen "Tiltakets sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

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


### [TODO] Tiltak Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Tiltak<br/>
når sammenligner Tiltakene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere Tiltak med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureDuplicateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureDuplicate.kt)



#### [TODO] Tiltak Kontroll 20: Tiltak skal ha relasjon fra Vedtak

Gitt at det er et Tiltak, en Relasjon og et Vedtak <br/>
når en relasjon som inneholder vedtak/Id i sin FraId, "Vedtak" i sin FraType, tiltak/Id i sin TilId og "Tiltak" i sin TilType mangler<br/>
så gi feilmelding "Tiltak skal ha relasjon fra Vedtak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureRelatedFromDecisionSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureRelatedFromDecision.kt)



#### [TODO] Tiltak Kontroll 21: Tiltak sin Lovhjemmel skal ha baseres på det relaterte Vedtak sin Lovhjemmel

Gitt at det er et Tiltak, en Relasjon og et Vedtak <br/>
når en [Relasjon](#relasjon) fra Vedtak til Tiltak finnes,<br/>
men Vedtak/Lovhjemmel/Lov er ulik Tiltak/Lovhjemmel/Lov<br/>
eller Vedtak/Lovhjemmel/Kapittel er ulik Tiltak/Lovhjemmel/Kapittel<br/>
eller Vedtak/Lovhjemmel/Paragraf er ulik Tiltak/Lovhjemmel/Paragraf<br/>
så gi feilmelding "Tiltak er basert på annen Lovhjemmel enn det som er definert i Vedtak"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/measure/MeasureLawReferenceRelatedFromDecisionLawReferenceSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/measure/MeasureLawReferenceRelatedFromDecisionLawReference.kt)




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
så gi feilmeldingen "Ettervernets tilbudSendtDato {TilbudSendtDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareOfferSentDateAfterEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareOfferSentDateAfterEndDate.kt)



### [TODO] Ettervern Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Ettervern der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når planens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Ettervern sin sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareEndDateAfterCaseEndDate.kt)



### [TODO] Ettervern Kontroll 2e: TilbudSendtDato er før sakens StartDato

Gitt at man har et Ettervern der TilbudSendtDato finnes og sak der StartDato finnes<br/>
når ettervernets TilbudSendtDato er før sakens StartDato <br/>
så gi feilmeldingen "Ettervern sin tilbud-sendt-dato {TilbudSendtDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareOfferSentDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareOfferSentDateBeforeCaseStartDate.kt)



### [TODO] Ettervern Kontroll 3: Alder er mindre enn 18 år

Gitt at man har et Ettervern der TilbudSendtDato finnes og sak der Fodselsdato finnes<br/>
når ettervernets TilbudSendtDato minus sakens Fodselsdato er mindre enn 18 år<br/>
så gi feilmeldingen "Barn under 18 år skal ikke ha ettervern"

Alvorlighetsgrad: Warning

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/aftercare/AftercareForMinorsSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/aftercare/AftercareForMinors.kt)




## <a name="flytting">Flytting</a> 

### [TODO] Flytting Kontroll 2c: SluttDato mot sakens SluttDato

Gitt at man har en Flytting der SluttDato finnes og en sak der SluttDato finnes<br/>
når flyttingens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Flyttingens sluttdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/relocation/RelocationEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/relocation/RelocationEndDateAfterCaseEndDate.kt)



### [TODO] Flytting Kontroll 2f: SluttDato mot sakens StartDato

Gitt at man har en Flytting der SluttDato finnes og en sak der StartDato finnes<br/>
når flyttingens SluttDato er etter sakens StartDato<br/>
så gi feilmeldingen "Flyttingens sluttdato {SluttDato} er etter sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/relocation/RelocationEndDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/relocation/RelocationEndDateBeforeCaseStartDateSpec.kt)



## <a name="oversendelse-til-fylkesnemnd">Oversendelse til fylkesnemnd</a> 

### [TODO] Oversendelse til fylkesnemnd Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Oversendelse til fylkesnemnd der StartDato finnes og en sak der StartDato finnes<br/>
når Oversendelse til fylkesnemndens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Oversendelse til fylkesnemndens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/caseforwarding/CaseForwardingStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/caseforwarding/CaseForwardingStartDateBeforeCaseStartDate.kt)



### [TODO] Oversendelse til fylkesnemnd Kontroll 2f: StartDato er etter sakens SluttDato

Gitt at man har en Oversendelse til fylkesnemnd der StartDato finnes og en sak der SluttDato finnes<br/>
når Oversendelse til fylkesnemndens StartDato er etter sakens SluttDato <br/>
så gi feilmeldingen "Oversendelse til fylkesnemndens startdato {StartDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/caseforwarding/CaseForwardingEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/caseforwarding/CaseForwardingStartDateAfterCaseEndDate.kt)


