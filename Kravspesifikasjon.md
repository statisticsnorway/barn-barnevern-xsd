# Kravspesifikasjon for validering

## Innhold
- [Filbeskrivelse](#Filbeskrivelse)
- [Sak](#Sak)
- [Virksomhet](#Virksomhet)
- [Melding](#Melding)
  - [Melder](#Melder)
  - [Saksinnhold](#Saksinnhold)
- [Undersøkelse](#Undersokelse)
- [Vedtak](#Vedtak)
- [Tiltak](#Tiltak)
  - [Lovhjemmel](#TiltakLovhjemmel)
- [Plan](#Plan)
- [Ettervern](#Ettervern)
- [Flytting](#Flytting)
- [Oversendelse til fylkesnemnd](#Oversendelse_til_fylkesnemnd)




<a name="Filbeskrivelse"></a>
## Filbeskrivelse

### Validéring av innhold mot filbeskrivelse

Gitt at man har en fil med innhold som man skal validere mot filbeskrivelsen<br/>
når validering av filen mot filbeskrivelsen feiler<br/>
så gi feilmeldingen "Innholdet er feil i forhold til filbeskrivelsen / XSD"

Alvorlighetsgrad: FATAL

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/XsdRuleSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/XsdRule.kt)

<a name="Sak"></a>
## Sak

### Sak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en sak der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Sakens startdato {StartDato} er etter sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/CaseEndDateAfterStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/CaseEndDateAfterStartDate.kt)



### [Sak Kontroll 3: Fødselsnummer og DUFnummer](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityIdAndDuf.groovy)

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Feil i fødselsnummer. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når fødselsnummer mangler og DUF-nummer mangler <br/>
så gi feilmeldingen "DUFnummer mangler. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når fødselsnummer og DUF-nummer mangler <br/>
så gi feilmeldingen "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere klienten."

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityIdAndDufSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/CaseSocialSecurityIdAndDuf.kt)



### [Sak Kontroll 6: Klienten skal ha melding, plan eller tiltak](src/test/groovy/no/ssb/barn/validation/rule/CaseHasContentSpec.groovy)

Gitt at man har en sak <br/>
når saken mangler melding, plan og tiltak<br/>
så gi feilmeldingen "Klienten har ingen meldinger, planer eller tiltak"

Alvorlighetsgrad: ERROR



### [Sak Kontroll 7: Klient over 25 år og skal avsluttes i barnevernet](src/test/groovy/no/ssb/barn/validation/rule/CaseAgeAboveTwentyFive.groovy)

Gitt at man har en sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 25 eller større<br/>
så gi feilmeldingen "Klienten er over 25 år og skal avsluttes som klient"

Alvorlighetsgrad: ERROR




### [Sak Kontroll 8: Klient over 18 år skal ha tiltak](src/test/groovy/no/ssb/barn/validation/rule/CaseAgeAboveEighteen.groovy)

Gitt at man har en sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 18 eller større og tiltak mangler <br/>
så gi feilmeldingen "Klienten er over 18 år og skal dermed ha tiltak"

Alvorlighetsgrad: ERROR



### [Sak Kontroll 11: Fødselsnummer](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityId.groovy)

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Klienten har ufullstendig fødselsnummer. Korriger fødselsnummer."

Alvorlighetsgrad: Warning





<a name="Virksomhet"></a>
## Virksomhet

### Virksomhet Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en virksomhet der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Virksomhetens startdato {StartDato} er etter sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/BusinessEndDateAfterStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/BusinessEndDateAfterStartDate.kt)



### O Virksomhet Kontroll 2c: SluttDato mot sakens SluttDato

Gitt at man har en sak der SluttDato finnes og virksomhet der SluttDato finnes<br/>
når virksomhetens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Virksomhetens startdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/BusinessEndDateAfterCaseEndDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/BusinessEndDateAfterCaseEndDate.kt)



### X Virksomhet Kontroll 2e: StartDato mot sakens StartDato

Gitt at man har en sak der StartDato finnes og virksomhet der StartDato finnes<br/>
når virksomhetens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Virksomhetens startdato {StartDato } er før sakens startdato {StartDato }"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/BusinessStartDateBeforeCaseStartDateSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/BusinessStartDateBeforeCaseStartDate.kt)



### Virksomhet Kontroll 3: Bydelsnummer og bydelsnavn

Gitt at man har en virksomhet der Organisasjonsnummer er en av 958935420 (Oslo), 964338531 (Bergen), 942110464 (Trondheim) eller 964965226 (Stavanger)<br/>
når Bydelsnummer eller Bydelsnavn mangler utfylling<br/>
så gi feilmeldingen "Virksomhetens Bydelsnummer og Bydelsnavn skal være utfylt"

Alvorlighetsgrad: ERROR

[Akseptanse kriterie](src/test/groovy/no/ssb/barn/validation/rule/BusinessUrbanDistrictNumberAndNameSpec.groovy)

[Kildekode](src/main/kotlin/no/ssb/barn/validation/rule/BusinessUrbanDistrictNumberAndName.kt)



<a name="Melding"></a>

## Melding

### [Melding Kontroll 2a: StartDato er etter SluttDato](src/test/groovy/no/ssb/barn/validation/rule/MessageEndDateAfterStartDate.groovy)

Gitt at man har en melding der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR



### X Melding Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Melding Kontroll 2e: StartDato mot virksomhetens StartDato

### X Melding Kontroll 3: Behandlingstid av melding

### X Melding Kontroll 4: Konkludert melding inneholder melder

### X Melding Kontroll 5: Konkludert melding inneholder saksinnhold

<a name="Melder"></a>

### Melder

#### X Melder Kontroll 2: Kode med Presisering

<a name="Saksinnhold"></a>

### Saksinnhold

#### X Saksinnhold Kontroll 2: Kode med Presisering





[Individ Kontroll 11: Fødselsnummer](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityId.groovy)

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Klienten har ufullstendig fødselsnummer. Korriger fødselsnummer."

Alvorlighetsgrad: Warning





<a name="Undersokelse"></a>
## Undersøkelse

### X Undersøkelse Kontroll 2a: StartDato er etter SluttDato

### X Undersøkelse Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Undersøkelse Kontroll 2e: StartDato mot virksomhetens StartDato

### X Undersøkelse Kontroll 3: Kode med Presisering

### X Undersøkelse Kontroll 4: Undersøkelse med SluttDato skal være konkludert

### X Undersøkelse Kontroll 7: Konkludert undersøkelse skal ha vedtaksgrunnlag

### X Undersøkelse Kontroll 10: Undersøkelse skal ha relasjon til melding





<a name="Vedtak"></a>
## Vedtak

### X Vedtak Kontroll 2a: StartDato er etter SluttDato

### X Vedtak Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Vedtak Kontroll 2e: StartDato mot virksomhetens StartDato

<a name="Tiltak"></a>

## Tiltak

### X Tiltak Kontroll 2a: StartDato er etter SluttDato

### X Tiltak Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Tiltak Kontroll 2e: StartDato mot virksomhetens StartDato

### X Tiltak Kontroll  4: Omsorgstiltak med sluttdato krever årsak til opphevelse

### X Tiltak Kontroll 5: Kontroll om barnet er over 7 år og er i barnehage

### [Tiltak Kontroll 6: Klienten er 11 år eller eldre og i SFO](src/test/groovy/no/ssb/barn/validation/rule/MeasureAgeAboveElevenAndInSfoSpec.groovy)

Gitt at det er en sak med tiltak og fødselnummer (slik at man kan utlede alder)<br/>
når barnets alder er større enn 11 år og tiltakets kategori er '4.2' SFO/AKS<br/>
så gi feilmelding "Klienten er over 11 år og i SFO"

Alvorlighetsgrad: Warning

### X Tiltak Kontroll 7: Kontroll om presisering av tiltakskategori



### X Tiltak Kontroll  8: Kontroll av kode og presisering av opphevelse



### X Tiltak Kontroll 9: Kontroll om flere plasseringstiltak er oppgitt i samme tidsperiode

<a name="TiltakLovhjemmel"></a>

### Lovhjemmel

#### X Tiltak Kontroll 12: Kontroll av omsorgstiltak med sluttdato, krever årsak til opphevelse

#### X Tiltak Kontroll 13: Kontroll om individ er over 18 år og har omsorgtiltak

#### X Tiltak Kontroll 14: Kontroll om lovhjemmel er fylt ut med tallet 0



<a name="Plan"></a>

## Plan

### X Plan Kontroll 2a: StartDato er etter SluttDato

### X Plan Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Plan Kontroll 2e: StartDato mot virksomhetens StartDato

<a name="Ettervern"></a>
## Ettervern

### X Ettervern Kontroll 2a: TilbudSendtDato er etter SluttDato

### X Ettervern Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Ettervern Kontroll 2e: StartDato mot virksomhetens StartDato

<a name="Flytting"></a>
## Flytting

### X Plan Kontroll 2c: SluttDato mot virksomhetens SluttDato

### X Plan Kontroll 2f: SluttDato mot virksomhetens StartDato

<a name="Oversendelse_til_fylkesnemnd"></a>

## Oversendelse til fylkesnemnd

### X Plan Kontroll 2c: StartDato mot virksomhetens SluttDato

### X Plan Kontroll 2e: StartDato mot virksomhetens StartDato
