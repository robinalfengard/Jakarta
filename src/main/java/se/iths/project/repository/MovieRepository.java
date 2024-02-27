package se.iths.project.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import se.iths.project.dto.MovieDto;
import se.iths.project.entity.Movie;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;

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

    public Long getIdByUuid(UUID uuid){
        String jpql = "SELECT m.id from Movie m where m.uuid = :uuid";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("uuid", uuid);
        if(query.getResultList().isEmpty())
            throw new NoResultException("No movie with that UUID found");
        return (Long) query.getSingleResult();
    }

    @Transactional
    public void deleteByUuid(String uuidString){
        UUID uuid = UUID.fromString(uuidString);
        String jpql = "DELETE FROM Movie WHERE uuid = :uuid";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("uuid", uuid);
        query.executeUpdate();
    }

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
    @Transactional
    public void updateDB(long id, MovieDto movieDto){
        Movie movie= entityManager.find(Movie.class,id);
        movie.setMovieName(movieDto.movieName());
        movie.setReleaseYear(movieDto.releaseYear());
        movie.setMovieCode(movieDto.movieCode());
        movie.setDirector(movieDto.director());
        movie.setFirstRole(movieDto.firstRole());
    }

}
