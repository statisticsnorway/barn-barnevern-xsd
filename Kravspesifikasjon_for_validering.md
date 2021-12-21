# Kravspesifikasjon for validering

## Innhold

[TOC]



## Omfang

Gjelder fortløpende innrapportering barnevernsdata direkte fra fagsystem. 

Kontaktperson: Tone Dyrhaug, tlf. 21 09 47 71, e-post: [tone.dyrhaug@ssb.no](mailto:tone.dyrhaug@ssb.no)

Barnevernsstatistikken er en individstatistikk som sendes inn fra alle kommunale barnevernstjenester som et filuttrekk fra kommunens fagsystem for barnevern. Oppbyggingen er slik:

1. Opplysninger om fagsystemet
2. Opplysninger om kommunen
3. Opplysninger om saken
   1. Opplysninger om virksomheten, som står for saksbehandlingen
      1. Meldinger
      2. Undersøkelser
      3. Plantyper
      4. Tiltak etter lov om barneverntjenester
      5. Vedtak
      6. Ettervern
      7. Krav
      8. Flyttinger
      9. Er barnet klient ved utgangen av rapporteringsåret?



## Advarsler og feil

Valideringstjenesten lager en liste med advarsler som ikke hindrer innsending, samt feil og kritiske feil som hindrer innsending. Advarsler skal kommunens barnevernstjeneste sjekke og om mulig rette opp. Feil som hindrer innsending må rettes for å få rapportert. Kritiske feil hindrer andre valideringer i å bli utført så innsending må rettes for å få rapportert. I valideringsrapportene som er resultatet fra valideringen vil identifikasjon av hvilket barn valideringen slår ut være journalnummer. Journalnummer kan derfor ikke være noe som kan identifisere individet, for eksempel fødsels- og personnummer/D-nummer eller DUF-nummer. 



## Generelle dato-valideringer

#### Datovalideringer: StartDato, SluttDato og Avslutta3112

Begrepet SluttDato går igjen på flere nivåer i xml’en. I stedet for å definere de samme valideringene i hvert av elementene Individ, Melding, Undersokelse, Vedtaksgrunnlag, Plan og Tiltak defineres de nå generelt der det kan finnes en SluttDato. Hvis SluttDato trenger en spesiell validering på ett eller flere av de overnevnte elementene, beskrives dette eksplisitt. Nummereringen til datovalideringene gjenspeiles på de begrepene.



#### 2. Kontroll av rekkefølge på datoer

Gjelder for Sak, Virksomhet, Melding, Undersokelse, Plan, Tiltak og Vedtak.

Gitt at:

| Begrep         | ForsteDato              | SisteDato                          | Alvorlighetsgrad | ReferanseId      | Tittel                                               | Melding                                                      |
| -------------- | ----------------------- | ---------------------------------- | ---------------- | ---------------- | ---------------------------------------------------- | ------------------------------------------------------------ |
| //Sak          | Sak/@Startdato          | Sak/@Sluttdato                     | Feil             | Sak/@Id          | Sak Validering 2a: Startdato før sluttdato.          | Sakens startdato (*ForsteDato*) er ikke før sluttdato (*SisteDato*). |
| //Virksomhet   | Virksomhet/@Startdato   | Virksomhet/@Sluttdato              | Feil             | Virksomhet/@Id   | Virksomhet Validering 2a: Startdato før sluttdato.   | Virksomhetens startdato  (*ForsteDato*) er ikke før sluttdato (*SisteDato*). |
| //Melding      | Melding/@Startdato      | Melding/Konklusjon/@Sluttdato      | Feil             | Melding/@Id      | Melding Validering 2a: Startdato før sluttdato.      | Meldingens startdato  (*ForsteDato*) er ikke før sluttdato (*SisteDato*). |
| //Undersøkelse | Undersokelse/@Startdato | Undersokelse/Konklusjon/@Sluttdato | Feil             | Undersokelse/@Id | Undersøkelse Validering 2a: Startdato før sluttdato. | Undersøkelsens startdato  (*ForsteDato*) er ikke før sluttdato (*SisteDato*) |
| //Plan         | Plan/@Startdato         | Plan/Konklusjon/@Sluttdato         | Feil             | Plan/@Id         | Plan Validering 2a: Startdato før sluttdato.         | Planens startdato  (*ForsteDato*) er ikke før sluttdato (*SisteDato*) |
| //Tiltak       | Tiltak/@Startdato       | Tiltak/Konklusjon/@Sluttdato       | Feil             | Tiltak/@Id       | Tiltak Validering 2a: Startdato før sluttdato.       | Tiltakets startdato  (*ForsteDato*) er ikke før sluttdato (*SisteDato*) |



Når:

Hvis SisteDato finnes, validér at ForsteDato er før SisteDato

Så:

Ved feil, legg til begrep, alvorlighetsgrad. referanseid, tittel og melding i valideringsrapport



























