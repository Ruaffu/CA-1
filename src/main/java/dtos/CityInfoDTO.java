package dtos;

import entities.Address;
import entities.CityInfo;

import javax.persistence.OneToMany;
import java.util.Set;

public class CityInfoDTO {
    private Long id;
    private String zipcode;
    private String city;
    private Set<Address> addresses;

    public CityInfoDTO(String zipcode, String city, Set<Address> addresses) {
        this.zipcode = zipcode;
        this.city = city;
        this.addresses = addresses;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if(cityInfo.getId() != null)
            this.id = cityInfo.getId();
        this.zipcode = cityInfo.getZipcode();
        this.city = cityInfo.getCity();
        this.addresses = cityInfo.getAddresses();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "CityInfoDTO{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
