package dtos;

import entities.Hobby;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HobbyDTO {
    private Long id;
    private String name;
    private String description;

    public HobbyDTO(Hobby hobby) {
        if(hobby.getId() != null)
            this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public static Set<HobbyDTO> getHobbyDTOs(Set<Hobby> hobbies){
        Set<HobbyDTO> hobbyDTOS = new HashSet<>();
        hobbies.forEach(h->hobbyDTOS.add(new HobbyDTO(h)));
        return hobbyDTOS;
    }

    public static Set<HobbyDTO> getHobbyDTOs(List<Hobby> hobbies){
        Set<HobbyDTO> hobbyDTOS = new HashSet<>();
        hobbies.forEach(h->hobbyDTOS.add(new HobbyDTO(h)));
        return hobbyDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
