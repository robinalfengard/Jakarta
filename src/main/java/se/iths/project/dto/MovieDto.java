package se.iths.project.dto;

import jakarta.validation.constraints.NotEmpty;
import se.iths.project.entity.Movie;
import se.iths.project.validate.ReleaseYear;
import java.util.UUID;

public record MovieDto(@NotEmpty String movieName, @ReleaseYear int releaseYear,
                       @NotEmpty String director, @NotEmpty String firstRole, UUID uuid){


    public static MovieDto map(Movie movie){
        return new MovieDto(
                movie.getMovieName(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getFirstRole(),
                movie.getUuid());

    }

    public static Movie map(MovieDto movieDto){
        var movie =  new Movie();
        movie.setMovieName(movieDto.movieName);
        movie.setReleaseYear(movieDto.releaseYear);
        movie.setDirector(movieDto.director);
        movie.setFirstRole(movieDto.firstRole);
        movie.setUuid(movieDto.uuid);
        return movie;
    }

}
