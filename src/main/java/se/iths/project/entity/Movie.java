package se.iths.project.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieCode;
    private String movieName;
    private String director;
    private String firstRole;
    private int releaseYear;

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getReleaseYear() == movie.getReleaseYear() && Objects.equals(getId(), movie.getId()) && Objects.equals(getMovieCode(), movie.getMovieCode()) && Objects.equals(getMovieName(), movie.getMovieName()) && Objects.equals(getDirector(), movie.getDirector()) && Objects.equals(getFirstRole(), movie.getFirstRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovieCode(), getMovieName(), getDirector(), getFirstRole(), getReleaseYear());
    }
}
