package se.iths.project.dto;

import jakarta.validation.constraints.NotEmpty;
import se.iths.project.entity.Movie;

public record MovieDto(@NotEmpty String movieName, int releaseYear, String director, String firstRole, String movieKode){

    public static MovieDto map(Movie movie){
        return new MovieDto(movie.getMovieName(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getFirstRole(),
                movie.getMovieKode());
    }

    public static Movie map(MovieDto movieDto){
        var movie =  new Movie();
        movie.setMovieName(movieDto.movieName);
        movie.setReleaseYear(movieDto.releaseYear);
        movie.setDirector(movieDto.director);
        movie.setFirstRole(movieDto.firstRole);
        movie.setMovieKode(movieDto.movieKode);
        return movie;
    }
}
