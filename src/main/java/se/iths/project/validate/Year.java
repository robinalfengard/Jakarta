package se.iths.project.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.lang.annotation.*;

@PositiveOrZero(message = "Year can not be negative")
@Max(value = 2024, message = "Year can not be in the future")
@Min(value = 1890, message = "Year can not be before 1890")
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Year.List.class)
public @interface Year {
    String message() default "Not a valid year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD,
            ElementType.FIELD,
            ElementType.ANNOTATION_TYPE,
            ElementType.CONSTRUCTOR,
            ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Year[] value();
    }
}
