package cap.libapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> 
{
	/* Implementation for custom validation annotation for password for Library app.
	 * Author - Shrey.
	 */

    @Override
    public void initialize(ValidPassword arg0) {
      //For intialisation.
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context)     //Method to validate password.
    {
        	PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),													//Defines Length.
            new CharacterRule(EnglishCharacterData.UpperCase, 1),					//At least one Uppercase.
            new CharacterRule(EnglishCharacterData.LowerCase, 1),					//At least one Lowercase.
            new CharacterRule(EnglishCharacterData.Digit, 1),						//At least one Digit.
            new CharacterRule(EnglishCharacterData.Special, 1),						//At least one Special Character.
            new WhitespaceRule()													//No whitespace allowed.
        	));
        	
        	RuleResult result = validator.validate(new PasswordData(password));
        	if (result.isValid()) return true;										//Valid Password.
 
        	List<String> messages = validator.getMessages(result);					//Invalid Password message collection.
        	String messageTemplate = messages.stream().collect(Collectors.joining(""));
        	context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        	return false;
    }

}
