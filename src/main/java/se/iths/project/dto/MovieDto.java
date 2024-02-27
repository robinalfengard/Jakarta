package se.iths.project.dto;

import se.iths.project.entity.Movie;

import java.util.UUID;

public record MovieDto(String movieName, int releaseYear, String director, String firstRole, String movieKode, UUID uuid){

    public static MovieDto map(Movie movie){
        return new MovieDto(
                movie.getMovieName(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getFirstRole(),
                movie.getMovieKode(),
                movie.getUuid());
    }

    public static Movie map(MovieDto movieDto){
        var movie =  new Movie();
        movie.setMovieName(movieDto.movieName);
        movie.setReleaseYear(movieDto.releaseYear);
        movie.setDirector(movieDto.director);
        movie.setFirstRole(movieDto.firstRole);
        movie.setMovieKode(movieDto.movieKode);
        movie.setUuid(movieDto.uuid);
        return movie;
    }

}
