package se.iths.project.resource;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.project.dto.MovieDto;
import se.iths.project.dto.Movies;
import se.iths.project.repository.MovieRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public Response create(@Valid MovieDto movieDto){
        var m = movieRepository.add(MovieDto.map(movieDto));
       return Response.created(
                URI.create("http://localhost:8080/api/movies/" + m.getId()))
                .build();
    }

    @DELETE
    @Path("{uuid}")
    public Response deleteByUuid(@PathParam("uuid") UUID uuid) {
        try {
            var movie = movieRepository.findById(movieRepository.getIdByUuid(uuid));
            if (movie == null)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Movie not found")
                        .build();
            movieRepository.deleteByUuid(movie.getUuid().toString());
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, MovieDto movieDto) {
        if (id == 0) throw new BadRequestException("Id cannot be zero");
        movieRepository.updateDB(id, movieDto);
        return Response.
                created(URI.create("http://localhost:8080/api/movies/" + id))
                .build();
    }

}
