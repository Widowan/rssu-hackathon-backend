package com.hypnotoad.configurations.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpelAssertValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpelAssert {
    String message() default "Value couldn't be validated";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String expr();
}
