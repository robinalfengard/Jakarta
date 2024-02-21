package se.iths.restdemo.dto;

import se.iths.restdemo.entity.Person;

public record PersonDto(String name, int age){

    public static PersonDto map(Person person){
        return new PersonDto(person.getName(),person.getAge());
    }

    public static Person map(PersonDto personDto){
        var person =  new Person();
        person.setName(personDto.name);
        person.setAge(personDto.age);
        return person;
    }
}
