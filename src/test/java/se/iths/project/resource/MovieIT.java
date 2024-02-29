package se.iths.project.resource;

import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import se.iths.project.dto.Movies;
import se.iths.project.entity.Movie;
import se.iths.project.repository.MovieRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Testcontainers
public class MovieIT {
    @Container
    public static ComposeContainer environment =
            new ComposeContainer(new File("src/test/resources/compose-test.yml"))
                    .withExposedService("wildfly", 8080, Wait.forHttp("/api/movies")
                            .forStatusCode(200))
                    .withLocalCompose(true);


    static String host;
    Movies movies;
    static int port;

    @BeforeAll
    static void beforeAll() {
        host = environment.getServiceHost("wildfly", 8080);
        port = environment.getServicePort("wildfly", 8080);
    }

    @BeforeEach
    void before() {
        RestAssured.baseURI = "http://" + host + "/api";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Request for read respons status code 200")
    void requestForReadResponsStatusCode200() {
        movies = RestAssured.get("/movies").then()
                .statusCode(200)
                .extract()
                .as(Movies.class);
        movies.movieDtos().clear();;
    }

    @Test
    @DisplayName("Request for create response Status code 201")
    void requestForCreateResponseStatusCode201() {
        String requestBody = "{"
                + "\"movieName\": \"Oppenheimer\","
                + "\"releaseYear\": 2023,"
                + "\"director\": \"Christopher Nolan\","
                + "\"firstRole\": \"Cillian Murphy\""
                + "}";

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post("/movies")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Update movie with put updates created movie")
    void updateUpdatesChosenMovie() {
        // Create movie with title unupdated movie
        String createdMovie = "{"
                + "\"movieName\": \"Unupdated Movie\","
                + "\"releaseYear\": 2024,"
                + "\"director\": \"New Director\","
                + "\"firstRole\": \"New Actor\""
                + "}";
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdMovie)
                .when()
                .post("/movies")
                .then()
                .statusCode(201);


        // Update movie title with put
        String requestBody = "{"
                + "\"movieName\": \"Updated Movie\","
                + "\"releaseYear\": 2024,"
                + "\"director\": \"New Director\","
                + "\"firstRole\": \"New Actor\""
                + "}";


        Movie unUpdatedMovie = RestAssured.get("/movies/1").as(Movie.class);
        Long id = unUpdatedMovie.getId();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/movies/" + id)
                .then()
                .statusCode(201);

        // get first movie and test that the title is updated
        Movie movie = RestAssured.get("/movies/1").as(Movie.class);
        String expectedUpdatedMovieTitle = "Updated Movie";
        assertEquals(expectedUpdatedMovieTitle, movie.getMovieName());

    }

    @Test
    @DisplayName("Attempt to delete a non-existing movie returns status 404")
    void deleteNonExistingMovie_ReturnsNotFound() {

        UUID randomUUID = UUID.randomUUID();

        // Attempt to delete the non-existing movie
        RestAssured.given()
                .when()
                .delete("/movies/" + randomUUID.toString())
                .then()
                .statusCode(404); // Expect 404 Not Found as the movie does not exist
    }



    @Test
    @DisplayName("Request for delete response Status code 200")
    void requestForDeleteResponseStatusCode200() {
        String requestBody = "{"
                + "\"movieName\": \"Oppenheimer\","
                + "\"releaseYear\": 2023,"
                + "\"director\": \"Christopher Nolan\","
                + "\"firstRole\": \"Cillian Murphy\""
                + "}";

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post("/movies")
                .then()
                .statusCode(201);
        Movies movies = RestAssured.get("/movies").then()
                .statusCode(200)
                .extract()
                .as(Movies.class);
        UUID uuid= movies.movieDtos().get(0).uuid();

        RestAssured.given()
                .when()
                .delete("/movies/" + uuid)
                .then()
                .statusCode(204);
        movies.movieDtos().clear();
    }
}
