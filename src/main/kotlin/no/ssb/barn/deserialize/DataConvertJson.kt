package no.ssb.barn.deserialize

import org.json.XML
import java.io.File

class DataConvertJson {
    fun convertJson(): String {
        val p_index_factor = 2
        val xmlString = File("./test01_fil01.xml").readText() // skal byttes

        val jsonObj = XML.toJSONObject(xmlString)
        val jsonString = jsonObj.toString(p_index_factor)

        return jsonString
    }
}