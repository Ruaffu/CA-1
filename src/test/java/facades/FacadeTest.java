package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
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
            p1 = new Person("Some txt", "More text", "some more text", new HashSet<>(),new Address("Chr. den 8. vej", "", new CityInfo("8600", "Silkeborg")), new HashSet<>());
            p1.addPhone(new Phone("12345678","fastnet"));
            Hobby h =new Hobby("fodbold", "spark", new HashSet<>());
            p1.addHobby(h);

            p2 = new Person("aaa", "bbb", "ccc");

            em.persist(h);

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
    void testGetPersonById()
    {
        System.out.println("Testing getById()");
        String expected = p1.getFirstname();
        String actual = facade.getById(p1.getId()).getFirstname();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllPersons()
    {
        System.out.println("Testing getAll()");
        int expected = 2;
        int actual = facade.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void testCreatePerson()
    {
        System.out.println("Testing create()");
        PersonDTO pd1 = new PersonDTO("test", "testy","12345");
        facade.create(pd1);
        int expected = 3;
        int actual = facade.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void testGetPersonsByHobby() {
        System.out.println("Testing PersonsByHobby()");
        HobbyDTO hobbyDTO = new HobbyDTO("fodbold","spark",new HashSet<>());
        hobbyDTO.getPersons().add(p1);
        int expected = 1;
        int actual = facade.getPersonsByHobby(hobbyDTO).size();
        assertEquals(expected, actual);
    }
}
