package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import errorhandling.CityNotFoundException;
import errorhandling.InvalidInputException;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;
import facades.Facade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final Facade FACADE = Facade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


    //TODO:
    // editperson, createperson,
    // deletepersonbyid

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") long id) throws PersonNotFoundException
    {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getById(id)))
                .build();
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAll()))
                .build();
    }

    @GET
    @Path("/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByHobby(@PathParam("hobby") String hobby) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getPersonsByHobby(hobby)))
                .build();
    }

    @GET
    @Path("/zipcode/{zipcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByZipcode(@PathParam("zipcode") String zipcode) throws InvalidInputException
    {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAllPersonsByZip(zipcode)))
                .build();
    }

    @GET
    @Path("/phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("phone") String phone) throws PersonNotFoundException
    {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getPersonByPhoneNumber(phone)))
                .build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deletePersonById(@PathParam("id") Long id) throws PersonNotFoundException
    {
        PersonDTO personDTO = FACADE.deletePersonByID(id);
        return Response
                .ok()
                .entity(GSON.toJson(personDTO))
                .build();
    }

    //TODO:check create method
    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String content) {
        PersonDTO personDTO = GSON.fromJson(content, PersonDTO.class);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.create(personDTO)))
                .build();
    }

    //TODO: check edit method
    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPersonById(@PathParam("id") long id, String content) {
        PersonDTO personDTO = GSON.fromJson(content, PersonDTO.class);
        PersonDTO editedPerson = FACADE.editPerson(personDTO);
        return Response
                .ok()
                .entity(GSON.toJson(editedPerson))
                .build();
    }

}
