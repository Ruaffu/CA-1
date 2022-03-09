package entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String additionalinfo;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;

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
}
