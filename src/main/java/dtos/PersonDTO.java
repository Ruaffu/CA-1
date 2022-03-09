/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;

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


    public PersonDTO(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }


    public static List<PersonDTO> getDtos(List<Person> rms){
        List<PersonDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new PersonDTO(rm)));
        return rmdtos;
    }


    public PersonDTO(Person p) {
        if(p.getId() != null)
            this.id = p.getId();
        this.firstname = p.getFirstname();
        this.lastname = p.getLastname();
        this.email = p.getEmail();
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

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
