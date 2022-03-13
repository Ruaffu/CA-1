package rest;

import dtos.PersonDTO;
import entities.*;
import io.restassured.path.json.JsonPath;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2, p3, p4, p5, p6;
    private static Phone ph1, ph2, ph3, ph4;
    private static Address a1, a2, a3, a4, a5;
    private static CityInfo c1, c2, c3, c4, c5;
    private static Hobby h1, h2, h3, h4;
    private static PersonDTO p1DTO, p2DTO, p3DTO, p4DTO, p5DTO;
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    /**
     * // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
     * //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
     *
     * @BeforeEach public void setUp() {
     * EntityManager em = emf.createEntityManager();
     * try {
     * em.getTransaction().begin();
     * em.createNamedQuery("Person.deleteAllRows").executeUpdate();
     * p1 = new Person("Some txt", "More text", "some more text", new HashSet<>(),
     * new Address("Chr. den 8. vej", "",
     * new CityInfo("8600", "Silkeborg")), new HashSet<>());
     * p1.addPhone(new Phone("12345678", "fastnet"));
     * Hobby h1 = new Hobby("fodbold", "spark", new HashSet<>());
     * p1.addHobby(h1);
     * <p>
     * p2 = new Person("aaa", "bbb", "ccc", new HashSet<>(),
     * new Address("Mobo vej", "",
     * new CityInfo("4040", "Jyllinge")), new HashSet<>());
     * p2.addPhone(new Phone("87654321", "mobile"));
     * p2.addHobby(h1);
     * <p>
     * em.persist(p1);
     * em.persist(p2);
     * <p>
     * em.getTransaction().commit();
     * } finally {
     * em.close();
     * }
     * }
     * @Test void testGetPersonById(){
     * given().contentType(MediaType.APPLICATION_JSON)
     * .get("/person/{id}", p1.getId())
     * .then()
     * .assertThat()
     * .statusCode(200)
     * .body("firstName", equalTo(p1.getFirstname()))
     * .body("lastName", equalTo(p1.getLastname()));
     * }
     **/

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();


        ph1 = new Phone("11111111", "Mobil");
        ph2 = new Phone("22222222", "Mobil");
        ph3 = new Phone("33333333", "Fastnet");
        ph4 = new Phone("44444444", "Mobil");

        c1 = new CityInfo("4040", "Jyllinge");
        c2 = new CityInfo("2800", "Kongens Lyngby");
        c3 = new CityInfo("2650", "Hvidovre");
        c4 = new CityInfo("8210", "Aarhus V");

        a1 = new Address("Jyllingevej", "1", c1);
        a2 = new Address("Lyngbyvej", "2", c2);
        a3 = new Address("Hvidovrevej", "3", c3);
        a4 = new Address("Aarhusvej", "4", c4);

        h1 = new Hobby("Fodbold", "https://en.wikipedia.org/wiki/Soccer");
        h2 = new Hobby("Videospil", "https://en.wikipedia.org/wiki/Video_gaming");
        h3 = new Hobby("Film", "https://en.wikipedia.org/wiki/Movies");
        h4 = new Hobby("Bordfodbold", "https://en.wikipedia.org/wiki/Table_football");

        //First person has 1 phone number, an address and 1 hobby
        p1 = new Person("Alice", "A.", "alice@awesome.com");
        p1.addPhone(ph1);
        p1.setAddress(a1);
        p1.addHobby(h1); //sharing with person 4 (dave)

        //Second person has more than one phone number and hobby and an address
        p2 = new Person("Bob", "B.", "bob@bad.com");
        p2.addPhone(ph2);
        p2.addPhone(ph3);
        p2.setAddress(a2);
        p2.addHobby(h2);
        p2.addHobby(h3);
        p2.addHobby(h4); //sharing with person 4 (dave)

        //Third person has 1 phone number and an address
        p3 = new Person("Charlie", "C.", "charlie@charming.com");
        p3.addPhone(ph4);
        p3.setAddress(a3);

        //Fourth person has 1 hobby and an address
        p4 = new Person("Dave", "D.", "dave@dark.com");
        p4.setAddress(a4);
        p4.addHobby(h1); //sharing with person 1 (Alice)
        p4.addHobby(h4); //sharing with person 2 (bob)

        //Fifth person has only an address
        p5 = new Person("Eva", "E.", "eva@expert.com");
        p5.setAddress(a3); //sharing with person 3 (charlie)

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(ph1);
            em.persist(ph2);
            em.persist(ph3);
            em.persist(ph4);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(h4);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(a4);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        p1DTO = new PersonDTO(p1);
        p2DTO = new PersonDTO(p2);
        p3DTO = new PersonDTO(p3);
        p4DTO = new PersonDTO(p4);
        p5DTO = new PersonDTO(p5);

    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server up");
        given()
                .when()
                .get("/person")
                .then()
                .statusCode(200);
    }

    @Test
    void getPersonById() {
        System.out.println("Testing to get person by id");
        PersonDTO actualPersonDTO;
        actualPersonDTO = given()
                .contentType("application/json")
                .when()
                .get("/person/" + p1.getId())
                .then()
                .extract().body().jsonPath().getObject("", PersonDTO.class);
        assertThat(actualPersonDTO, equalTo(p1DTO));
    }

    @Test
    void getAllPersons() throws Exception {
        System.out.println("Testing to get all persons");
        List<PersonDTO> actualPersonsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/person/all")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);
        assertThat(actualPersonsDTOs, containsInAnyOrder(p1DTO, p2DTO, p3DTO, p4DTO, p5DTO));
    }

    @Test
    void getPersonByHobby() {
        System.out.println("Testing to get persons by hobby");
        List<PersonDTO> actualPersonsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/person/hobby/" + h1.getName())
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);
        assertThat(actualPersonsDTOs, containsInAnyOrder(p1DTO, p4DTO));
    }

    @Test
    void getPersonsByZipcode() {
        System.out.println("Testing to get persons by zipcode");
        List<PersonDTO> actualPersonsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/person/zipcode/" + c3.getZipcode())
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);
        assertThat(actualPersonsDTOs, containsInAnyOrder(p3DTO, p5DTO));
    }

    @Test
    void getPersonByPhone() {
        System.out.println("Testing to get persons by phone number");
        PersonDTO actualPersonDTO = given()
                .contentType("application/json")
                .when()
                .get("/person/phone/" + ph1.getNumber())
                .then()
                .extract().body().jsonPath().getObject("", PersonDTO.class);
        assertThat(actualPersonDTO, equalTo(p1DTO));
    }

    //TODO:make test work
    @Test
    void deletePersonById() {
        System.out.println("Testing to delete and get person by id");
        PersonDTO actualPersonDTO = given()
                .contentType("application/json")
                .when()
                .get("/person/delete/")
                .then()
                .extract().body().jsonPath().getObject("", PersonDTO.class);
        assertThat(actualPersonDTO, equalTo(p1DTO));
    }


    //TODO: make test
    @Test
    void createPerson() {
        System.out.println("Testing to create a person");


    }

    //TODO: make test work
    @Test
    void editPersonById() {
        System.out.println("Testing to edit a person");
        PersonDTO actualPersonDTO = given()
                .contentType("application/json")
                .when()
                .get("/edit/" + p1.getId())
                .then()
                .extract().body().jsonPath().getObject("", PersonDTO.class);
        assertThat(actualPersonDTO, equalTo(p1DTO));
    }


}
