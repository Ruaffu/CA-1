package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import utils.EMF_Creator;
import facades.Facade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    // getpersonbyphonenumber, deletepersonbyid

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") long id) {
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
    public Response getPersonsByZipcode(@PathParam("zipcode") String zipcode) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAllPersonsByZip(zipcode)))
                .build();
    }

    @GET
    @Path("phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByPhone(@PathParam("phone") String phone) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getPersonByPhoneNumber(phone)))
                .build();
    }


}
