package dev.abgeo.secretic.controller;

import dev.abgeo.secretic.form.UserForm;
import dev.abgeo.secretic.model.Role;
import dev.abgeo.secretic.model.User;
import dev.abgeo.secretic.repository.RoleRepository;
import dev.abgeo.secretic.service.SecurityService;
import dev.abgeo.secretic.service.UserService;
import dev.abgeo.secretic.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthenticationController {

    private final UserService userService;

    private final SecurityService securityService;

    private final UserFormValidator userFormValidator;

    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationController(
            UserService userService,
            SecurityService securityService,
            UserFormValidator userFormValidator,
            RoleRepository roleRepository
    ) {
        this.userService = userService;
        this.securityService = securityService;
        this.userFormValidator = userFormValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model, UserForm userForm) {
        model.addAttribute("userForm", userForm);

        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {
        userFormValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "authentication/registration";
        }

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(userRoles);

        userService.save(user);
        securityService.autoLogin(user.getUsername(), userForm.getPassword());

        return "redirect:/edit-profile";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "მომხმარებლის სახელი ან პაროლი არასწორია!");
        }

        if (logout != null) {
            model.addAttribute("message", "თქვენ წარმატებით გახვედით სისტემიდან.");
        }

        return "authentication/login";
    }

}
