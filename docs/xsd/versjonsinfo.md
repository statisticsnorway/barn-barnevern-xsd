# kostra-barnevern-xsd
Filbeskrivelse basert på xml schema definition brukt til å rapportere barnevernsdata direkte fra kommunenes fagsystemer til Nasjonalt barnevernregister. Erstatter tidligere KOSTRA barnevern-rapportering til Statistisk sentralbyrå og Kommunal halvårsrapportering til Bufdir.

# Versjonshistorikk
---
# v.2.2.2
Endringsdato: 16. august 2023

## Struktur
- /@versjon oppdatert til 2.2.2
- Lagt til begrensning for alle dato-attributter, som for eksempel @Fodseldato, @StartDato og @SluttDato med flere. De må være i tidsrommet 01. januar 1998 til 31. desember 2029.
- Lagt til begrensning i form av regulære uttrykk for formatering av elementene Kapittel, Paragraf, Ledd, Bokstav og Punktum under LovhjemmelType

---
# v.2.2.1
Endringsdato: 15. juni 2023

## Struktur
- /@versjon oppdatert til 2.2.1
- Presisering
  - Endret lengde fra 300 til 10000
- Lagt til krav/begrensning på unik Id for:
  - Ettervern
  - Flytting
  - Melding
  - OversendelseFylkesnemnd
  - Personalia
  - Plan
  - Relasjon
  - Slettet
  - Tiltak
  - Undersøkelse
  - Vedtak

---
# v.2.2.0
Endringsdato: 10. januar 2023

## Struktur
- /@versjon oppdatert til 2.2.0
- PlanType
  - Lagt til ny kode:
    - 5 = Plan for ettervern etter BVL2021 § 8-5, 2. ledd / BVL1992 § 4-15, 4. ledd
    - 6 = Samværsplan etter BVL 2021 § 7-6
    - 7 = Undersøkelsesplan etter BVL 2021 § 2-2, 3. ledd
    - 8 = Undersøkelsesplan etter akuttvedtak etter BVL 2021 § 8-2, 3. ledd
  - Kode som utgår og blir ugyldig fra 1. januar 2023
    - 3 = Foreløpig omsorgsplan etter BVL1992 § 4-15, 3. ledd
- /Barnevern/Sak/Tiltak
  - Lagt til underelement SaksinnholdType

---
# v.2.1.1
Endringsdato: 16. desember 2022

## Struktur
- /@versjon oppdatert til 2.1.1
- SaksinnholdType
  - Lagt til nye koder: 
    - 29 = Foreldres kognitive vansker
    - 30 = Barnets kognitive vansker
    - 31 = Foreldre har vedvarende økonomiske problemer/vedvarende lavinntekt 

---
# v.2.1.0
Endringsdato: 02. desember 2022

## Struktur
- /@versjon oppdatert til 2.1.0
- /Barnevern/Sak/Vedtak
  - Lagt til attributten @MigrertId
- /Barnevern/Sak/Vedtak/Krav
  - Lagt til attributtene @Kode og @Presisering
- /Barnevern/Sak/Ettervern
  - Lagt til attributten @MigrertId
- /Barnevern/Sak/OversendelseFylkesnemnd
  - Lagt til attributten @MigrertId
---
# v.2.0.0
Endringsdato: 26. august 2022

## Struktur
- /@versjon oppdatert til 2.0.0 
- Lagt til /Barnevern/Sak/Personalia
- /Barnevern/Sak/Personalia
  - Lagt til attributtene @Id og @StartDato
  - Attributtene @Fodselsnummer, @Fodselsdato, @Kjonn og @DUFnummer er flyttet fra /Barnevern/Sak til /Barnevern/Sak/Personalia
- LovhjemmelType
  - Kun kodene BVL (som refererer til Barnevernsloven fra 1992) og BVL2021 (som refererer til Barnevernsloven fra 2021) er nå tilatt i bruk

---
# v.1.2.0
Endringsdato: 5. mai 2022

## Struktur
- /@versjon oppdatert til 1.2.0 
- LovhjemmelType
  - Endret listen med Ledd til å være valgfri
  - Lagt til liste med Bokstav, satt til å være valgfri

---
# v.1.1.0
Endringsdato: 8. april 2022

## Struktur
- /@versjon oppdatert til 1.1.0
- /Barnevern
  - Lagt til attributtet @Id, for å kunne identifisere innsendingen
  - Lagt til attributtet @ForrigeId, for å kunne identifisere forrige innsending og dermed vite at ingen informasjon har gått tapt mellom fagsystemet og SSB den gitt saken/barnet.
- /Barnevern/Leverandor
  - /Barnevern/Sak/Virksomhet/@Bydelsnummer er flyttet i struktur til /Barnevern/Leverandor/@Bydelsnummer 
  - /Barnevern/Sak/Virksomhet/@Bydelsnavn er flyttet i struktur til /Barnevern/Leverandor/@Bydelsnavn
