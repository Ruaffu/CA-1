/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 *
 * @author tha
 */

// todo: phones, address, hobbies into Strings....
public class PersonDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private Set<Phone> phones;
    private Address address;
    private Set<Hobby> hobbies;

    //TODO: MAYBE DELETE LATER?
    public PersonDTO(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public PersonDTO(String firstname, String lastname, String email, Set<Phone> phones, Address address, Set<Hobby> hobbies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phones = phones;
        this.address = address;
        this.hobbies = hobbies;
    }

    public PersonDTO(Person person) {
        if(person.getId() != null)
            this.id = person.getId();
        this.firstname = person.getFirstname();
        this.lastname = person.getLastname();
        this.email = person.getEmail();
        this.phones = person.getPhones();
        this.address = person.getAddress();
        this.hobbies = person.getHobbies();
    }

    public static Set<PersonDTO> getPersonDTOs(List<Person> person){
        Set<PersonDTO> personDTOS = new HashSet<>();
        person.forEach(p->personDTOS.add(new PersonDTO(p)));
        return personDTOS;
    }

    public static Set<PersonDTO> getPersonDTOs(Set<Person> person){
        Set<PersonDTO> personDTOS = new HashSet<>();
        person.forEach(p->personDTOS.add(new PersonDTO(p)));
        return personDTOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", address=" + address +
                ", hobbies=" + hobbies +
                '}';
    }
}
