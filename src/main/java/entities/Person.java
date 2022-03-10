package entities;

import java.io.Serializable;
import java.util.List;
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

    @OneToMany(mappedBy = "person")
    private List<Phone> phones;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToMany(mappedBy = "persons")
    private List<Hobby> hobbies;

    
    public Person() {
    }

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public void setAddress(Address address) {
        this.address = address;
        address.addPerson(this);
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        hobby.addPerson(this);
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public boolean hasHobby(Hobby hobby) {
        for (Hobby h : hobbies) {
            if(h.getId() == hobby.getId()) {
                return true;
            }
        }
        return false;
    }
}
