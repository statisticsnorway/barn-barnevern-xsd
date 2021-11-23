package no.ssb.barn.deserialize

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
class Student {

    private String name
    private int id
    private String subject

    Student() {
    }

    Student(String name, int id, String subject) {
        this.name = name
        this.id = id
        this.subject = subject
    }

    @XmlElement
    public String getName() {
        return name
    }

    public void setName(String name) {
        this.name = name
    }

    @XmlAttribute
    public int getId() {
        return id
    }

    public void setId(int id) {
        this.id = id
    }

    @XmlElement
    public String getSubject() {
        return subject
    }

    public void setSubject(String subject) {
        this.subject = subject
    }
}
