package dtos;

import entities.Hobby;
import entities.Person;

import javax.persistence.ManyToMany;
import java.util.List;

public class HobbyDTO {
    private Long id;
    private String name;
    private String description;
    private List<Person> persons;

    public HobbyDTO(String name, String description, List<Person> persons) {
        this.name = name;
        this.description = description;
        this.persons = persons;
    }

    public HobbyDTO(Hobby hobby) {
        if(hobby.getId() != null)
            this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
        this.persons = hobby.getPersons();
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

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", persons=" + persons +
                '}';
    }
}
