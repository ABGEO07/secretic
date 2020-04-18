package dev.abgeo.secretic.controller;

import dev.abgeo.secretic.form.UserForm;
import dev.abgeo.secretic.model.Post;
import dev.abgeo.secretic.model.User;
import dev.abgeo.secretic.repository.PostRepository;
import dev.abgeo.secretic.service.UserService;
import dev.abgeo.secretic.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserController {

    private final UserService userService;

    private final UserFormValidator userFormValidator;

    @Autowired
    public UserController(UserService userService, UserFormValidator userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @GetMapping("/user/{username}")
    public String profile(@PathVariable("username") String username, Model model) {
        User user = userService.findByUsername(username);

        if (null == user) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        model.addAttribute("user", user);

        return "user/profile";
    }
    
    @GetMapping("/edit-profile")
    public String editProfile(Model model, UserForm userForm, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());

        model.addAttribute("userForm", userForm);

        return "user/edit-profile";
    }

    @PostMapping("/edit-profile")
    public String EditProfile(
            @ModelAttribute("userForm") UserForm userForm,
            Model model,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        User userFromDb = userService.findByUsername(authentication.getName());
        User userFromDbClone = (User) userFromDb.clone();
        // Hack for avoid referenced value change (THIS IS JAVAAA!!!).
        // TODO: Find more semantic and elegant way.

        userForm.setId(userFromDbClone.getId());
        userForm.setUsername(authentication.getName());
        if (null == userForm.getPasswordOld() || userForm.getPasswordOld().isEmpty() || userForm.getPasswordOld().isBlank()) {
            userForm.setPasswordOld(null);
            userForm.setPassword(userFromDbClone.getPassword());
            userForm.setPasswordConfirm(userFromDbClone.getPassword());
        }

        userFormValidator.validate(userForm, bindingResult);

        if (!bindingResult.hasErrors()) {
            userFromDbClone.setFirstName(userForm.getFirstName());
            userFromDbClone.setLastName(userForm.getLastName());
            userFromDbClone.setPassword(userForm.getPassword());

            userService.save(userFromDbClone);

            model.addAttribute("updatedSuccessfully", true);
        }

        return "user/edit-profile";
    }

}
