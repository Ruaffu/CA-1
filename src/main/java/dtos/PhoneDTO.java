package dtos;

import entities.Person;
import entities.Phone;

// todo: make person field into a String
public class PhoneDTO {
    private Long id;
    private String number;
    private String description;
    private Person person;

    public PhoneDTO(String number, String description, Person person) {
        this.number = number;
        this.description = description;
        this.person = person;
    }

    public PhoneDTO(Phone phone) {
        if(phone.getId() != null)
            this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
        this.person = phone.getPerson();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", person=" + person +
                '}';
    }
}
