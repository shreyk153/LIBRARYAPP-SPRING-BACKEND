package cap.libapp.util;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = RoleConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole 						//Custom annotation for validating the role entered.
{
	/* Custom validation annotation for role for Library app.
	 * Author - Shrey.
	 */
    String message() default "Invalid Role Entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
