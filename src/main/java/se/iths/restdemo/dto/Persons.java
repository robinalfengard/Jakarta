package se.iths.restdemo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record Persons(List<PersonDto> personDtos, LocalDateTime updated){}
