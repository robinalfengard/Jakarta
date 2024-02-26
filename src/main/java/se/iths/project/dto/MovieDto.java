package se.iths.project.dto;

import se.iths.project.entity.Movie;

public record MovieDto(String movieName, int releaseYear, String director, String firstRole, String movieCode){

    public static MovieDto map(Movie movie){
        return new MovieDto(movie.getMovieName(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getFirstRole(),
                movie.getMovieCode());
    }

    public static Movie map(MovieDto movieDto){
        var movie =  new Movie();
        movie.setMovieName(movieDto.movieName);
        movie.setReleaseYear(movieDto.releaseYear);
        movie.setDirector(movieDto.director);
        movie.setFirstRole(movieDto.firstRole);
        movie.setMovieCode(movieDto.movieCode);
        return movie;
    }
}
