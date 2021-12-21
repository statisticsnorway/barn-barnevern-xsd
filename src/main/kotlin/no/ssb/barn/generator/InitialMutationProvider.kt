package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate

object InitialMutationProvider {

    @JvmStatic
    fun createInitialMutation(currentDate: LocalDate): BarnevernType =
        BarnevernType()
            .apply {

                datoUttrekk = currentDate.atStartOfDay().plusHours(
                    (8..20).random().toLong()
                )
                fagsystem = RandomUtils.generateRandomFagsystemType()
                avgiver = RandomUtils.generateRandomAvgiverType()

                // START sak
                // id OK
                // migrertId 2DO
                // startDato OK
                // sluttDato 2DO
                // journalnummer 2DO, init in constructor
                // fodselsnummer OK
                // duFnummer 2DO
                // avsluttet 2DO
                // virksomhet OK

                sak.apply {
                    startDato = currentDate
                    fodselsnummer = RandomUtils.generateRandomSSN(
                        LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(1)
                    )
                }

                // START sak -> virksomhet
                // startDato OK
                // sluttDato 2DO
                // organisasjonsnummer 2DO, init in constructor
                // bydelsnummer OK
                // bydelsnavn OK
                // distriktsnummer 2DO
                // melding partially OK, see generateRandomMeldingType
                // undersokelse partially OK, see fromMessageToInvestigation
                // plan partially OK, see fromMeasureToPlan
                // tiltak partially OK, see createTiltakType
                // vedtak partially OK, see createVedtakType
                // ettervern partially OK, see fromDecisionToAfterCare
                // oversendelseBarneverntjeneste 2DO
                // flytting 2DO
                // relasjon 2DO

                //
                // START virksomhet -> undersokelse
                //
                // id OK, init in constructor
                // migrertId 2DO
                // startDato 2DO, init in constructor
                // vedtaksgrunnlag 2DO
                // utvidetFrist 2DO
                // konklusjon 2DO

                // START virksomhet -> undersokelse -> vedtaksgrunnlag (MutableList<SaksinnholdType>)
                // kode partially OK, init in constructor
                // presisering 2DO

                // START virksomhet -> undersokelse -> utvidetFrist (UndersokelseUtvidetFristType?)
                // startDato 2DO, init in constructor
                // innvilget 2DO, init in constructor

                // START virksomhet -> undersokelse -> konklusjon (UndersokelseKonklusjonType?)
                // sluttDato 2DO, init in constructor
                // kode 2DO, init in constructor
                // presisering 2DO, init with null in constructor

                //
                // START virksomhet -> plan (MutableList<PlanType>)
                //
                // id OK, init in constructor
                // migrertId 2DO
                // startDato 2DO, init in constructor
                // plantype 2DO, init in constructor
                // evaluering 2DO (MutableList<PlanEvalueringType>)
                // konklusjon 2DO, init in constructor (PlanKonklusjonType?)

                // START virksomhet -> plan -> evaluering (MutableList<PlanEvalueringType>)
                // utfortDato 2DO, init in constructor

                // START virksomhet -> plan -> konklusjon (PlanKonklusjonType?)
                // sluttDato 2DO, init in constructor

                //
                // START virksomhet -> tiltak (MutableList<TiltakType>)
                // See also createTiltakType
                //
                // id OK, init in constructor
                // migrertId 2DO
                // startDato OK
                // lovhjemmel 2DO (LovhjemmelType?)
                // jmfrLovhjemmel 2DO (MutableList<LovhjemmelType>)
                // kategori 2DO (KategoriType?)
                // tilsyn 2DO (MutableList<TilsynType>)
                // oppfolging 2DO (MutableList<OppfolgingType>)
                // opphevelse 2DO (OpphevelseType?)
                // konklusjon 2DO (TiltakKonklusjonType?)

                // START virksomhet -> tiltak -> lovhjemmel (LovhjemmelType?)
                // START virksomhet -> tiltak -> jmfrLovhjemmel (MutableList<LovhjemmelType>)
                // lov 2DO, init in constructor
                // kapittel 2DO, init in constructor
                // paragraf 2DO, init in constructor
                // ledd 2DO, init in constructor, MutableList<String>
                // punktum 2DO, init in constructor, MutableList<String>

                // START virksomhet -> tiltak -> kategori (KategoriType)
                // kode 2DO, init in constructor
                // presisering 2DO

                // START virksomhet -> tiltak -> tilsyn (MutableList<TilsynType>)
                // id OK, init in constructor
                // utfortDato 2DO, init in constructor

                // START virksomhet -> tiltak -> oppfolging (MutableList<OppfolgingType>)
                // id OK, init in constructor
                // utfortDato 2DO, init in constructor

                // START virksomhet -> tiltak -> opphevelse (OpphevelseType?)
                // kode 2DO, init in constructor
                // presisering 2DO

                // START virksomhet -> tiltak -> konklusjon (TiltakKonklusjonType?)
                // sluttDato 2DO, init in constructor

                RandomUtils.generateRandomVirksomhetType(avgiver)
                    .also { virksomhet ->
                        virksomhet.startDato = currentDate
                        sak.virksomhet.add(virksomhet)

                        // START sak -> virksomhet -> melding
                        // id OK, init im constructor
                        // migrertId 2DO
                        // startDato OK
                        // melder, partially OK, see generateRandomMeldingType
                        // saksinnhold, partially OK, see generateRandomMeldingType
                        // konklusjon 2DO

                        RandomUtils.generateRandomMeldingType(currentDate)
                            .also { melding ->
                                virksomhet.melding.add(melding)
                            }
                    }
            }
}