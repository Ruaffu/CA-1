package facades;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import errorhandling.CityNotFoundException;
import errorhandling.HobbyNotFoundException;
import errorhandling.InvalidInputException;
import errorhandling.PersonNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeTest {

    private static EntityManagerFactory emf;
    private static Facade facade;
    Person p1;
    Person p2;

    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            CityInfo c1 = new CityInfo("8600", "Silkeborg");
            Address a1 = new Address("Chr. den 8. vej", "", c1);
            Hobby h1 = new Hobby("fodbold", "spark", new HashSet<>());
            Hobby h2 = new Hobby("musik", "lalala");
            p1 = new Person("Some txt", "More text", "some more text");
            p1.addPhone(new Phone("12345678", "fastnet"));
            p1.addHobby(h1);
            p1.setAddress(a1);
            CityInfo c2 = new CityInfo("4040", "Jyllinge");
            Address a2 = new Address("Mobo vej", "", c2);
            p2 = new Person("aaa", "bbb", "ccc");
            p2.addPhone(new Phone("87654321", "mobile"));
            p2.addHobby(h1);
            p2.addHobby(h2);
            p2.setAddress(a2);

            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(h1);
            em.persist(h2);

            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    void testGetPersonById() throws PersonNotFoundException
    {
        System.out.println("Testing getById()");
        String expected = p1.getFirstname();
        System.out.println(p1.getId());
        String actual = facade.getById(p1.getId()).getFirstname();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllPersons() {
        System.out.println("Testing getAll()");
        int expected = 2;
        int actual = facade.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void testCreatePerson() {
        System.out.println("Testing create()");
        Person p = new Person("test", "testy", "12345");
        p.setAddress(new Address("", "", new CityInfo("8600", "Silkeborg")));
        PersonDTO pd1 = new PersonDTO(p);
        facade.create(pd1);
        int expected = 3;
        int actual = facade.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void testGetPersonsByHobby() {
        System.out.println("Testing PersonsByHobby()");
        int expected = 2;
        int actual = facade.getPersonsByHobby("fodbold").size();
        assertEquals(expected, actual);
    }

    @Test
    void testGetPersonsByZipcode() throws InvalidInputException
    {
        System.out.println("Testing getPersonByZip()");
        int expected = 1;
        int actual = facade.getAllPersonsByZip("8600").size();
        System.out.println(facade.getAllPersonsByZip("8600").iterator().next().getFirstname());
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllCityInfos() throws CityNotFoundException
    {
        System.out.println("Testing getAllCityInfos()");
        int expected = 2;
        int actual = facade.getAllCityInfos().size();
        assertEquals(expected, actual);
    }

    @Test
    void testEditPerson() {
        System.out.println("Testing editPerson()");

        String expected = "bob";
        p1.setFirstname("bob");
        p1.setAddress(new Address("Danmarks gade 2","", new CityInfo("8600", "Silkeborg")));
        //p1.addHobby(new Hobby("musik", "lalala"));
        p1.getPhones().remove(p1.getPhones().iterator().next());
        p1.addPhone(new Phone("86865221","home"));
        p1.addPhone(new Phone("87654321","mobile"));
        p1.getHobbies().remove(p1.getHobbies().iterator().next());
        //System.out.println(p1.getHobbies().size());
        String actual = facade.editPerson(new PersonDTO(p1)).getFirstname();
        assertEquals(expected, actual);
    }

//    @Test
//    void testDeleteAddress() {
//        System.out.println("Testing deleteAddress()");
//        int expected = 0;
//        System.out.println(facade.deleteAddress(p1.getAddress().getId()));
//
//        int actual = facade.getAllPersonsByZip("8600").size();
//        assertEquals(expected, actual);
//    }

    @Test
    void testGetNumberOfPeopleWithGivenHobby(){
        System.out.println("Testing getNumberOfPeopleWithAHobby()");
        int expected = 2;
        int actual = facade.getNumberOfPeopleWithAHobby("fodbold");
        assertEquals(expected, actual);
    }

    @Test
    void testGetPersonByPhoneNumber() throws PersonNotFoundException
    {
        System.out.println("Testing getPersonByPhoneNumber()");
        String expected = "Some txt";
        String actual = facade.getPersonByPhoneNumber("12345678").getFirstname();
        assertEquals(expected, actual);
    }

    @Test
    void testDeletePersonById() throws PersonNotFoundException
    {
        System.out.println("Testing deletePersonById()");
        int expected = 1;
        PersonDTO deletedPerson = facade.deletePersonByID(p1.getId());
        int actual = facade.getAll().size();
        System.out.println(deletedPerson.getFirstname());
        assertEquals(expected, actual);

    }

    @Test
    void testGetAllHobbies() throws HobbyNotFoundException
    {
        System.out.println("Testing getAllHobbies()");
        int expected = 1;
        int actual = facade.getAllHobbies().size();;
        assertEquals(expected, actual);
    }
}
