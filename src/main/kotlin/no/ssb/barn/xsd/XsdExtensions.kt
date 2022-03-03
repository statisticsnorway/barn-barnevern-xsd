package no.ssb.barn.xsd

fun TiltakType.erOmsorgsTiltak(): Boolean =
    lovhjemmel.kapittel == "4"
            && (
            lovhjemmel.paragraf == "12"
                    ||
                    lovhjemmel.paragraf == "8"
                    && lovhjemmel.ledd.any {
                listOf("2", "3").contains(it)
            })