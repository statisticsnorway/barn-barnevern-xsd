package no.ssb.barn.testutil

import no.ssb.barn.deserialize.BarnevernDeserializer
import no.ssb.barn.framework.ValidationContext

import java.time.Year

class TestDataProvider {

    static String getMockSocialSecurityNumber(int age){
        def twoDigitBirthYear = Year.now().minusYears(age).value % 100
        "0101${twoDigitBirthYear}99999"
    }

    static ValidationContext getTestContextXmlOnly(String xmlResource) {
        new ValidationContext(
                UUID.randomUUID().toString(),
                getResourceAsString(xmlResource))
    }

    static ValidationContext getTestContext() {
        def xmlAsString = getResourceAsString("test01_fil09.xml")

        new ValidationContext(
                UUID.randomUUID().toString(),
                xmlAsString,
                BarnevernDeserializer.unmarshallXml(xmlAsString))
    }

    static String getResourceAsString(String resourceName) {
        new String(TestDataProvider.class.getClassLoader()
                .getResourceAsStream(resourceName).readAllBytes())
    }

    static InputStream getResourceAsStream(String path) {
        TestDataProvider.class.getClassLoader().getResourceAsStream(path)
    }
}
