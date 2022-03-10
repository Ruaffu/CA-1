package entities;

import javax.persistence.*;

@Entity
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private String description;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
