package entities;

import dtos.PersonDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Set;
import javax.persistence.*;


@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<Phone> phones = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private Set<Hobby> hobbies = new HashSet<>();


    public Person() {
    }

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Person(String firstname, String lastname, String email, Set<Phone> phones, Address address, Set<Hobby> hobbies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phones = phones;
        this.address = address;
        this.hobbies = hobbies;
    }

    public void setAddress(Address address) {
        this.address = address;
        address.addPerson(this);
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        hobby.getPersons().add(this);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Address getAddress() {
        return address;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
        phone.setPerson(this);
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
}
