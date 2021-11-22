package no.ssb.barn.deserialize

import no.ssb.barn.xsd.BarnevernType
import javax.xml.bind.JAXBContext
import javax.xml.transform.Source

class DataConvertX {

    fun unmarshallXML(schemaFile: Source): BarnevernType? {
        val context: JAXBContext = JAXBContext.newInstance(BarnevernType::class.java)
        val unmarshaller = context.createUnmarshaller()

        return unmarshaller.unmarshal(schemaFile) as BarnevernType
    }

}