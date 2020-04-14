package dev.abgeo.secretic.validator;

import dev.abgeo.secretic.form.UserForm;
import dev.abgeo.secretic.model.User;
import dev.abgeo.secretic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserFormValidator(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        User userFromDatabase = userService.findByUsername(userForm.getUsername());

        if (null == userForm.getUsername() || userForm.getUsername().length() < 4 || userForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username", new Object[] {4, 32}, null);
            return;
        }

        if (null != userFromDatabase && !userFromDatabase.getId().equals(userForm.getId())) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            return;
        }

        if (null != userForm.getPasswordOld() && null != userFromDatabase) {
            if (!bCryptPasswordEncoder.matches(userForm.getPasswordOld(), userFromDatabase.getPassword())) {
                errors.rejectValue("passwordOld", "Diff.userForm.passwordOld");
                return;
            }

            if (bCryptPasswordEncoder.matches(userForm.getPassword(), userFromDatabase.getPassword())) {
                errors.rejectValue("password", "Equals.userForm.password.old");
                return;
            }
        }

        if (null == userForm.getPassword() || userForm.getPassword().length() < 8) {
            errors.rejectValue("password", "Size.userForm.password", new Object[] {8}, null);
            return;
        }

        if (!userForm.getPasswordConfirm().equals(userForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

}
