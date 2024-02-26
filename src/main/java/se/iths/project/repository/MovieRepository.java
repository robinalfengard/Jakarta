package se.iths.project.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
    public Movie update(Movie existingMovie) {
//        if (existingMovie == null || existingMovie.getId() == null){
//            throw new IllegalArgumentException("Movie cannot be null and must have an id");
//        }
        existingMovie = entityManager.find(Movie.class, existingMovie.getId());
//        if (existingMovie == null) {
//            throw new IllegalArgumentException("Movie with id " + existingMovie.getId() + " does not exist");
//        }

        Movie updatedMovie = entityManager.merge(existingMovie);

        return updatedMovie;
    }

}
