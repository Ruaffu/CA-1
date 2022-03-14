package entities;

import dtos.PhoneDTO;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String description;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(String number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
    }

    public Phone(PhoneDTO phoneDTO){
        this.number = phoneDTO.getNumber();
        this.description = phoneDTO.getDescription();
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
