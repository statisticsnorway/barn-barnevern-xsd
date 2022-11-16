package no.ssb.barn

import java.io.StringReader
import javax.xml.transform.stream.StreamSource


fun String.toStreamSource() = StreamSource(StringReader(this))


object TestUtils {

    fun getResourceAsString(resourceName: String) = this.javaClass.getResource("/$resourceName")!!.readText()
}
