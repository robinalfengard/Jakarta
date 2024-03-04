package se.iths.project.resource;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.iths.project.dto.Movies;
import se.iths.project.entity.Movie;
import java.io.File;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
@Testcontainers
public class MovieIT {
    @Container
    public static ComposeContainer environment =
            new ComposeContainer(new File("src/test/resources/compose-test.yml"))
                    .withExposedService("wildfly", 8080, Wait.forHttp("/api/movies")
                            .forStatusCode(200))
                    .withLocalCompose(true);

    static String host;
    static Movies movies;
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
    @DisplayName("Request for read respons status code 200")
    void requestForReadResponsStatusCode200() {
        movies = RestAssured.get("/movies").then()
                .statusCode(200)
                .extract()
                .as(Movies.class);
        movies.movieDtos().clear();
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
                movies = RestAssured.get("/movies").then()
                .extract()
                .as(Movies.class);
        UUID uuid= movies.movieDtos().get(0).uuid();

        RestAssured.given()
                .when()
                .delete("/movies/" + uuid)
                .then()
                .statusCode(200);
        movies.movieDtos().clear();
    }
    @Test
    @DisplayName("Update movie with put updates created movie")
    void updateUpdatesChosenMovie() {
        movies.movieDtos().clear();
        String movie = "{"
                + "\"movieName\": \"Oppenheimer\","
                + "\"releaseYear\": 2023,"
                + "\"director\": \"Christopher Nolan\","
                + "\"firstRole\": \"Cillian Murphy\""
                + "}";
        String requestBody = "{"
                + "\"movieName\": \"Updated Movie\","
                + "\"releaseYear\": 2024,"
                + "\"director\": \"New Director\","
                + "\"firstRole\": \"New Actor\""
                + "}";
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movie)
                .when()
                .post("/movies")
                .then()
                .statusCode(201);
                movies = RestAssured.get("/movies").then()
                .extract()
                .as(Movies.class);
        int id = movies.movieDtos().size();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .put("/movies/" + id)
                .then()
                .statusCode(201);
        Movie movie1 = RestAssured.get("/movies/" + id).as(Movie.class);


        assertEquals("Updated Movie",movie1.getMovieName());
        movies.movieDtos().clear();
    }
}
