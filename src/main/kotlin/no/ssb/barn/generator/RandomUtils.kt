package no.ssb.barn.generator

import no.ssb.barn.xsd.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("SpellCheckingInspection")
object RandomUtils {

    @JvmStatic
    fun generateRandomIntFromRange(
        startInclusive: Int,
        endExclusive: Int
    ): Int =
        (startInclusive until endExclusive).random()

    @JvmStatic
    fun generateRandomLongFromRange(
        startInclusive: Long,
        endExclusive: Long
    ): Long =
        (startInclusive until endExclusive).random()

    @JvmStatic
    fun generateRandomString(stringLength: Int): String =
        (1..stringLength)
            .map { generateRandomIntFromRange(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

    @JvmStatic
    fun generateRandomInt(): Int = (1..100_000).random()

    @JvmStatic
    fun generateRandomSSN(
        startInclusive: LocalDate,
        endExclusive: LocalDate
    ): String {
        val startEpochDay: Long = startInclusive.toEpochDay()
        val endEpochDay: Long = endExclusive.toEpochDay()
        val randomDay =
            generateRandomLongFromRange(startEpochDay, endEpochDay)
        val birthDate123456 = LocalDate.ofEpochDay(randomDay)
            .format(DateTimeFormatter.ofPattern("ddMMyy")).toString()

        while (true) {
            val ssn789 = generateRandomIntFromRange(100, 499).toString()
            val mod10 = birthDate123456.plus(ssn789)
                .toCharArray()
                .map { s -> s.toString().toInt() }
                .toList()
                .zip(
                    listOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
                ) { digit, weight -> digit * weight }
                .fold(0) { sum, i -> sum + i }
                .mod(11)

            if (mod10 != 1) {
                val digit10 = if (mod10 == 0) 0 else 11 - mod10
                val mod11 =
                    birthDate123456.plus(ssn789).plus(digit10.toString())
                        .toCharArray()
                        .map { s -> s.toString().toInt() }
                        .toList()
                        .zip(
                            listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)
                        ) { digit, weight -> digit * weight }
                        .fold(0) { sum, i -> sum + i }
                        .mod(11)

                if (mod11 != 1) {
                    val digit11 = if (mod11 == 0) 0 else 11 - mod11

                    return birthDate123456.plus(ssn789)
                        .plus(digit10.toString()).plus(digit11.toString())
                }
            }
        }
    }

    @JvmStatic
    fun generateRandomFagsystemType(): FagsystemType =
        listOf(
            FagsystemType("Visma", "Flyt Barnevern", "1.0.0"),
            FagsystemType("Netcompany", "Modulus Barn", "0.1.0"),
        ).random()

    @JvmStatic
    fun generateRandomVirksomhetType(avgiver: AvgiverType): VirksomhetType =
        VirksomhetType().apply {

            // we should probably use orgnr for bydel when Oslo
            organisasjonsnummer = avgiver.organisasjonsnummer

            if (avgiver.kommunenummer == GeneratorConstants.OSLO) {
                cityPartsOslo.entries.random().also {
                    bydelsnummer = it.key
                    bydelsnavn = it.value
                }
            }
        }

    @JvmStatic
    fun generateRandomMeldingType(currentDate: LocalDate): MeldingType =
        MeldingType().apply {
            id = java.util.UUID.randomUUID()
            startDato = currentDate
            melder.add(MelderType(MelderType.getRandomCode(currentDate)))
            saksinnhold.add(
                SaksinnholdType(
                    SaksinnholdType.getRandomCode(currentDate)
                )
            )
        }

    @JvmStatic
    fun generateRandomAvgiverType() = avgiverType.random()

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private val cityPartsOslo = mapOf(
        Pair("12", "Alna"),
        Pair("09", "Bjerke"),
        Pair("05", "Frogner"),
        Pair("01", "Gamle Oslo"),
        Pair("10", "Grorud"),
        Pair("02", "Grünerløkka"),
        Pair("08", "Nordre Aker"),
        Pair("14", "Nordstrand"),
        Pair("03", "Sagene"),
        Pair("04", "St. Hanshaugen"),
        Pair("11", "Stovner"),
        Pair("15", "Søndre Nordstrand"),
        Pair("06", "Ullern"),
        Pair("07", "Vestre Aker"),
        Pair("13", "Østensjø")
    )

    private val avgiverType = listOf(
        AvgiverType("958935420", GeneratorConstants.OSLO, "Oslo"),

        AvgiverType("944496394", "1101", "Eigersund"),
        AvgiverType("964965226", "1103", "Stavanger"),
        AvgiverType("944073787", "1106", "Haugesund"),
        AvgiverType("964965137", "1108", "Sandnes"),
        AvgiverType("964965692", "1111", "Sokndal"),
        AvgiverType("964966486", "1112", "Lund"),
        AvgiverType("970490361", "1114", "Bjerkreim"),
        AvgiverType("964969590", "1119", "Hå"),
        AvgiverType("864969682", "1120", "Klepp"),
        AvgiverType("859223672", "1121", "Time"),
        AvgiverType("964978573", "1122", "Gjesdal"),
        AvgiverType("948243113", "1124", "Sola"),
        AvgiverType("934945514", "1127", "Randaberg"),
        AvgiverType("964978751", "1130", "Strand"),
        AvgiverType("864979092", "1133", "Hjelmeland"),
        AvgiverType("964979189", "1134", "Suldal"),
        AvgiverType("964979367", "1135", "Sauda"),
        AvgiverType("964979634", "1144", "Kvitsøy"),
        AvgiverType("964979723", "1145", "Bokn"),
        AvgiverType("964979812", "1146", "Tysvær"),
        AvgiverType("940791901", "1149", "Karmøy"),
        AvgiverType("964979901", "1151", "Utsira"),
        AvgiverType("988893226", "1160", "Vindafjord"),
        AvgiverType("991891919", "1505", "Kristiansund"),
        AvgiverType("921221967", "1506", "Molde"),
        AvgiverType("920415288", "1507", "Ålesund"),
        AvgiverType("964978662", "1511", "Vanylven"),
        AvgiverType("822534422", "1514", "Sande"),
        AvgiverType("964978840", "1515", "Herøy"),
        AvgiverType("964979456", "1516", "Ulstein"),
        AvgiverType("964979278", "1517", "Hareid"),
        AvgiverType("939461450", "1520", "Ørsta"),
        AvgiverType("964980098", "1525", "Stranda"),
        AvgiverType("964980365", "1528", "Sykkylven"),
        AvgiverType("964980543", "1531", "Sula"),
        AvgiverType("964980721", "1532", "Giske"),
        AvgiverType("939901965", "1535", "Vestnes"),
        AvgiverType("864980902", "1539", "Rauma"),
        AvgiverType("964981337", "1547", "Aukra"),
        AvgiverType("962378064", "1554", "Averøy"),
        AvgiverType("964981426", "1557", "Gjemnes"),
        AvgiverType("964981515", "1560", "Tingvoll"),
        AvgiverType("964981604", "1563", "Sunndal"),
        AvgiverType("964981892", "1566", "Surnadal"),
        AvgiverType("945012986", "1573", "Smøla"),
        AvgiverType("988913898", "1576", "Aure"),
        AvgiverType("939760946", "1577", "Volda"),
        AvgiverType("921891687", "1578", "Fjord"),
        AvgiverType("921133642", "1579", "Hustadvika"),
        AvgiverType("972418013", "1804", "Bodø"),
        AvgiverType("959469059", "1806", "Narvik"),
        AvgiverType("964983380", "1811", "Bindal"),
        AvgiverType("944810277", "1812", "Sømna"),
        AvgiverType("964983291", "1813", "Brønnøy"),
        AvgiverType("941017975", "1815", "Vega"),
        AvgiverType("940651034", "1816", "Vevelstad"),
        AvgiverType("872417982", "1818", "Herøy"),
        AvgiverType("938712441", "1820", "Alstahaug"),
        AvgiverType("945034572", "1822", "Leirfjord"),
        AvgiverType("844824122", "1824", "Vefsn"),
        AvgiverType("940643112", "1825", "Grane"),
        AvgiverType("944716904", "1826", "Hattfjelldal"),
        AvgiverType("945114878", "1827", "Dønna"),
        AvgiverType("939600515", "1828", "Nesna"),
        AvgiverType("846316442", "1832", "Hemnes"),
        AvgiverType("872418032", "1833", "Rana"),
        AvgiverType("940667852", "1834", "Lurøy"),
        AvgiverType("940192692", "1835", "Træna"),
        AvgiverType("945717173", "1836", "Rødøy"),
        AvgiverType("970189866", "1837", "Meløy"),
        AvgiverType("845901422", "1838", "Gildeskål"),
        AvgiverType("961147867", "1839", "Beiarn"),
        AvgiverType("972417734", "1840", "Saltdal"),
        AvgiverType("972418021", "1841", "Fauske - Fuosko"),
        AvgiverType("972417750", "1845", "Sørfold"),
        AvgiverType("962299385", "1848", "Steigen"),
        AvgiverType("945468661", "1851", "Lødingen"),
        AvgiverType("940642140", "1853", "Evenes - Evenášši"),
        AvgiverType("945037687", "1856", "Røst"),
        AvgiverType("964994307", "1857", "Værøy"),
        AvgiverType("863320852", "1859", "Flakstad"),
        AvgiverType("942570619", "1860", "Vestvågøy"),
        AvgiverType("938644500", "1865", "Vågan"),
        AvgiverType("958501420", "1866", "Hadsel"),
        AvgiverType("945452676", "1867", "Bø"),
        AvgiverType("845152012", "1868", "Øksnes"),
        AvgiverType("847737492", "1870", "Sortland - Suortá"),
        AvgiverType("945624809", "1871", "Andøy"),
        AvgiverType("945962151", "1874", "Moskenes"),
        AvgiverType("970542507", "1875", "Hamarøy - Hábmer"),
        AvgiverType("959159092", "3001", "Halden"),
        AvgiverType("920817521", "3002", "Moss"),
        AvgiverType("938801363", "3003", "Sarpsborg"),
        AvgiverType("940039541", "3004", "Fredrikstad"),
        AvgiverType("921234554", "3005", "Drammen"),
        AvgiverType("942402465", "3006", "Kongsberg"),
        AvgiverType("940100925", "3007", "Ringerike"),
        AvgiverType("964947082", "3011", "Hvaler"),
        AvgiverType("940875560", "3012", "Aremark"),
        AvgiverType("964944334", "3013", "Marker"),
        AvgiverType("920123899", "3014", "Indre Østfold"),
        AvgiverType("941962726", "3015", "Skiptvet"),
        AvgiverType("945372281", "3016", "Rakkestad"),
        AvgiverType("940802652", "3017", "Råde"),
        AvgiverType("959272581", "3018", "Våler (Viken)"),
        AvgiverType("943485437", "3019", "Vestby"),
        AvgiverType("922092648", "3020", "Nordre Follo"),
        AvgiverType("964948798", "3021", "Ås"),
        AvgiverType("963999089", "3022", "Frogn"),
        AvgiverType("944383565", "3023", "Nesodden"),
        AvgiverType("935478715", "3024", "Bærum"),
        AvgiverType("920125298", "3025", "Asker"),
        AvgiverType("948164256", "3026", "Aurskog-Høland"),
        AvgiverType("952540556", "3027", "Rælingen"),
        AvgiverType("964949581", "3028", "Enebakk"),
        AvgiverType("842566142", "3029", "Lørenskog"),
        AvgiverType("820710592", "3030", "Lillestrøm"),
        AvgiverType("971643870", "3031", "Nittedal"),
        AvgiverType("864949762", "3032", "Gjerdrum"),
        AvgiverType("933649768", "3033", "Ullensaker"),
        AvgiverType("938679088", "3034", "Nes (Akershus)"),
        AvgiverType("964950113", "3035", "Eidsvoll"),
        AvgiverType("964950202", "3036", "Nannestad"),
        AvgiverType("939780777", "3037", "Hurdal"),
        AvgiverType("960010833", "3038", "Hole"),
        AvgiverType("964951462", "3039", "Flå"),
        AvgiverType("964951640", "3040", "Nes (Buskerud)"),
        AvgiverType("964952612", "3041", "Gol"),
        AvgiverType("964952701", "3042", "Hemsedal"),
        AvgiverType("864952992", "3043", "Ål"),
        AvgiverType("944889116", "3044", "Hol"),
        AvgiverType("964962766", "3045", "Sigdal"),
        AvgiverType("964962855", "3046", "Krødsherad"),
        AvgiverType("970491589", "3047", "Modum"),
        AvgiverType("954597482", "3048", "Øvre Eiker"),
        AvgiverType("857566122", "3049", "Lier"),
        AvgiverType("940898862", "3050", "Flesberg"),
        AvgiverType("964963282", "3051", "Rollag"),
        AvgiverType("964950946", "3052", "Nore og Uvdal"),
        AvgiverType("961381363", "3053", "Jevnaker"),
        AvgiverType("961381452", "3054", "Lunner"),
        AvgiverType("944117784", "3401", "Kongsvinger"),
        AvgiverType("970540008", "3403", "Hamar"),
        AvgiverType("945578564", "3405", "Lillehammer"),
        AvgiverType("940155223", "3407", "Gjøvik"),
        AvgiverType("864950582", "3411", "Ringsaker"),
        AvgiverType("964950679", "3412", "Løten"),
        AvgiverType("970169717", "3413", "Stange"),
        AvgiverType("964950768", "3414", "Nord-Odal"),
        AvgiverType("964947716", "3415", "Sør-Odal"),
        AvgiverType("964948054", "3416", "Eidskog"),
        AvgiverType("964948143", "3417", "Grue"),
        AvgiverType("964948232", "3418", "Åsnes"),
        AvgiverType("871034222", "3419", "Våler (Innlandet)"),
        AvgiverType("952857991", "3420", "Elverum"),
        AvgiverType("864948502", "3421", "Trysil"),
        AvgiverType("940152496", "3422", "Åmot"),
        AvgiverType("964948887", "3423", "Stor-Elvdal"),
        AvgiverType("940028515", "3424", "Rendalen"),
        AvgiverType("964948976", "3425", "Engerdal"),
        AvgiverType("940192404", "3426", "Tolga"),
        AvgiverType("940837685", "3427", "Tynset"),
        AvgiverType("939984194", "3428", "Alvdal"),
        AvgiverType("939885684", "3429", "Folldal"),
        AvgiverType("943464723", "3430", "Os (Innlandet)"),
        AvgiverType("939849831", "3431", "Dovre"),
        AvgiverType("964949204", "3432", "Lesja"),
        AvgiverType("961381096", "3433", "Skjåk"),
        AvgiverType("959377677", "3434", "Lom"),
        AvgiverType("939607706", "3435", "Vågå"),
        AvgiverType("839893132", "3436", "Nord-Fron"),
        AvgiverType("939617671", "3437", "Sel"),
        AvgiverType("941827195", "3438", "Sør-Fron"),
        AvgiverType("939864970", "3439", "Ringebu"),
        AvgiverType("961381185", "3440", "Øyer"),
        AvgiverType("961381274", "3441", "Gausdal"),
        AvgiverType("964949859", "3442", "Østre Toten"),
        AvgiverType("971028300", "3443", "Vestre Toten"),
        AvgiverType("961381541", "3446", "Gran"),
        AvgiverType("961381630", "3447", "Søndre Land"),
        AvgiverType("861381722", "3448", "Nordre Land"),
        AvgiverType("961381819", "3449", "Sør-Aurdal"),
        AvgiverType("933038173", "3450", "Etnedal"),
        AvgiverType("961381908", "3451", "Nord-Aurdal"),
        AvgiverType("961382157", "3452", "Vestre Slidre"),
        AvgiverType("961382068", "3453", "Øystre Slidre"),
        AvgiverType("961382246", "3454", "Vang"),
        AvgiverType("964951284", "3801", "Horten"),
        AvgiverType("917151229", "3802", "Holmestrand"),
        AvgiverType("921383681", "3803", "Tønsberg"),
        AvgiverType("916882807", "3804", "Sandefjord"),
        AvgiverType("918082956", "3805", "Larvik"),
        AvgiverType("939991034", "3806", "Porsgrunn"),
        AvgiverType("938759839", "3807", "Skien"),
        AvgiverType("938583986", "3808", "Notodden"),
        AvgiverType("817263992", "3811", "Færder"),
        AvgiverType("864953042", "3812", "Siljan"),
        AvgiverType("940244145", "3813", "Bamble"),
        AvgiverType("963946902", "3814", "Kragerø"),
        AvgiverType("933277461", "3815", "Drangedal"),
        AvgiverType("964963371", "3816", "Nome"),
        AvgiverType("920297293", "3817", "Midt-Telemark"),
        AvgiverType("864963552", "3818", "Tinn"),
        AvgiverType("964963649", "3819", "Hjartdal"),
        AvgiverType("964963738", "3820", "Seljord"),
        AvgiverType("964963827", "3821", "Kviteseid"),
        AvgiverType("964964343", "3822", "Nissedal"),
        AvgiverType("939772766", "3823", "Fyresdal"),
        AvgiverType("964964521", "3824", "Tokke"),
        AvgiverType("964964610", "3825", "Vinje"),
        AvgiverType("964977402", "4201", "Risør"),
        AvgiverType("864964702", "4202", "Grimstad"),
        AvgiverType("940493021", "4203", "Arendal"),
        AvgiverType("820852982", "4204", "Kristiansand"),
        AvgiverType("921060440", "4205", "Lindesnes"),
        AvgiverType("964083266", "4206", "Farsund"),
        AvgiverType("964967369", "4207", "Flekkefjord"),
        AvgiverType("964964998", "4211", "Gjerstad"),
        AvgiverType("964965048", "4212", "Vegårshei"),
        AvgiverType("964965781", "4213", "Tvedestrand"),
        AvgiverType("946439045", "4214", "Froland"),
        AvgiverType("964965404", "4215", "Lillesand"),
        AvgiverType("964965870", "4216", "Birkenes"),
        AvgiverType("864965962", "4217", "Åmli"),
        AvgiverType("864966012", "4218", "Iveland"),
        AvgiverType("964966109", "4219", "Evje og Hornnes"),
        AvgiverType("964966397", "4220", "Bygland"),
        AvgiverType("964966575", "4221", "Valle"),
        AvgiverType("958814968", "4222", "Bykle"),
        AvgiverType("936846777", "4223", "Vennesla"),
        AvgiverType("964966842", "4224", "Åseral"),
        AvgiverType("922421498", "4225", "Lyngdal"),
        AvgiverType("964963916", "4226", "Hægebostad"),
        AvgiverType("964964076", "4227", "Kvinesdal"),
        AvgiverType("964964165", "4228", "Sirdal"),
        AvgiverType("964338531", "4601", "Bergen"),
        AvgiverType("820956532", "4602", "Kinn"),
        AvgiverType("959435375", "4611", "Etne"),
        AvgiverType("864967272", "4612", "Sveio"),
        AvgiverType("834210622", "4613", "Bømlo"),
        AvgiverType("939866914", "4614", "Stord"),
        AvgiverType("944073310", "4615", "Fitjar"),
        AvgiverType("959412340", "4616", "Tysnes"),
        AvgiverType("964967636", "4617", "Kvinnherad"),
        AvgiverType("920500633", "4618", "Ullensvang"),
        AvgiverType("944227121", "4619", "Eidfjord"),
        AvgiverType("971159928", "4620", "Ulvik"),
        AvgiverType("960510542", "4621", "Voss"),
        AvgiverType("944233199", "4622", "Kvam"),
        AvgiverType("964968985", "4623", "Samnanger"),
        AvgiverType("844458312", "4624", "Bjørnafjorden"),
        AvgiverType("941139787", "4625", "Austevoll"),
        AvgiverType("922530890", "4626", "Øygarden"),
        AvgiverType("964338442", "4627", "Askøy"),
        AvgiverType("961821967", "4628", "Vaksdal"),
        AvgiverType("964969302", "4629", "Modalen"),
        AvgiverType("864338712", "4630", "Osterøy"),
        AvgiverType("920290922", "4631", "Alver"),
        AvgiverType("948350823", "4632", "Austrheim"),
        AvgiverType("944041036", "4633", "Fedje"),
        AvgiverType("945627913", "4634", "Masfjorden"),
        AvgiverType("938497524", "4635", "Gulen"),
        AvgiverType("964967458", "4636", "Solund"),
        AvgiverType("964967547", "4637", "Hyllestad"),
        AvgiverType("944439838", "4638", "Høyanger"),
        AvgiverType("937498764", "4639", "Vik"),
        AvgiverType("922121893", "4640", "Sogndal"),
        AvgiverType("964968063", "4641", "Aurland"),
        AvgiverType("954681890", "4642", "Lærdal"),
        AvgiverType("954679721", "4643", "Årdal"),
        AvgiverType("964968241", "4644", "Luster"),
        AvgiverType("964968330", "4645", "Askvoll"),
        AvgiverType("864968422", "4646", "Fjaler"),
        AvgiverType("921244207", "4647", "Sunnfjord"),
        AvgiverType("959318166", "4648", "Bremanger"),
        AvgiverType("921060157", "4649", "Stad"),
        AvgiverType("964969124", "4650", "Gloppen"),
        AvgiverType("963989202", "4651", "Stryn"),
        AvgiverType("942110464", "5001", "Trondheim"),
        AvgiverType("840029212", "5006", "Steinkjer"),
        AvgiverType("942875967", "5007", "Namsos - Nåavmesjenjaelmie"),
        AvgiverType("964982597", "5014", "Frøya"),
        AvgiverType("944350675", "5020", "Osen"),
        AvgiverType("964983003", "5021", "Oppdal"),
        AvgiverType("940083672", "5022", "Rennebu"),
        AvgiverType("939898743", "5025", "Røros"),
        AvgiverType("937697767", "5026", "Holtålen"),
        AvgiverType("970187715", "5027", "Midtre Gauldal"),
        AvgiverType("938726027", "5028", "Melhus"),
        AvgiverType("939865942", "5029", "Skaun"),
        AvgiverType("971035560", "5031", "Malvik"),
        AvgiverType("971197609", "5032", "Selbu"),
        AvgiverType("864983472", "5033", "Tydal"),
        AvgiverType("835231712", "5034", "Meråker"),
        AvgiverType("939958851", "5035", "Stjørdal"),
        AvgiverType("944482253", "5036", "Frosta"),
        AvgiverType("938587051", "5037", "Levanger"),
        AvgiverType("938587418", "5038", "Verdal"),
        AvgiverType("964982031", "5041", "Snåase - Snåsa"),
        AvgiverType("972417963", "5042", "Lierne"),
        AvgiverType("964982120", "5043", "Raarvihke - Røyrvik"),
        AvgiverType("864982212", "5044", "Namsskogan"),
        AvgiverType("940010853", "5045", "Grong"),
        AvgiverType("959220476", "5046", "Høylandet"),
        AvgiverType("939896600", "5047", "Overhalla"),
        AvgiverType("845153272", "5049", "Flatanger"),
        AvgiverType("944484574", "5052", "Leka"),
        AvgiverType("997391853", "5053", "Inderøy"),
        AvgiverType("944305483", "5054", "Indre Fosen"),
        AvgiverType("920920004", "5055", "Heim"),
        AvgiverType("938772924", "5056", "Hitra"),
        AvgiverType("921806027", "5057", "Ørland"),
        AvgiverType("921875533", "5058", "Åfjord"),
        AvgiverType("921233418", "5059", "Orkland"),
        AvgiverType("921785410", "5060", "Nærøysund"),
        AvgiverType("940138051", "5061", "Rindal"),
        AvgiverType("940101808", "5401", "Tromsø"),
        AvgiverType("972417971", "5402", "Harstad - Hárstták"),
        AvgiverType("944588132", "5403", "Alta"),
        AvgiverType("972418048", "5404", "Vardø"),
        AvgiverType("964993602", "5405", "Vadsø"),
        AvgiverType("921770669", "5406", "Hammerfest"),
        AvgiverType("972417998", "5411", "Kvæfjord"),
        AvgiverType("959469326", "5412", "Tjeldsund"),
        AvgiverType("959469792", "5413", "Ibestad"),
        AvgiverType("959469415", "5414", "Gratangen"),
        AvgiverType("959469881", "5415", "Loabák - Lavangen"),
        AvgiverType("864993982", "5416", "Bardu"),
        AvgiverType("961416388", "5417", "Salangen"),
        AvgiverType("972418005", "5418", "Målselv"),
        AvgiverType("940755603", "5419", "Sørreisa"),
        AvgiverType("864994032", "5420", "Dyrøy"),
        AvgiverType("921369417", "5421", "Senja"),
        AvgiverType("940208580", "5422", "Balsfjord"),
        AvgiverType("940330408", "5423", "Karlsøy"),
        AvgiverType("840014932", "5424", "Lyngen"),
        AvgiverType(
            "964994129",
            "5425",
            "Storfjord - Omasvuotna - Omasvuono"
        ),
        AvgiverType(
            "940363586",
            "5426",
            "Gáivuotna - Kåfjord - Kaivuono"
        ),
        AvgiverType("941812716", "5427", "Skjervøy"),
        AvgiverType("943350833", "5428", "Nordreisa - Ráisa - Raisi"),
        AvgiverType("940331102", "5429", "Kvænangen"),
        AvgiverType("945475056", "5430", "Guovdageaidnu - Kautokeino"),
        AvgiverType("963063237", "5432", "Loppa"),
        AvgiverType("964830711", "5433", "Hasvik"),
        AvgiverType("941087957", "5434", "Måsøy"),
        AvgiverType("938469415", "5435", "Nordkapp"),
        AvgiverType(
            "959411735",
            "5436",
            "Porsanger - Porsángu - Porsanki"
        ),
        AvgiverType("963376030", "5437", "Kárásjohka - Karasjok"),
        AvgiverType("940400392", "5438", "Lebesby"),
        AvgiverType("934266811", "5439", "Gamvik"),
        AvgiverType("962388108", "5440", "Berlevåg"),
        AvgiverType("943505527", "5441", "Deatnu - Tana"),
        AvgiverType("839953062", "5442", "Unjárga - Nesseby"),
        AvgiverType("938795592", "5443", "Båtsfjord"),
        AvgiverType("942110286", "5444", "Sør-Varanger")
    )
}