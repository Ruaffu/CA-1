package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String additionalinfo;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Person> persons = new HashSet<>();

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalinfo = additionalInfo;
    }

    public Address(Long id, String street, String additionalinfo, Set<Person> persons, CityInfo cityInfo) {
        this.id = id;
        this.street = street;
        this.additionalinfo = additionalinfo;
        this.persons = persons;
        this.cityInfo = cityInfo;
    }

    public Address(String street, String additionalinfo, CityInfo cityInfo) {
        this.street = street;
        this.additionalinfo = additionalinfo;
        this.cityInfo = cityInfo;
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
