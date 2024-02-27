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
}
