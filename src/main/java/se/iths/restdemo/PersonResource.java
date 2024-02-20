package se.iths.restdemo;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Path("persons")
public class PersonResource {

    //Todo: Add pagination
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Persons all() {
        return new Persons(
                List.of(new Person("Martin", 45), new Person("Pippi", 17)),
                LocalDateTime.now());
    }

    //Don't use primary key as id. Use nanoid or UUID
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Person one(@PathParam("id") String id){
        return new Person("Martin", 45);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(Person person){
        //Save to database
       return Response.created(
               //Ask Jakarta application server for hostname and url path
                URI.create("http://localhost:8080/restdemo-1.0-SNAPSHOT/api/persons/id"))
                .build();
    }








}
