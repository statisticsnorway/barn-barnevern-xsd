# Kravspesifikasjon for validering

## Innhold
- [Filbeskrivelse](#Filbeskrivelse)
- [Sak](#Sak)
- [Virksomhet](#Virksomhet)
- [Melding](#Melding)
- [Undersøkelse](#Undersokelse)
- [Vedtak](#Vedtak)
- [Tiltak](#Tiltak)
- [Plan](#Plan)
- [Ettervern](#Ettervern)
- [Flytting](#Flytting)
- [Oversendelse til fylkesnemnd](#Oversendelse_til_fylkesnemnd)




<a name="Filbeskrivelse"></a>
## Filbeskrivelse

#### [Validéring av innhold mot filbeskrivelse](src/test/groovy/no/ssb/barn/validation/rule/XsdRuleSpec.groovy)

Gitt at man har en fil med innhold<br/>
når man validérer filen mot filbeskrivelsen<br/>
så skal man ved feil gi feilmeldingen "Innholdet er feil i forhold til filbeskrivelsen / XSD"

Alvorlighetsgrad: FATAL

<a name="Sak"></a>
## Sak

#### [Fødselsnummer og DUFnummer](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityIdAndDuf.groovy)

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Feil i fødselsnummer. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når DUF-nummer mangler <br/>
så gi feilmeldingen "DUFnummer mangler. Kan ikke identifisere klienten."

Gitt at man har en sak<br/>
når fødselsnummer og DUF-nummer mangler <br/>
så gi feilmeldingen "Fødselsnummer og DUFnummer mangler. Kan ikke identifisere klienten."

Alvorlighetsgrad: ERROR

#### [Fødselsnummer](src/test/groovy/no/ssb/barn/validation/rule/CaseSocialSecurityId.groovy)

Gitt at man har en sak<br/>
når fødselsnummer mangler <br/>
så gi feilmeldingen "Klienten har ufullstendig fødselsnummer. Korriger fødselsnummer."

Alvorlighetsgrad: Warning


#### [Klient over 18 år skal ha tiltak](src/test/groovy/no/ssb/barn/validation/rule/CaseAgeAboveEighteen.groovy)

Gitt at man har en sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 18 eller større og tiltak mangler <br/>
så gi feilmeldingen "Klienten er over 18 år og skal dermed ha tiltak"

Alvorlighetsgrad: ERROR

#### [Klient over 25 år og skal avsluttes i barnevernet](src/test/groovy/no/ssb/barn/validation/rule/CaseAgeAboveTwentyFive.groovy)

Gitt at man har en sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 25 eller større<br/>
så gi feilmeldingen "Klienten er over 25 år og skal avsluttes som klient"

Alvorlighetsgrad: ERROR

#### [Klienten skal ha melding, plan eller tiltak](src/test/groovy/no/ssb/barn/validation/rule/CaseHasContentSpec.groovy)

Gitt at man har en sak <br/>
når saken mangler melding, plan og tiltak<br/>
så gi feilmeldingen "Klienten har ingen meldinger, planer eller tiltak"

Alvorlighetsgrad: ERROR

#### [StartDato er etter SluttDato](src/test/groovy/no/ssb/barn/validation/rule/CaseEndDateAfterStartDate.groovy)

Gitt at man har en sak der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Sakens startdato er etter sluttdato"

Alvorlighetsgrad: Warning



<a name="Virksomhet"></a>
## Virksomhet

<a name="Melding"></a>
## Melding

<a name="Undersokelse"></a>
## Undersøkelse

<a name="Vedtak"></a>
## Vedtak

<a name="Tiltak"></a>
## Tiltak

#### [Klienten er 11 år eller eldre og i SFO](src/test/groovy/no/ssb/barn/validation/rule/MeasureAgeAboveElevenAndInSfoSpec.groovy)

Gitt at det er en sak med tiltak og fødselnummer (slik at man kan utlede alder)<br/>
når barnets alder er større enn 11 år og tiltakets kategori er '4.2' SFO/AKS<br/>
så gi feilmelding "Klienten er over 11 år og i SFO"

Alvorlighetsgrad: Warning

<a name="Plan"></a>
## Plan

<a name="Ettervern"></a>
## Ettervern

<a name="Flytting"></a>
## Flytting

<a name="Oversendelse_til_fylkesnemnd"></a>
## Oversendelse til fylkesnemnd
