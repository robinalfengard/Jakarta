package se.iths.restdemo.dto;

import se.iths.restdemo.entity.Movie;

public record PersonDto(String name, int age){

    public static PersonDto map(Movie movie){
        return new PersonDto(movie.getMovieName(), movie.getReleaseYear());
    }

    public static Movie map(PersonDto personDto){
        var person =  new Movie();
        person.setMovieName(personDto.name);
        person.setReleaseYear(personDto.age);
        return person;
    }
}
