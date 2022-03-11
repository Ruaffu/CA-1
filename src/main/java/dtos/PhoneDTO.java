package dtos;

import entities.Phone;

import java.util.HashSet;
import java.util.Set;

public class PhoneDTO {
    private Long id;
    private String number;
    private String description;

    public PhoneDTO(Phone phone) {
        if(phone.getId() != null)
            this.id = phone.getId();
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

    public static Set<PhoneDTO> getPhoneDTOs(Set<Phone> phone){
        Set<PhoneDTO> phoneDTOS = new HashSet<>();
        phone.forEach(p->phoneDTOS.add(new PhoneDTO(p)));
        return phoneDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
