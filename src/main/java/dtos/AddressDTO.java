package dtos;

import entities.Address;
import entities.CityInfo;
import entities.Person;

import java.util.Set;
// todo: make persons, cityinfo into strings...
public class AddressDTO {
    private Long id;
    private String street;
    private String additionalinfo;
    private Set<Person> persons;
    private CityInfo cityInfo;


    public AddressDTO(String street, String additionalinfo, Set<Person> persons, CityInfo cityInfo) {
        this.street = street;
        this.additionalinfo = additionalinfo;
        this.persons = persons;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(Address address) {
        if(address.getId() != null)
            this.id = address.getId();

        this.street = address.getStreet();
        this.additionalinfo = address.getAdditionalInfo();
        this.persons = address.getPersons();
        this.cityInfo = address.getCityInfo();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", additionalinfo='" + additionalinfo + '\'' +
                ", persons=" + persons +
                ", cityInfo=" + cityInfo +
                '}';
    }
}
