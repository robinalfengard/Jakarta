package se.iths.project.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MovieDtoTest {
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Test valid movie")
    void testValidMovie() {
        MovieDto movie=new MovieDto("Oppenheimer",2023,
                "Christopher Nolan","Cillian Murphy", UUID.randomUUID());
        var violations = validator.validate(movie);
        assertEquals(0, violations.size());
    }

    @Test
    @DisplayName("Test invalid movie")
    void testInvalidMovie() {
        MovieDto movie=new MovieDto("Oppenheimer",2123,
                "Christopher Nolan","Cillian Murphy", UUID.randomUUID());
        var violations = validator.validate(movie);
        assertEquals(1, violations.size());
        assertEquals("Year can not be in the future", violations.iterator().next().getMessage());
    }
    @Test
    @DisplayName("Test empty String Should Return Error Message")
    void testEmptyStringShouldReturnErrorMessage(){
        MovieDto movie=new MovieDto("",2001,
                "Christopher Nolan",
                "Cillian Murphy",
                UUID.randomUUID());
        var violations = validator.validate(movie);
        assertEquals(1, violations.size());
        assertEquals("must not be empty", violations.iterator().next().getMessage());
    }

}