package facades;

import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import utils.EMF_Creator;

public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private Facade() {
    }

    /* todo: Get all persons with a given hobby,
        Get all persons living in a given city (i.e. 2800 Lyngby)
        Get the all people with a given hobby
        Get a list of all zip codes in Denmark
        Create a Person (with hobbies, phone, address etc.)
        Delete an address
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


    // todo: check if it is up to date
    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstname(), personDTO.getLastname(), personDTO.getEmail());
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

    // todo: check if working correct
    public PersonDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
//        if (person == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new PersonDTO(person);
    }

    //todo: check if working correct
    public List<PersonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> personList = query.getResultList();
        return PersonDTO.getDtos(personList);
    }

    // todo: make hobbyDTO
    public List<PersonDTO> getPersonsByHobby(Hobby hobby){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.hobbies =:hobby", Person.class);
        query.setParameter("hobby", hobby.getName());
        List<Person> personList = query.getResultList();
        return PersonDTO.getDtos(personList);
    }


    // todo: needed?
//    public static void main(String[] args) {
//        emf = EMF_Creator.createEntityManagerFactory();
//        Facade fe = getFacade(emf);
//        fe.getAll().forEach(dto -> System.out.println(dto));
//    }

}
