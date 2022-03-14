package no.ssb.barn.xsd

import java.time.ZonedDateTime
import java.util.*

/**
 * Specialized POJO for use when serializing to JSON.
 */
class TiltakTypeJson(
    id: UUID,
    migrertId: String?,
    startDato: ZonedDateTime,
    lovhjemmel: LovhjemmelType,
    jmfrLovhjemmel: MutableList<LovhjemmelType>,
    kategori: KategoriType,
    tilsyn: MutableList<TilsynType>,
    oppfolging: MutableList<OppfolgingType>,
    opphevelse: OpphevelseType?,
    var erOmsorgsTiltak: Boolean
) : TiltakType(
    id = id,
    migrertId = migrertId,
    startDato = startDato,
    lovhjemmel = lovhjemmel,
    jmfrLovhjemmel = jmfrLovhjemmel,
    kategori = kategori,
    tilsyn = tilsyn,
    oppfolging = oppfolging,
    opphevelse = opphevelse
) {
    /**
     * Constructor for use when creating new instances based on
     * parent type.
     *
     * NOTE: This constructor does not clone child instances.
     */
    constructor(tiltakType: TiltakType) : this(
        tiltakType.id as UUID,
        tiltakType.migrertId,
        tiltakType.startDato,
        tiltakType.lovhjemmel,
        tiltakType.jmfrLovhjemmel,
        tiltakType.kategori,
        tiltakType.tilsyn,
        tiltakType.oppfolging,
        tiltakType.opphevelse,
        erOmsorgsTiltak = tiltakType.erOmsorgsTiltak()
    )
}