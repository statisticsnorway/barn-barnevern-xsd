# kostra-barnevern-xsd
Filbeskrivelse basert på xml schema definition brukt til å rapportere barnevernsdata fra kommuner til Statistisk sentralbyrå i forbindelse med KOSTRA, Kommune-Stat-Rapportering.

# Versjonshistorikk
---
# v.0.10.1
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
- *Tiltak@Lovhjemmel* og *Tiltak@JmfrLovhjemmel* er tatt ut av *Tiltak* - forutsettes innrapportert via *Vedtak*
- *Presisering* er nå definert som en attributt-type (se i avsnitt "Grunnstruktur")
- *SaksinnholdType@Kode* kodeliste er endret
- *KategoriType@Kode* kodeliste er endret
- *VedtakType@StatusEndretDato* utgår, dette fanges opp tilstrekkelig nøye ved daglige innrapporteringer
- *Sak@Avsluttet* erstatter tidligere attributt *Avsluttet3112*

---
# v.0.9.1 (presentert i arbeidsgruppe med leverandører 21.06.2021 - ikke publisert)
Det er foretatt en omstrukturering av XSD’ene (definisjonene er splittet opp til 3 nye filer) samt noen endringer i innhold. Dette i et forsøk på å gjøre spesifikasjonen mer konsistent, forståelig og brukbar. I den nærmeste tiden vil vi søke å få avklart enkelte gjenstående spørsmål med leverandører og barnevernfaglige.

## Nye filer:

### Fagsystem.xsd (ny)
Innhold: Navn/versjon på systemet det rapporteres fra. Dette er nyttig informasjon ved forsøk på å finne ut av feil.

### Global.xsd (ny)
Inneholder definisjoner på attributtene Id, StartDato og SluttDato som tidligere lå i Individ.xsd

### Innsending.xsd (ny)
Inneholder definisjonen på nytt element InnsendingType og elementet Innsending. Behovet oppstår med flere innrapporteringer i løpet av et år. 

## Endringer:

### Barnevern.xsd
Attributtet *DatoUttrekk* er flyttet til *InnsendingType* i Innsending.xsd

### Individ.xsd
Endringer (i tillegg til det som er nevnt over):
-	*Undersokelse* er flyttet «ned» fra Melding til Individ.
-	Elementet *Ansvar* utgår
-	Nytt element *Ettervern* (*EttervernType*)
-	Elementet *DatoSisteEndring* utgår
-	Felles for alle enumeration-typer: Hver enumeration-verdi har fått tilhørende tekstlabel i annotation/appinfo, dette for å kunne gjøre enumeration-typene maskinlesbare
-	I Vedtak er *VedtaksgrunnlagType* erstattet med *SaksinnholdType*, disse var innholdsmessig like. 
-	*VedtaksgrunnlagType* utgår (se over)
-	Attributt *Konklusjon* har fått flere verdier for å kombinere tidl. KOSTRA og kommunal halvårsrapportering
-	Nytt attributt *Undersokelse@UtledetFraId* inneholder meldingens id, fordi *Undersøkelse* ikke lenger er del av *Melding*. 
