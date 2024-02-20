package se.iths.restdemo;

import java.time.LocalDateTime;
import java.util.List;

public record Persons(List<Person> persons, LocalDateTime updated){}
