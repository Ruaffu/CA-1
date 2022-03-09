package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CityInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String zipcode;
    private String city;

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}