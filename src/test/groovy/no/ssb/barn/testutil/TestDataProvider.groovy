package no.ssb.barn.testutil

import no.ssb.barn.deserialize.BarnevernDeserializer
import no.ssb.barn.framework.ValidationContext

import java.time.Year

class TestDataProvider {

    static String getMockSocialSecurityNumber(int age){
        def twoDigitBirthYear = Year.now().minusYears(age).value % 100
        "0101${twoDigitBirthYear}88123"
    }

    static ValidationContext getTestContext() {

        // get XML
        def xmlAsString = getResourceAsString("test01_fil09.xml")

        // get deserialized XML
        def barnevernType = BarnevernDeserializer.unmarshallXml(xmlAsString)

        new ValidationContext(xmlAsString, barnevernType)
    }

    static String getResourceAsString(String resourceName) {
        new String(TestDataProvider.class.getClassLoader()
                .getResourceAsStream(resourceName).readAllBytes())
    }

    static InputStream getResourceAsStream(String path) {
        TestDataProvider.class.getClassLoader().getResourceAsStream(path)
    }
}
