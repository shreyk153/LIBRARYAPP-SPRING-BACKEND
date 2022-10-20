package cap.libapp.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleConstraintValidator implements ConstraintValidator<ValidRole, String>
{
	/* Implementation for custom validation annotation for role for Library app.
	 * Author - Shrey.
	 */
	    @Override
	    public void initialize(ValidRole arg0) {
	    		//For initialisation.
     }

	    @Override
	    public boolean isValid(String role, ConstraintValidatorContext context) 		//Method to validate role.
	    {
	    	String[] validRoles = {"ROLE_USER","ROLE_ADMIN"};
	    	for(String s:validRoles)
	    	{
	    		if(s.equals(role)) return true;								//If Role is one of the above, accepted.
	    	}
	    	
	    	context.buildConstraintViolationWithTemplate("Invalid Role")	//Message for invalid role.
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
	    	return false;
	    	
	    }
}
