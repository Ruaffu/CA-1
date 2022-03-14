package rest;

import entities.*;
import io.restassured.http.ContentType;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest
{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;

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

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            p1 = new Person("Some txt", "More text", "some more text", new HashSet<>(),
                    new Address("Chr. den 8. vej", "",
                            new CityInfo("8600", "Silkeborg")), new HashSet<>());
            p1.addPhone(new Phone("12345678", "fastnet"));
            Hobby h1 = new Hobby("fodbold", "spark", new HashSet<>());
            p1.addHobby(h1);

            p2 = new Person("aaa", "bbb", "ccc", new HashSet<>(),
                    new Address("Mobo vej", "",
                            new CityInfo("4040", "Jyllinge")), new HashSet<>());
            p2.addPhone(new Phone("87654321", "mobile"));
            p2.addHobby(h1);

            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void testGetPersonById(){
        given().contentType(MediaType.APPLICATION_JSON)
                .get("/person/{id}", p1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo(p1.getFirstname()))
                .body("lastname", equalTo(p1.getLastname()));
    }

    @Test
    void testGetAllPeople(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/all")
                .then()
                .assertThat()
                .statusCode(200)
                .log().body();
    }

    @Test
    void testGetPersonByHobby(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/hobby/{hobby}")
                .then()
                .assertThat()
                .statusCode(200)
                .log().body();
    }

    @Test
    void testGetPersonByZipcode(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/zipcode/{zipcode}")
                .then()
                .assertThat()
                .statusCode(200)
                .log().body();
    }

    @Test
    void testGetPersonByPhone(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/phone/{phone}")
                .then()
                .assertThat()
                .statusCode(200)
                .log().body();
    }

    @Test
    void testDeletePersonByID(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", p2.getId())
                .delete("/person/delete/{id}")
                .then()
                .statusCode(200)
                .body("id",equalTo(p2.getId()));
    }


}
