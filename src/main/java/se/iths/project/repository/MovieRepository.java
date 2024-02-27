package se.iths.project.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import se.iths.project.dto.MovieDto;
import se.iths.project.entity.Movie;


import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class MovieRepository implements Serializable {

    @PersistenceContext(unitName = "mysql")
    EntityManager entityManager;

    public List<Movie> all() {
        return entityManager
                .createQuery("select m from Movie m", Movie.class)
                .getResultList();
    }

    @Transactional
    public Movie add(Movie movie) {
        entityManager.persist(movie);
        return movie;
    }

    public Movie findById(long id) {
        return entityManager.find(Movie.class, id);
    }

    @Transactional
    public void updateDB(long id, MovieDto movieDto) {
        if (movieDto == null) throw new IllegalArgumentException("Movie cannot be null");
        Movie movie = entityManager.find(Movie.class, id);
        if (movie == null) throw new IllegalArgumentException("This movie does not exist in the database");
        movie.setMovieName(movieDto.movieName());
        movie.setReleaseYear(movieDto.releaseYear());
        movie.setMovieCode(movieDto.movieCode());
        movie.setDirector(movieDto.director());
        movie.setFirstRole(movieDto.firstRole());
    }
}
