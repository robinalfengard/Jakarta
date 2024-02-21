package se.iths.restdemo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.restdemo.dto.PersonDto;
import se.iths.restdemo.dto.Persons;
import se.iths.restdemo.repository.PersonRepository;

import java.net.URI;
import java.time.LocalDateTime;

@Path("persons")
public class PersonResource {

    private PersonRepository personRepository;

    public PersonResource() {
    }

    @Inject
    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Persons all() {
        return new Persons(
                personRepository.all().stream().map(PersonDto::map).toList(),
                LocalDateTime.now());
    }

    //Don't use primary key as id. Use nanoid or UUID
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public PersonDto one(@PathParam("id") long id){
        var person = personRepository.findById(id);
        if( person == null)
            throw new NotFoundException("Invalid id " + id);
        return PersonDto.map(person);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(PersonDto personDto){
        //Save to database
        var p = personRepository.add(PersonDto.map(personDto));

       return Response.created(
               //Ask Jakarta application server for hostname and url path
                URI.create("http://localhost:8080/api/persons/" + p.getId()))
                .build();
    }
}
