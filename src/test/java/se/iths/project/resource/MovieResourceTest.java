package se.iths.project.resource;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.iths.project.dto.MovieDto;
import se.iths.project.dto.Movies;
import se.iths.project.entity.Movie;
import se.iths.project.repository.MovieRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MovieResourceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieResource movieResource;

    private MovieDto movieDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieDto = new MovieDto("movie", 2015, "Director", "Actor", UUID.randomUUID());
    }

    @Test
    void verifyAllMethodAddsMovieDtosToTheMovieList() {
        List<Movie> mockMovies = List.of(new Movie(), new Movie());
        when(movieRepository.all()).thenReturn(mockMovies);

        Movies movieList = movieResource.all();

        assertNotNull(movieList);
        assertEquals(2, movieList.movieDtos().size());
        verify(movieRepository).all();
    }

    @Test
    void testOneMovieSuccess() {
        long id = 1L;
        Movie mockMovie = new Movie();
        when(movieRepository.findById(id)).thenReturn(mockMovie);

        MovieDto result = movieResource.one(id);

        assertNotNull(result);
        verify(movieRepository).findById(id);
    }

    @Test
    void testOneMovieNotFound() {
        long id = 2L;
        when(movieRepository.findById(id)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> movieResource.one(id));
        verify(movieRepository).findById(id);
    }

    @Test
    void testCreateMovie() {

        Movie savedMovie = new Movie();
        when(movieRepository.add(any(Movie.class))).thenReturn(savedMovie);

        Response response = movieResource.create(movieDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(movieRepository).add(any(Movie.class));
    }

    @Test
    void testDeleteMovieSuccess() {
        UUID uuid = UUID.randomUUID();
        Movie mockMovie = new Movie();
        mockMovie.setUuid(uuid);
        when(movieRepository.findById(anyLong())).thenReturn(mockMovie);

        Response response = movieResource.deleteById(uuid);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(movieRepository).deleteByUuid(uuid.toString());
    }

    @Test
    void testDeleteMovieNotFound() {
        UUID uuid = UUID.randomUUID();
        when(movieRepository.findById(anyLong())).thenReturn(new Movie());

        Response response = movieResource.deleteById(uuid);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateMovieSuccess() {
        long id = 1L;

        doNothing().when(movieRepository).updateDB(eq(id), any(MovieDto.class));

        Response response = movieResource.update(id, movieDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(movieRepository).updateDB(eq(id), any(MovieDto.class));
    }

    @Test
    void testUpdateMovieIdZero() {
        assertThrows(BadRequestException.class, () -> movieResource.update(0, new MovieDto("Movie2", 2023, "Director2", "Actor2", UUID.randomUUID())));
    }
}