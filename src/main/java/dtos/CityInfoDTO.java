package dtos;

import entities.Address;
import entities.CityInfo;
import entities.Person;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
// todo: make addresses into string...
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

    public static Set<CityInfoDTO> getCityInfoDTOs(List<CityInfo> cityInfos){
        Set<CityInfoDTO> cityInfoDTOs = new HashSet<>();
        cityInfos.forEach(c->cityInfoDTOs.add(new CityInfoDTO(c)));
        return cityInfoDTOs;
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
