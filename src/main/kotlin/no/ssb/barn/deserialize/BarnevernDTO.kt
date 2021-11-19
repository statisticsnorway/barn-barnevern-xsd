package no.ssb.barn.deserialize

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import no.ssb.barn.xsd.*
import java.util.*

@JsonSerialize
@JsonDeserialize
data class BarnevernDTO (
    val userName: String,
    val userId: String?,
    val datouttrekk: Date?,
    val avgiver: AvgiverType?,
    val sak: SakType?
)

