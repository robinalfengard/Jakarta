package se.iths.project.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieName;
    private String director;
    private String firstRole;
    private int releaseYear;
    private UUID uuid;


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFirstRole() {
        return firstRole;
    }

    public void setFirstRole(String firstRole) {
        this.firstRole = firstRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getReleaseYear() == movie.getReleaseYear() && Objects.equals(getId(), movie.getId()) && Objects.equals(getMovieName(), movie.getMovieName()) && Objects.equals(getDirector(), movie.getDirector()) && Objects.equals(getFirstRole(), movie.getFirstRole()) && Objects.equals(getUuid(), movie.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovieName(), getDirector(), getFirstRole(), getReleaseYear(), getUuid());
    }
}