- /Barnevern/Sak
  - Attributtet @Journalnummer er endret fra 20 til 36 tegn
  - Lagt til nyt kode 0 = Ufødt for attributtet @Kjonn
  - Attributtene @Fodselsnummer, @Fodselsdato og @Kjonn er endret fra valgfri til obligatorisk
- /Barnevern/Sak/Virksomhet er fjernet. Alle gjenværende underelementer er flyttet i struktur til Barnevern/Sak
- /Barnevern/Sak/Melding/Saksinnhold
  - Oppdatert forklaringstekst for kode 8
  - Lagt til manglende kode 22
  - Lagt ny kode 28
- /Barnevern/Sak/Undersokelse/Vedtaksgrunnlag
  - Sammen endringer som under Barnevern/Sak/Melding/Saksinnhold da de benytter felles kompleks type SaksinnholdType.
- /Barnevern/Sak/Tiltak/Kategori/@Kode
  - Fjernet kode 6.5
- /Barnevern/Sak/Tiltak/Tilsyn endret fra liste til et samlebegrep
  - Attributtene @Id og @UtfortDato under Barnevern/Sak/Tiltak/Tilsyn er flyttet i struktur til Barnevern/Sak/Tiltak/Tilsyn/Utfort
  - Lagt til nytt obligatorisk begrep Barnevern/Sak/Tiltak/Tilsyn/Ansvarlig med attributtene @Id, @StartDato og @Kommunenummer. Brukes til å referere hvilken kommune som har tilsynsansvar.
  - Lagt til nytt obligatorisk begrep Barnevern/Sak/Tiltak/Tilsyn/Hyppighet med attributtene @Id, @StartDato og @Kode. Brukes for å kunne vite hvor mange tilsyn det skal utføres per år.
- /Barnevern/Sak/Vedtak
  - Barnevern/Sak/Vedtak/Status er endret fra 0 til mange -> til 1 til mange
  - Barnevern/Sak/Vedtak/Status/@EndretDato er endret datatype fra xs:string til xs:date
  - Lagt til Barnevern/Sak/Vedtak/Status/@Id slik feilregistrerte statuser kan slettes
- /Barnevern/Sak/Slettet er lagt til med attributtene @Id, @Type (baseres på BegrepsType) og @Sluttdato. Brukes for å informere om sletting av elementer på grunn av for eksempel feilregistrering. Refererer til element ved hjelp av @Id og @Type.
- BegrepsType
  - Lagt typene Status og Relasjon
 
---
# v0.13.0
Endringsdato: 10. desember 2021

## Struktur
- Nytt element *Virksomhet* inn som rotelement i *Sak* - for å håndtere når sak overføres mellom ulike bydeler og samme sak med samme id rapporteres fra ulike bydeler
    - Attributt *AvgiverType/@Distriktsnummer*, *AvgiverType/@Bydelsnummer* og *AvgiverType/@Bydelsnavn* er flyttet til *VirksomhetType*
- *Ettervern* har fått ny struktur: 
    - Attributt *Konklusjon* er blitt til et element med attributt *Kode*
		- Element *Konklusjon* har fått attributt *SluttDato*
- *OversendelseBarneverntjeneste* lagt til under *Sak* (var definert som type, men ikke lagt til som element)
- Fjernet *AvvistMelding*, meldinger som karakteriseres som grunnløse skal ikke rapporteres inn
- *Melding/Konklusjon* er blitt et eget element som inneholder obligatoriske attributter *Kode* og *SluttDato* for å unngå egen valideringsregel
- *Plan/@EvaluertDato* flyttet i struktur til *Plan/Evaluering/@UtfortDato*
- *Plan/@SluttDato* flyttet i struktur til *Plan/Konklusjon/@SluttDato*
- *Undersokelse/@UtvidetFrist* og *Undersokelse/@UtvidetFristDato* erstattet med nytt element *Undersokelse/UtvidetFrist* med attributter *StartDato* og *Innvilget* (ja/nei)
- *Tiltak/@SluttDato* flyttet i struktur til *Tiltak/Konklusjon/SluttDato*
- *Vedtak/@StatusKode* flyttet til *Vedtak/Status/@Kode* 
- Nytt attributt: *Vedtak/Status/@EndretDato*
- Nytt element: *Virksomhet/Flytting*

## Nye elementtyper
- *MigrertId* representerer original id for elementer (*Virksomhet*, *Plan*, *Melding*, *Tiltak*, *Undersøkelse*) som er migrert fra gammel løsning. Disse kan potensielt være rapportert tidligere for saker som har historie tilbake til periode som er rapportert på gammel løsning. MigreringId brukes til å identifisere disse slik at man unngår duplikater.   
- *FlyttingType* representerer flytting av barn under omsorg (innbefatter ikke kun adresseendring i samme tiltak)

