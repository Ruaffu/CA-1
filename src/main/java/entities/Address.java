package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String additionalinfo;

    @OneToMany(mappedBy = "address")
    private Set<Person> persons;

    @ManyToOne()
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, String additionalinfo) {
        this.street = street;
        this.additionalinfo = additionalinfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public void addPerson(Person person) {
        if(person.getAddress() != this) {
            this.persons.add(person);
            person.setAddress(this);
        }
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
        cityInfo.addAddress(this);
    }
}
