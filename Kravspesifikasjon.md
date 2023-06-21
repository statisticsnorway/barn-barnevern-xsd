# Kravspesifikasjon for validering

## Innhold
- [Oversettelser / Translations](#oversettelser_translations)
- [Migrering av data innrapportert via Kostra](#migrering)
- [Definisjoner](#definisjoner)
- [Validéringer](#valideringer)
  - [Filbeskrivelse](#filbeskrivelse)
  - [Avgiver](#avgiver)
  - [Sak](#sak)
  - [Personalia](#personalia)
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
- [Endringslogg](#endringslogg)


## <a name="oversettelser_translations">Oversettelser / Translations</a>

| Norsk                        | English                             |
|------------------------------|-------------------------------------|
| Barnevernloven               | Child Care Act                      |
| Endringslogg                 | Changelog                           |
| Ettervern                    | Aftercare                           |
| Filbeskrivelse               | File description                    |
| Flytting                     | Relocation                          |
| Melder                       | Reporter                            |
| Melding (bekymringsmelding)  | Message (message of worry)          |
| Oversendelse til fylkesnemnd | Case forwarding to the county board |
| Omsorgstiltak                | Care measure                        |
| Personalia                   | Personalia                          |
| Plan (tiltaksplan)           | Plan (plan of measures)             |
| Relasjon                     | Relation                            |
| Sak                          | Case                                |
| Saksinnhold                  | Case content                        |
| Tiltak                       | Measure                             |
| Undersøkelse                 | Investigation                       |
| Vedtak                       | Desicion                            |


## <a name="migrering">Migrering av data innrapportert via Kostra</a>
I forbindelse med at data stammer i fra rapportering via **[KOSTRA](#kostra_kanal)** og inneholder MigrertId så vil alle valideringsregler sette alvorlighetsgrad til WARNING, ikke ERROR slik det står i spesifikasjonen. 


## <a name="definisjoner">Definisjoner</a>

### <a name="barnevernloven">Barnevernloven</a>
Barnevernloven brukes i to forskjellige versjoner:
- Versjonen fra 1992 refereres som **<a name="bvl">BVL</a>** av historiske årsaker, eventuelt **<a name="bvl1992">BVL1992</a>** i dokumentasjonssammenheng, og gjelder til 31. desember 2022.<br/>
- Versjonen fra 2021 refereres som **<a name="bvl2021">BVL2021</a>** og gjelder fra 1. januar 2023.


### <a name="innrapporteringskanaler">Innrapporteringskanaler</a>
Rapportering til SSB skjer i 2 forskjellige kanaler:
- **<a name="fagsystem_kanal">FAGSYSTEM</a>**, direkte rapportering fra fagsystem via Fiks til SSB.
- **<a name="kostra_kanal">KOSTRA</a>**, klassisk og indirekte rapportering ved hjelp av filuttrekk.


### <a name="omsorgsovertakelse">Omsorgsovertakelse</a>
Et Tiltak er en Omsorgsovertakelse dersom en av følgende:
- Lovhjemmel/Lov = **[BVL](#bvl)** og Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 12
- Lovhjemmel/Lov = **[BVL](#bvl)** og Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 8 og Lovhjemmel/Ledd er én av 2 eller 3
- Lovhjemmel/Lov = **[BVL](#bvl)** og Lovhjemmel/Kapittel = 4 og Lovhjemmel/Paragraf = 8 og én av JmfrLovhjemmel/Kapittel = 4 og JmfrLovhjemmel/Paragraf = 12
- Lovhjemmel/Lov = **[BVL2021](#bvl2021)** og Lovhjemmel/Kapittel = 5 og Lovhjemmel/Paragraf = 1


### <a name="plasseringstiltak">Plasseringstiltak</a>
Et Tiltak er et plasseringstiltak dersom Kategori/@Kode er en av følgende koder:<br/>
1.1, 1.2, 1.99, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.99 eller 8.2<br/>


### <a name="relasjon">Relasjon</a>
En Relasjon beskriver en kobling mellom to begrep. For eksempel fra Vedtak til Tiltak vil Relasjon inneholder vedtaket sin Id i sin FraId, "Vedtak" i sin FraType, tiltakets Id i sin TilId og "Tiltak" i sin TilType


# <a name="valideringer">Valideringer</a>

## <a name="filbeskrivelse">Filbeskrivelse</a>

### Validéring av innhold mot filbeskrivelse

Gitt at man har en fil med innhold som man skal validere mot filbeskrivelsen<br/>
når validering av filen mot filbeskrivelsen feiler<br/>
så gi feilmeldingen "Innholdet er feil i forhold til filbeskrivelsen / XSD"

Alvorlighetsgrad: FATAL<br/>
Gyldig fra 2013-01-01



## <a name="avgiver">Avgiver</a>

### Avgiver Kontroll 1: Bydelsnummer og bydelsnavn

Gitt at man har en Avgiver der Organisasjonsnummer er en av 958935420 (Oslo), 964338531 (Bergen) eller 942110464 (Trondheim)<br/>
når Bydelsnummer eller Bydelsnavn mangler utfylling<br/>
så gi feilmeldingen "Bydelsnummer og/eller Bydelsnavn skal være utfylt"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



## <a name="sak">Sak</a>

### Sak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Sak der StartDato og SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Sakens startdato {StartDato} er etter sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Sak Kontroll 6: Klienten skal ha melding, plan eller tiltak

Gitt at man har en Sak <br/>
når saken mangler melding, plan og tiltak<br/>
så gi feilmeldingen "Klienten har ingen meldinger, planer eller tiltak"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Sak Kontroll 7: Klient over 25 år og skal avsluttes i barnevernet

Gitt at man har en Sak med datoUttrekk og fødselsdato<br/>
når datoUttrekk minus fødselsdato er lik 25 år eller større<br/>
så gi feilmeldingen "Klienten er over 25 år og skal avsluttes som klient. Alder: {alder} år."

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Sak Kontroll 8: Klient over 18 år skal ha tiltak

Gitt at man har en Sak og utleder alder ved hjelp av fødselsnummer<br/>
når alder er 18 eller større og tiltak mangler <br/>
så gi feilmeldingen "Klienten er over 18 år og skal dermed ha tiltak. Alder: {alder} år."

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



## <a name="personalia">Personalia</a>

### Personalia Kontroll 2f: StartDato er etter sakens SluttDato

Når Personalia sin StartDato er etter sakens SluttDato <br/>
så gi feilmeldingen "Personalia sin startdato {StartDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### ### Personalia kontroll 11: Klienten skal ha personalia

Gitt at man har en Sak <br/>
når saken mangler Personalia<br/>
så gi feilmeldingen "Klienten mangler personaliainformasjon"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia kontroll 12: Fødselsnummer

Gitt at man har Personalia med fødselsnummer<br/>
når fødselsnummer ikke oppfyller noen følgende definisjoner:<br/>
* Fødselsnummer på [https://www.udi.no/ord-og-begreper/fodselsnummer/](https://www.udi.no/ord-og-begreper/fodselsnummer/),
* D-nummer på [https://www.udi.no/ord-og-begreper/d-nummer/](https://www.udi.no/ord-og-begreper/d-nummer/),
* fødselsdato (DDMMÅÅ) + 00100,
* fødselsdato (DDMMÅÅ) + 00200,
* termindato (DDMMÅÅ) + 99999

så gi feilmeldingen "Ugyldig Fødselsnummer"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia Kontroll 13: Kontroll av Fødselsnummer og Fødselsdato

Gitt at man har Personalia med fødselsnummer og fødselsdato<br/>
når datodelen fødselsnummer (de 6 første karakterene) viser til en annen dato enn fødselsdato<br/>
så gi feilmeldingen "Fødselsnummer og Fødselsdato viser til forskjellige datoer"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia Kontroll 14: Kontroll av Fødselsnummer og Kjønn

Gitt at man har Personalia med fødselsnummer og kjønn<br/>
når de 5 siste karakterene i Personlia sitt fødselsnummer er ulik 99999 og den 9. karakteren i fødselsnummer modulus 2 er lik 0 er forskjellig fra koden for kjønn<br/>
så gi feilmeldingen "Fødselsnummer og Kjønn viser til forskjellige kjønn"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia Kontroll 15: Kontroll av Fødselsnummer, fødselsdato og StartDato

Gitt at man har Personalia med fødselsnummer, startdato og datouttrekk<br/>
når startdato er etter fødselsdato og de 5 siste karakterene i fødselsnummer er lik 99999<br/>
så gi feilmeldingen "Feil i Fødselsnummer for født barn"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia Kontroll 16: Kontroll av Fødselsdato og StartDato

Gitt at man har Personalia med fødselsdato og startdato<br/>
når startdato er mer enn 9 måneder før fødselsdato<br/>
så gi feilmeldingen "Personalia sin startdato {StartDato} er mer enn 9 måneder før barnets fødselsdato {Fødselsdato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Personalia Kontroll 17: Kontroll av DUF-nummer

Gitt at man har Personalia med utfylt DUF-nummer<br/>
når DUF-nummer ikke oppfyller noen følgende definisjoner:<br/>
* DUF-nummer [https://www.udi.no/ord-og-begreper/duf-nummer/](https://www.udi.no/ord-og-begreper/duf-nummer/)

så gi feilmeldingen "Ugyldig DUF-nummer"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01





## <a name="melding">Melding</a>

### Melding Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Melding der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Melding Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Melding der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når meldingens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Meldingens sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Melding Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Melding med StartDato og i sak med StartDato<br/>
når meldingens StartDato er før sakens StartDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Melding Kontroll 3: Fristoverskridelse på behandlingstid

Gitt at man har en Melding der StartDato og Konklusjon/SluttDato finnes<br/>
når Konklusjon/SluttDato er mer enn 7 dager etter StartDato<br/>
så gi feilmeldingen "Fristoverskridelse på behandlingstid for melding, ({StartDato} -> {Konklusjon/SluttDato})"

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Melding Kontroll 4: Konkludert melding mangler melder

Gitt at man har en Melding<br/>
når Konklusjon finnes og 1 eller flere Melder(e) mangler<br/>
så gi feilmeldingen "Konkludert melding mangler melder(e)"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Melding Kontroll 5: Konkludert melding mangler saksinnhold

Gitt at man har en Melding<br/>
når Konklusjon finnes og 1 eller flere Saksinnhold mangler<br/>
så gi feilmeldingen "Konkludert melding mangler saksinnhold"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01


### Melding Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Meldinger med konklusjon<br/>
når en sammenligner meldingene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere meldinger med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### <a name="melder">Melder</a>

#### Melder Kontroll 2: Mangler Presisering

Gitt at man har en Melder der Kode er 22 (= Andre offentlige instanser)<br/>
når Melder mangler Presisering<br/>
så gi feilmeldingen "Melder med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### <a name="saksinnhold">Saksinnhold</a>

#### Saksinnhold Kontroll 2: Mangler Presisering

Gitt at man har et Saksinnhold der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Saksinnhold mangler Presisering<br/>
så gi feilmeldingen "Saksinnhold med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



## <a name="undersokelse">Undersøkelse</a>

### Undersøkelse Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Undersøkelse der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Undersøkelse der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når undersøkelsens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Undersøkelsens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Undersøkelse der StartDato finnes og sak der StartDato finnes<br/>
når undersøkelsens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Undersøkelsens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 3: Vedtaksgrunnlag mangler presisering

Gitt at man har et Vedtaksgrunnlag der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Vedtaksgrunnlag mangler Presisering<br/>
så gi feilmeldingen "Vedtaksgrunnlag med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 7: Konkludert undersøkelse mangler vedtaksgrunnlag

Gitt at man har en Undersøkelse der Konklusjon finnes og Konklusjon sin Kode er 1 eller 2<br/>
når Vedtaksgrunnlag mangler<br/>
så gi feilmeldingen "Undersøkelse konkludert med kode {Konklusjon/Kode} mangler vedtaksgrunnlag"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



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

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Undersøkelser<br/>
når en sammenligner Undersøkelsene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere Undersøkelser med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Undersøkelse Kontroll 20: Undersøkelse skal ha relasjon fra melding

Gitt at man har en Undersøkelse, en Relasjon og en Melding<br/>
når en relasjon som inneholder melding/Id i sin FraId, "Melding" i sin FraType, undersøkelse/Id i sin TilId og "Undersokelse" i sin TilType mangler<br/>
så gi feilmeldingen "Undersøkelse mangler en relasjon fra melding"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01


### Undersøkelse Kontroll 21: Konkludert Undersøkelse skal ha relasjon til Vedtak

Gitt at man har en Undersøkelse, en Relasjon og et Vedtak<br/>
når Undersøkelse sin Konklusjon finnes og Konklusjon sin Kode er 1 eller 2 <br/>
og en relasjon fra undersøkelsen til vedtaket mangler, der relasjonen skal inneholde undersøkelse/Id i sin FraId, "Undersokelse" i sin FraType, vedtak/Id i sin TilId og "Vedtak" i sin TilType<br/>
så gi feilmeldingen "Konkludert Undersøkelse mangler en relasjon til vedtak"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



## <a name="vedtak">Vedtak</a>

### Vedtak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Vedtak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Vedtak der Konklusjon/SluttDato finnes og i en Sak der SluttDato finnes<br/>
når vedtakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Vedtakets sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Vedtak der StartDato finnes og en Sak der StartDato finnes<br/>
når vedtakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2f: Krav sin StartDato er etter SluttDato

Gitt at man har et Vedtak der Krav finnes StartDato og Konklusjon/SluttDato finnes<br/>
når for hvert krav validér at kravets StartDato er etter SluttDato <br/>
så gi feilmeldingen "Kravets startdato {StartDato} er etter sluttdato {Krav/Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2g: Krav sin SluttDato er etter vedtakets SluttDato

Gitt at man har et Vedtak der SluttDato finnes og Vedtaket har Krav der Krav sin Konklusjon/SluttDato finnes<br/>
når kravets SluttDato er etter vedtakets SluttDato<br/>
så gi feilmeldingen "Kravets sluttdato {Krav/Konklusjon/SluttDato} er etter Vedtakets sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2h: Krav sin StartDato er før vedtakets StartDato

Gitt at man har et Vedtak der StartDato finnes og Vedtaket har Krav der StartDato finnes<br/>
når kravets StartDato er før vedtakets StartDato <br/>
så gi feilmeldingen "Kravets startdato {Krav/StartDato} er før vedtakets startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2i: Status sin EndretDato er etter vedtakets SluttDato

Gitt at man har et Vedtak der SluttDato finnes og Vedtaket har Status der Status sin EndretDato finnes<br/>
når statusens EndretDato er etter vedtakets SluttDato<br/>
så gi feilmeldingen "Status sin endretdato {Status/EndretDato} er etter Vedtakets sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: INFO<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 2j: Status sin EndretDato er før vedtakets StartDato

Gitt at man har et Vedtak der StartDato finnes og Vedtaket har Status der Status sin EndretDato finnes<br/>
når statusens EndretDato er før vedtakets StartDato <br/>
så gi feilmeldingen "Status sin endretdato {Status/EndretDato} er før vedtakets startdato {StartDato}"

Alvorlighetsgrad: INFO<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 3: Avsluttet vedtak skal ha SluttDato

Gitt at man har et Vedtak der Status = 3 eller 4<br/>
når vedtakets status = 3 og Konklusjon/SluttDato mangler<br/>
så gi feilmeldingen "Vedtak med status 3 = 'Utgår / Bortfalt etter BVL' mangler sluttdato"<br/>

når vedtakets status = 4 og Konklusjon/SluttDato mangler<br/>
så gi feilmeldingen "Vedtak med status 4 = 'Avslått / Avsluttet' mangler sluttdato"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 4: Lovhjemmel refererer til feil barnevernlov

Gitt at man har et Vedtak der Vedtak/LovhjemmelType/Lov og eventuelt Vedtak/JmfrLovhjemmelType/Lov er utfylt<br/>
når Lov er utfylt med noe annet enn BVL eller BVL2021<br/>
så gir feilmeldingen "Lovhjemlene under Vedtak innholder annen verdi for Lov enn BVL eller BVL2021. Lov: {lov}."

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak kontroll 5: Lovhjemmel refererer til feil barnevernlov

Gitt att man har et Vedtak

når Vedtak sin StartDato er før 01. januar 2023 og Vedtak/Lovhjemmel/Lov ikke er BVL<br/>
så gi feilmeldingen "Lovhjemmel opprettet før 01. januar 2023 krever lov = '**[BVL](#barnevernloven)**'"

når Vedtak sin StartDato er 01. januar 2023 eller senere og Vedtak/Lovhjemmel/Lov ikke er BVL2021<br/>
så gi feilmeldingen "Lovhjemmel opprettet på 01. januar 2023 eller senere krever lov = '**[BVL2021](#barnevernloven)**'"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Vedtak Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Vedtak<br/>
når en sammenligner Vedtakene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere Vedtak med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: INFO<br/>
Gyldig fra 2013-01-01



### Vedtak Kontroll 20: Vedtak med status 2 = 'Begjæring_ oversendt nemnd' skal ha relasjon til OversendelseFylkesnemnd

Gitt at man har et Vedtak der Status = 2<br/>
når Relasjon mellom Vedtak og OversendelseFylkesnemnd mangler <br/>
så gi feilmeldingen "Vedtak med status 2 = 'Begjæring oversendt nemnd' skal ha relasjon til OversendelseFylkesnemnd"

når Relasjon mellom Vedtak og OversendelseFylkesnemnd finnes, men OversendelseFylkesnemnd mangler <br/>
så gi feilmeldingen "OversendelseFylkesnemndVedtak skal finnes for Vedtak med status 2 = 'Begjæring oversendt nemnd'"

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2022-01-01



## <a name="tiltak">Tiltak</a>

### Tiltak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Tiltak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Tiltak der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når tiltakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Tiltakets sluttdato {Konklusjon/SluttDato} er etter Sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Tiltak der StartDato finnes og sak der StartDato finnes<br/>
når tiltakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 5: Barnet er over 7 år og er i barnehage

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 7 år og tiltakets kategori er '4.1' Barnehage<br/>
så gi feilmelding "Barnet er over 7 år og i barnehage. Alder: {alder} år."

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 6: Barnet er over 11 år og i SFO

Gitt at det er en sak med tiltak og fødseldato (slik at man kan utlede alder)<br/>
når barnets alder er større enn 11 år og tiltakets kategori er '4.2' SFO/AKS<br/>
så gi feilmelding "Barnet er over 11 år og i SFO. Alder: {alder} år."

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 7: Kontroll om presisering av tiltakskategori

Gitt at man har et Tiltak der Kategori/Kode er en følgende koder:
1.99, 2.99, 3.7, 3.99, 4.99, 5.99, 6.99, 7.99 eller 8.99<br/>
når presisering mangler<br/>
så gi feilmelding "Tiltakskategori (kode) mangler presisering."

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 8: Kontroll av kode og presisering av opphevelse

Gitt at man har et Tiltak der Opphevelse/Kode er 4<br/>
når presisering mangler<br/>
så gi feilmelding "Opphevelse (kode) mangler presisering."

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 9: Flere plasseringstiltak er oppgitt i samme tidsperiode

Gitt at man har 2 eller flere [Plasseringstiltak](#plasseringstiltak)<br/>
og for de plasseringstiltakene der SluttDato mangler så brukes DatoUttrekk i stedet<br/>
når plasseringstiltak1 overlapper plasseringstiltak2 med mer enn 90 dager<br/>
så gi feilmelding "Flere plasseringstiltak i samme periode (PeriodeStartDato - PeriodeSluttDato). Plasseringstiltak kan ikke overlappe med mer enn 90 dager. Antall dager overlapp: `antallDager` dager. Overlapper med tiltak-id `tiltak-id`.

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 12: Omsorgstiltak med sluttdato krever årsak til opphevelse

Gitt at man har et [Omsorgstiltak](#omsorgstiltak) som også er et [Plasseringstiltak](#plasseringstiltak) der Konklusjon/SluttDato er satt<br/>
når Opphevelse/Kode mangler
så gi feilmelding "Omsorgstiltak med sluttdato krever årsak til opphevelse"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 13: Individ er over 18 år og har omsorgtiltak

Gitt at man har UttrekkDato, en Sak med fødselsdato og et [Omsorgstiltak](#omsorgstiltak)<br/>
når UttrekkDato er 18 år eller mer etter fødselsdato og sluttdato mangler<br/>
så gi feilmelding "Individet er over 18 år skal dermed ikke ha omsorgstiltak. Alder: {alder} år."

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 14: Lovhjemmel er fylt ut med tallet 0

Gitt at det er en sak med tiltak <br/>
når Lovhjemmel sin kapittel, paragraf, ledd eller punktum er utfylt med 0
så gi feilmelding "Ingen Lovhjemmel sin kapittel, paragraf, ledd eller punktum kan være utfylt med 0"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Tiltak Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at man har 2 eller flere Tiltak<br/>
når en sammenligner Tiltakene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere tiltak med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2022-01-01



### Tiltak Kontroll 20: Tiltak skal ha relasjon fra Vedtak

Gitt at det er et Tiltak, en Relasjon og et Vedtak <br/>
når det mangler en relasjon som inneholder vedtak/Id i sin FraId, "Vedtak" i sin FraType, tiltak/Id i sin TilId og "Tiltak" i sin TilType<br/>
så gi feilmelding "Tiltak skal ha relasjon fra Vedtak"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Tiltak Kontroll 21: Tiltak sin Lovhjemmel skal baseres på det relaterte Vedtak sin Lovhjemmel

Gitt at det er et Tiltak, en Relasjon og et Vedtak<br/>
når en [Relasjon](#relasjon) fra Vedtak til Tiltak finnes,<br/>
men Vedtak/Lovhjemmel/Lov er ulik Tiltak/Lovhjemmel/Lov<br/>
eller Vedtak/Lovhjemmel/Kapittel er ulik Tiltak/Lovhjemmel/Kapittel<br/>
eller Vedtak/Lovhjemmel/Paragraf er ulik Tiltak/Lovhjemmel/Paragraf<br/>
så gi feilmelding "Tiltak er registrert med annen lovhjemmel enn det som er registrert i vedtak"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Tiltak Kontroll 22: Tiltak med kode 1.99 Andre tiltak

Gitt at man har et Tiltak<br/>
når Kategori/Kode = 1.99<br/>
så gi advarsel "Dette er er en advarsel. Kun plasseringstiltak skal ha kode 1.99. <br/>
Dersom dette ikke er et plasseringstiltak (institusjon) må tiltakskode endres. <br/>
Er det et administrative vedtak som er gitt kode 1.99 skal tiltaket slettes."

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2023-01-01



### Tiltak Kontroll 23: Tiltak med kode 2.99 Andre tiltak

Gitt at man har et Tiltak<br/>
når Kategori/Kode = 2.99<br/>
så gi advarsel "Dette er er en advarsel. Kun plasseringstiltak skal ha kode 2.99. <br/>
Dersom dette ikke er et plasseringstiltak (fosterhjem) må tiltakskode endres. <br/>
Er det et administrative vedtak som er gitt kode 2.99 skal tiltaket slettes."

Alvorlighetsgrad: WARNING<br/>
Gyldig fra 2023-01-01



## <a name="plan">Plan</a>

### Plan Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en Plan der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Planens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01




### Plan Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Plan der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når planens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Planens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01




### Plan Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Plan der StartDato finnes og sak der StartDato finnes<br/>
når planens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Planens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2013-01-01




### Plan Kontroll 2f: UtfortDato er etter sakens SluttDato

Gitt at man har en Plan der Plan/Evaluering/UtfortDato finnes og Plan/Konklusjon/SluttDato finnes<br/>
når UtfortDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Utført evaluering {Plan/Evaluering/UtfortDato} er etter sakens sluttdato {Sak/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Plan Kontroll 2g: UtfortDato er før sakens StartDato

Gitt at man har en Plan der Plan/Evaluering/UtfortDato finnes<br/>
når UtfortDato er før sakens StartDato<br/>
så gi feilmeldingen "Utført evaluering {Plan/Evaluering/UtfortDato} er før sakens startdato {Sak/StartDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2013-01-01



### Plan Kontroll 19: Dublett på innhold, men forskjellige identer

Gitt at en har 2 eller flere Planer<br/>
når en sammenligner Planene med hverandre og finner helt identisk innhold, men forskjellig id<br/>
så gi feilmeldingen "Det finnes 2 eller flere Planer med identisk innhold, men med forskjellige identer"

Alvorlighetsgrad: INFO<br/>
Gyldig fra 2013-01-01


## <a name="ettervern">Ettervern</a>

### Ettervern Kontroll 2a: TilbudSendtDato er etter SluttDato

Gitt at man har et Ettervern der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Ettervernets tilbudSendtDato {TilbudSendtDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Ettervern Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har et Ettervern der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Ettervern sin sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Ettervern Kontroll 2e: TilbudSendtDato er før sakens StartDato

Gitt at man har et Ettervern der TilbudSendtDato finnes og sak der StartDato finnes<br/>
når ettervernets TilbudSendtDato er før sakens StartDato <br/>
så gi feilmeldingen "Ettervern sin tilbud-sendt-dato {TilbudSendtDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>



## <a name="flytting">Flytting</a>

### Flytting Kontroll 2c: SluttDato mot sakens SluttDato

Gitt at man har en Flytting der SluttDato finnes og en sak der SluttDato finnes<br/>
når flyttingens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Flyttingens sluttdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2022-01-01



### Flytting Kontroll 2f: SluttDato mot sakens StartDato

Gitt at man har en Flytting der SluttDato finnes og en sak der StartDato finnes<br/>
når flyttingens SluttDato er før sakens StartDato<br/>
så gi feilmeldingen "Flyttingens sluttdato {SluttDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Alvorlighetsgrad med migrert-id: WARNING<br/>
Gyldig fra 2022-01-01



## <a name="oversendelse-til-fylkesnemnd">Oversendelse til fylkesnemnd</a>

### Oversendelse til fylkesnemnd Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Oversendelse til fylkesnemnd der StartDato finnes og en sak der StartDato finnes<br/>
når Oversendelse til fylkesnemndens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Oversendelse til fylkesnemndens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Oversendelse til fylkesnemnd Kontroll 2f: StartDato er etter sakens SluttDato

Gitt at man har en Oversendelse til fylkesnemnd der StartDato finnes og en sak der SluttDato finnes<br/>
når Oversendelse til fylkesnemndens StartDato er etter sakens SluttDato <br/>
så gi feilmeldingen "Oversendelse til fylkesnemndens startdato {StartDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



### Oversendelse til fylkesnemnd Kontroll 5: Lovhjemmel refererer til feil barnevernlov

Gitt at man har et Oversendelse til fylkesnemnd<br/>

når OversendelseFylkesnemnd sin StartDato er før 01. januar 2023 og OversendelseFylkesnemnd/Lovhjemmel/Lov ikke er BVL<br/>
så gi feilmeldingen "Lovhjemmel opprettet før 01. januar 2023 krever lov = '**[BVL](#barnevernloven)**'"

når OversendelseFylkesnemnd sin StartDato er 01. januar 2023 eller senere og OversendelseFylkesnemnd/Lovhjemmel/Lov ikke er BVL2021<br/>
så gi feilmeldingen "Lovhjemmel opprettet på 01. januar 2023 eller senere krever lov = '**[BVL2021](#barnevernloven)**'"

Alvorlighetsgrad: ERROR<br/>
Gyldig fra 2022-01-01



## <a name="endringslogg">Endringslogg</a>

### <a name="2023-06-05">2023-06-05</a>

Lagt til
- Vedtak Kontroll 19: Dublett på innhold, men forskjellige identer
- Plan Kontroll 19: Dublett på innhold, men forskjellige identer

### <a name="2023-05-05">2023-05-05</a>

Endret
- Tiltak Kontroll 12: Omsorgstiltak med sluttdato krever årsak til opphevelse, endring i kravet.
- Plan Kontroll 2f: UtfortDato er etter sakens SluttDato, tydeligjort kravet.
- Plan Kontroll 2g: UtfortDato er før sakens StartDato, tydeligjort kravet.

### <a name="2023-05-02">2023-05-02</a>

Endret
- Vedtak Kontroll 2i: Status sin EndretDato er etter vedtakets SluttDato, endret alvorlighetsgrad fra ERROR til INFO.
- Vedtak Kontroll 2j: Status sin EndretDato er før vedtakets StartDato, endret alvorlighetsgrad fra ERROR til INFO.
- Personalia Kontroll 15: Kontroll av Fødselsnummer, fødselsdato og StartDato, fjernet første del av kravet.

### <a name="2023-01-06">2023-01-13</a>

Lagt til
- Punkt om "Migrering av data innrapportert via Kostra"
- Punkt om "Innrapporteringskanaler"
- Gyldig fra dato for alle valideringsregler

### <a name="2023-01-06">2023-01-06</a>

Lagt til
- Vedtak kontroll 5: Lovhjemmel refererer til feil barnevernlov
- Oversendelse til fylkesnemnd kontroll 5: Lovhjemmel refererer til feil barnevernlov

### <a name="2022-10-31">2022-10-31</a>

Endret
- Sak Kontroll 7: Klient over 25 år og skal avsluttes i barnevernet, endret alvorlighetsgrad fra ERROR til WARNING.
- Tiltak Kontroll 13: Individ er over 18 år og har omsorgtiltak, endring i kravet.

Slettet
- Personalia Kontroll 2e: StartDato er før sakens StartDato
- Ettervern Kontroll 3: Alder er mindre enn 18 år-

### <a name="2_0_0">2.0.0</a>

Lagt til
- Personalia Kontroll 2e: StartDato er før sakens StartDato
- Personalia Kontroll 2f: StartDato er etter sakens SluttDato
- Personalia kontroll 11: Klienten skal ha personalia

Flyttet og tilpasset
- Sak kontroll 12: Fødselsnummer -> Personalia kontroll 12: Fødselsnummer
- Sak Kontroll 13: Kontroll av Fødselsnummer og Fødselsdato -> Personalia Kontroll 13: Kontroll av Fødselsnummer og Fødselsdato
- Sak Kontroll 14: Kontroll av Fødselsnummer og Kjønn -> Personalia Kontroll 14: Kontroll av Fødselsnummer og Kjønn
- Sak Kontroll 15: Kontroll av Fødselsnummer, fødselsdato og StartDato -> Personalia Kontroll 15: Kontroll av Fødselsnummer, fødselsdato og StartDato
- Sak Kontroll 16: Kontroll av Fødselsdato og StartDato -> Personalia Kontroll 16: Kontroll av Fødselsdato og StartDato
- Sak Kontroll 1x: Kontroll av DUF-nummer -> Personalia Kontroll 17: Kontroll av DUF-nummer

Slettet
- Sak Kontroll 3: Fødselsnummer og DUFnummer