## Navneendringer
- Type *OversendelseBarneverntjeneste* endret til *OversendelseBarneverntjenesteType*
- Element *OversendelseBarneverntjeneste* endret til *OversendelseFylkesnemnd*

## Andre endringer
- Dokumentasjon lagt inn i flere element/typer
- Enum-typer har fått tilbake opplisting som Annotation for enklere oversikt i verktøy / generert dokumentasjon (men er fortsatt i tillegg maskinlesbare via AppInfo)
- Endret tekst *MelderType/@kode* 8=Politi
- *Lov*, *Kapittel* og *Paragraf* kan nå bare ha én forekomst (ikke lenger *maxOccurs=unbounded*),  
- Maks. lengde for *Presisering* økt fra 100 til 300
- Verdier 1.1, 1.2, 1.3, 1.4 fjernet fra kodeliste i *Melding/Konklusjon/@Kode*. 
  Ny liste:
	1 = Henlagt 
  2 = Ikke henlagt – konklusjonsdato melding (eventuelt 7 dager etter mottatt melding) er startdato undersøkelse
  3 = Henlagt pga. aktive tiltak
  4 = Melding i pågående undersøkelse

- Attributt *Sak/@Saksbehandler* utgår
- *Plan/@EvaluertDato* flyttet i struktur til *Plan/Evaluering/@UtfortDato*
- *Tiltak/@Lovhjemmel* og *Tiltak/@JmfrLovhjemmel* er lagt tilbake i *Tiltak* - praksis viser at et *Vedtak* kan inneholde flere tiltak med hver sin hjemmel
- *Tiltak/@SluttDato* flyttet i struktur til *Tiltak/Konklusjon/@SluttDato*
- *TiltakType/@Ettervern* fjernet (var en feil som hang igjen fra tidligere endring)
- Elementtype *AnsvarType* fjernet (var ikke i bruk)
- Attributt *Vedtak/Krav/@SluttDato* flyttet til nytt element *Vedtak/Krav/Konklusjon/@SluttDato*
- Nye enumverdier i *BegrepsType*: "OversendelseFylkesnemnd" og "Flytting"

---
# v0.10.1
XSD er nå strukturert for
- Innrapportering av én sak eller én avvist melding av gangen
- Rapporteringer med korte intervaller

Presisering av bruk av Id'er for innrapportering:
- Id'er forutsettes å være unike pr. elementtype pr. sak.	De trenger altså ikke være unike på tvers av elementtyper (eks.: et vedtak og en melding kan ha samme id).

## Struktur
- Alt er nå samlet i én fil for enklere redigering og oversikt
- Tidligere rotelement *Presisering* er lagt om til en type. XSD støtter ikke flere rotelement, så denne "feilen" har ikke vært synlig i XSD-verktøy eller ved lasting.

## Nye elementtyper
- *AvvistMelding* : Meldinger som ikke er realitetsbehandlet på grunn av innholdets karakter (sjikanøst, åpenbart grunnløst, o.l.) 
Kun Id skal innrapporteres (for å kunne telle opp unike id'er)
- *Referanse* : Representerer kobling mellom elementer
Alle koblinger som eksisterer i fagsystemet mellom elementer i xsd, som *Melding* <> *Undersøkelse*, *Undersøkelse* <> *Vedtak*, *Vedtak* <> *Vedtak*.
Erstatter tidligere *BasertPaaId*.
- *OversendelseBarneverntjeneste* representerer oversendelse fra barneverntjeneste til fylkesnemnd. Disse inngikk i kommunal halvårsrapportering, men lå ikke inne i XSD i versjon 0.9.0

## Navneendringer
- *Individ* er endret til *Sak*
- *Krav* er endret til *OversendelsePrivatKravType*

## Andre endringer
- *Tiltak/@Lovhjemmel* og *Tiltak/@JmfrLovhjemmel* er tatt ut av *Tiltak* - forutsettes innrapportert via *Vedtak*
- *Presisering* er nå definert som en attributt-type (se i avsnitt "Grunnstruktur")
- *SaksinnholdType/@Kode* kodeliste er endret
- *KategoriType/@Kode* kodeliste er endret
- *VedtakType/@StatusEndretDato* utgår, dette fanges opp tilstrekkelig nøye ved daglige innrapporteringer
- *Sak/@Avsluttet* erstatter tidligere attributt *Avsluttet3112*

---
# v0.9.1 (presentert i arbeidsgruppe med leverandører 21.06.2021 - ikke publisert)
Det er foretatt en omstrukturering av XSD’ene (definisjonene er splittet opp til 3 nye filer) samt noen endringer i innhold. Dette i et forsøk på å gjøre spesifikasjonen mer konsistent, forståelig og brukbar. I den nærmeste tiden vil vi søke å få avklart enkelte gjenstående spørsmål med leverandører og barnevernfaglige.

## Nye filer:
