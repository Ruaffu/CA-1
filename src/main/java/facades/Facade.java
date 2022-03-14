package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;


public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private Facade() {
    }

    /* todo: Delete an address: method no working, problem with foreign keys
             remake: Edit a Person to change hobbies and phone numbers etc.
             make custom exception, example not valid data, or missing data
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

    // todo: check if phone, hobby already exists
    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstname(), personDTO.getLastname(), personDTO.getEmail());
        EntityManager em = getEntityManager();
        try {
            // checks if address already exists
            Address address = checkAddress(personDTO.getAddress());
            if (address == null) {
                address = new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getAdditionalInfo(),
                        new CityInfo(personDTO.getAddress().getZipcode(), personDTO.getAddress().getCity()));
            }
            person.setAddress(address);

            // checks for address city
            CityInfo cityInfo = checkCityInfo(address.getCityInfo());
            if (cityInfo != null) {
                person.getAddress().setCityInfo(cityInfo);
            }

            // check if hobby exists and adds person to it
            personDTO.getHobbies().forEach(hobbyDTO -> {
                person.addHobby(checkHobby(hobbyDTO));
            });


            personDTO.getPhones().forEach(phoneDTO -> {
                person.addPhone(new Phone(phoneDTO.getNumber(), phoneDTO.getDescription()));
            });

            em.getTransaction().begin();
            em.persist(person);

            em.merge(person.getAddress().getCityInfo());

            person.getHobbies().forEach(hobby -> {
                if (hobby.getId() != null) {
                    em.merge(hobby);
                } else {
                    em.persist(hobby);
                }
            });

            if (person.getAddress().getId() == null) {
                em.persist(person.getAddress());
            } else {
                em.merge(person.getAddress());
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    private Hobby checkHobby(HobbyDTO hobbyDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name =:name", Hobby.class);
            query.setParameter("name", hobbyDTO.getName());

            Hobby hobby = query.getSingleResult();
            return hobby;

        } catch (NoResultException e) {
            return new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription());
        } finally {
            em.close();
        }
    }

    public boolean hasHobby(String hobbyname, Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name =:hobby", Hobby.class);
            query.setParameter("hobby", hobbyname);
            Hobby hobby = query.getSingleResult();
            if (hobby.getPersons().contains(person)) {
                return true;
            }
        } finally {
            em.close();
        }
        return false;
    }

    public void compareHobbies(PersonDTO pDTO, Person p) {
        for (Hobby h : p.getHobbies()) {
            if (pDTO.getHobbies().contains(h)) {

            } else {

            }
        }
    }

    public Address checkAddress(AddressDTO addressDTO) {
        EntityManager em = emf.createEntityManager();
        Address address;
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.street = :street AND a.cityInfo.zipcode =:zipcode", Address.class);
            query.setParameter("street", addressDTO.getStreet());
            query.setParameter("zipcode", addressDTO.getZipcode());
            address = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return address;
    }

    public CityInfo checkCityInfo(CityInfo cityInfo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c " +
                    "WHERE c.zipcode =:zipcode AND c.city =:city", CityInfo.class);
            query.setParameter("zipcode", cityInfo.getZipcode());
            query.setParameter("city", cityInfo.getCity());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
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


    public Set<PersonDTO> getPersonsByHobby(String hobbyName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name =:hobby", Hobby.class);
        query.setParameter("hobby", hobbyName);
        Hobby hobby = query.getSingleResult();
        return PersonDTO.getPersonDTOs(hobby.getPersons());
    }

    //Get all persons living in a given city (i.e. 2800 Lyngby)
    public Set<PersonDTO> getAllPersonsByZip(String zipcode) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN Address a ON p.address.id = a.id WHERE a.cityInfo.zipcode =:zipcode", Person.class);
        query.setParameter("zipcode", zipcode);
        List<Person> people = query.getResultList();
        return PersonDTO.getPersonDTOs(people);
    }

    public Set<CityInfoDTO> getAllCityInfos() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
        List<CityInfo> cityInfos = query.getResultList();
        return CityInfoDTO.getCityInfoDTOs(cityInfos);
    }

    // todo: remake method
    //  so that it works with DTO,
    //  checks for already existing data,
    //  and remove not used data from DB
    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();

        Person person = em.find(Person.class, personDTO.getId());
        Address address = em.find(Address.class, person.getAddress().getId());

        if (!(personDTO.getAddress().equals(address))) {
            person.setAddress(new Address(personDTO.getAddress()));
            if (address.getPersons().size() <= 1) {
                em.remove(address);
            }
        }

        CityInfo cityInfo = checkCityInfo(person.getAddress().getCityInfo());
        if (cityInfo != null) {
            person.getAddress().setCityInfo(cityInfo);
        }

        // adds person to hobbies
        personDTO.getHobbies().forEach(hobbyDTO -> {
            person.addHobby(checkHobby(hobbyDTO));
        });

        // makes a set of hobbies to remove
        Set<Hobby> hobbiesToRemove = new HashSet<>();
        for (Hobby h : person.getHobbies()) {
            if (!(personDTO.getHobbies().contains(new HobbyDTO(h)))) {
                hobbiesToRemove.add(h);
            }
        }
        // removes person from no longer active hobbies
         hobbiesToRemove.forEach(hobby -> {
             hobby.getPersons().remove(person);
         });



//        // todo: make sure that hobbies and phones that are no longer in use is remove from DB
//        person.setHobbies(personDTO.getHobbies());
//        person.setPhones(personDTO.getPhones());


        person.setFirstname(personDTO.getFirstname());
        person.setLastname(personDTO.getLastname());
        person.setEmail(personDTO.getEmail());

        try {
            em.getTransaction().begin();
            person.getHobbies().forEach(hobby -> {
                if (hobby.getId() != null) {
                    em.merge(hobby);
                } else {
                    em.persist(hobby);
                }
            });
            hobbiesToRemove.forEach(em::merge);

            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PersonDTO(person);
    }

    public int getNumberOfPeopleWithAHobby(String name) {
        EntityManager em = getEntityManager();
        TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name=:name", Hobby.class);
        query.setParameter("name", name);
        Hobby hobby = query.getSingleResult();
        return hobby.getPersons().size();
    }

    public PersonDTO getPersonByPhoneNumber(String number) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN Phone pn WHERE pn.number =:number AND p.id= pn.person.id", Person.class);
        query.setParameter("number", number);
        PersonDTO personDTO = new PersonDTO(query.getSingleResult());
        return personDTO;
    }

    public Set<HobbyDTO> getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
        List<Hobby> hobbyList = query.getResultList();
        return HobbyDTO.getHobbyDTOs(hobbyList);
    }

    public PersonDTO deletePersonByID(long id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        try {
            em.getTransaction().begin();

            // removes phone numbers
            person.getPhones().forEach(phone -> {
                em.remove(phone);
            });

            // removes address, if it's the only one living there.
            Address addressToRemove = em.find(Address.class, person.getAddress().getId());
            if (addressToRemove.getPersons().size() <= 1) {
                em.remove(addressToRemove);
            }

            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
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
//            System.out.println(id);
//            em.getTransaction().begin();
//            address = em.find(Address.class, id);
//            em.remove(address);
////            em.clear();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//
//        return new AddressDTO(address);
//    }
}
