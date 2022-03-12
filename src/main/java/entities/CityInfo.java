package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CityInfo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipcode;
    private String city;

    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.PERSIST)
    private Set<Address> addresses;

    public CityInfo() {
    }

    public CityInfo(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public void addAddress(Address address) {
        if(address.getCityInfo() != this) {
            address.setCityInfo(this);
            this.addresses.add(address);
        }
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
