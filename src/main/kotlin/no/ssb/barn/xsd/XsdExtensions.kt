package no.ssb.barn.xsd

fun TiltakType.erOmsorgsTiltak(): Boolean {
    val legalBasis = lovhjemmel
    return legalBasis != null
            && legalBasis.kapittel == "4"
            && (
            legalBasis.paragraf == "12"
                    ||
                    legalBasis.paragraf == "8"
                    && legalBasis.ledd.any {
                listOf("2", "3").contains(it)
            })

}