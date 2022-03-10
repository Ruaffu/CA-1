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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class PersonDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private List<Phone> phones;
    private Address address;
    private List<Hobby> hobbies;

    public PersonDTO(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public PersonDTO(String firstname, String lastname, String email, List<Phone> phones, Address address, List<Hobby> hobbies) {
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

    public static List<PersonDTO> getDtos(List<Person> rms){
        List<PersonDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new PersonDTO(rm)));
        return rmdtos;
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

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
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
