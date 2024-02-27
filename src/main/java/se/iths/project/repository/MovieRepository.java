package se.iths.project.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
    public Long getIdByMovieKode(String movieKode){
        String jpql = "SELECT m.id from Movie m where m.movieKode = :movieKode";
        Query query = entityManager.createQuery(jpql);
        if(query.getResultList().isEmpty())
            throw new NoResultException("No movie with that title found");
        query.setParameter("movieKode", movieKode);
        return (Long) query.getSingleResult();
    }

    @Transactional
    public void deleteByMovieKode(String movieKode){
        String jpql = "DELETE FROM Movie WHERE movieKode = :movieKode";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("movieKode", movieKode);
        query.executeUpdate();
    }
}
