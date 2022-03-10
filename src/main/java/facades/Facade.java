package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;


public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private Facade() {
    }

    /* todo: Get the all people with a given hobby: Done
        Get all persons living in a given city (i.e. 2800 Lyngby): Done
        Get a list of all zip codes in Denmark: Done
        Create a Person (with hobbies, phone, address etc.): Done
        Delete an address: method no working, problem with foreign keys
        Edit a Person to change hobbies and phone numbers etc.
     */


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstname(), personDTO.getLastname(), personDTO.getEmail(), personDTO.getPhones(), personDTO.getAddress(), personDTO.getHobbies());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    // todo: create custom exception
    public PersonDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
//        if (person == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new PersonDTO(person);
    }

    public Set<PersonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> personList = query.getResultList();
        return PersonDTO.getPersonDTOs(personList);
    }


    public Set<PersonDTO> getPersonsByHobby(HobbyDTO hobbyDTO){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name =:hobby", Hobby.class);
        query.setParameter("hobby", hobbyDTO.getName());
        Hobby hobby = query.getSingleResult();
        return PersonDTO.getPersonDTOs(hobby.getPersons());
    }

    //Get all persons living in a given city (i.e. 2800 Lyngby)
    public Set<PersonDTO> getAllPersonsByZip(String zipcode){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN Address a ON p.address.id = a.id WHERE a.cityInfo.zipcode =:zipcode", Person.class);
        query.setParameter("zipcode",zipcode);
        List<Person> people = query.getResultList();
        return PersonDTO.getPersonDTOs(people);
    }

    public Set<CityInfoDTO> getAllCityInfos() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
        List<CityInfo> cityInfos = query.getResultList();
        return CityInfoDTO.getCityInfoDTOs(cityInfos);
    }

//    public AddressDTO deleteAddress(Long id){
//        EntityManager em = emf.createEntityManager();
//
////        address.getPersons().forEach( (person -> {
////            person.setAddress(new Address("123", ""));
////            em.merge(person);
////        }));
////
////        address.setPersons(null);
//
//
//        Address address = null;
//        try {
//            em.getTransaction().begin();
//            address = em.find(Address.class, id);
//            em.remove(address);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//
//        return new AddressDTO(address);
//    }
}
