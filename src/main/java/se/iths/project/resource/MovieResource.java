package se.iths.project.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.project.dto.MovieDto;
import se.iths.project.dto.Movies;
import se.iths.project.entity.Movie;
import se.iths.project.repository.MovieRepository;

import java.net.URI;
import java.time.LocalDateTime;

@Path("movies")
public class MovieResource {

    private MovieRepository movieRepository;

    public MovieResource() {
    }

    @Inject
    public MovieResource(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Movies all() {
        return new Movies(
                movieRepository.all().stream().map(MovieDto::map).toList(),
                LocalDateTime.now());
    }

    //Don't use primary key as id. Use nanoid or UUID
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public MovieDto one(@PathParam("id") long id) {
        var movie = movieRepository.findById(id);
        if (movie == null)
            throw new NotFoundException("Invalid id " + id);
        return MovieDto.map(movie);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response create(MovieDto movieDto) {
        //Save to database
        var m = movieRepository.add(MovieDto.map(movieDto));

        return Response.created(
                        //Ask Jakarta application server for hostname and url path
                        URI.create("http://localhost:8080/api/movies/" + m.getId()))
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, MovieDto movieDto) {
        if (id == 0) throw new IllegalArgumentException("Id cannot be zero");
        movieRepository.updateDB(id, movieDto);
        return Response.
                created(URI.create("http://localhost:8080/api/movies/test" + id))
                .build();
    }

}
