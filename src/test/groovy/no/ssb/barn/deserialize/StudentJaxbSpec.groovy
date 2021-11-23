package no.ssb.barn.deserialize

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import spock.lang.Ignore
import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class StudentJaxbSpec extends Specification {

    def "unmarshal student"() {
        given:
        def jAXBContext = JAXBContext.newInstance(Student.class);
        and:
        def unmarshaller = jAXBContext.createUnmarshaller();

        when:
        def student = (Student) unmarshaller.unmarshal(getSourceFromClasspath("./student.xml"));

        then:
        null != student
        and:
        "~name~" == student.name
    }

    def getSourceFromClasspath(String path) {
        new StreamSource(this.getClass().getClassLoader()
                .getResourceAsStream(path))
    }

    @Ignore("for manual testing")
    def "marshal student"() {
        given:
        def student = new Student("~name~", 42, "~subject~")
        and:
        JAXBContext jContext = JAXBContext.newInstance(Student.class)
        and:
        Marshaller marshallObj = jContext.createMarshaller();
        and:
        marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        when:
        marshallObj.marshal(student, new FileOutputStream("/home/roars/student.xml"));

        then:
        true == true
    }
}
