package dev.abgeo.secretic.controller;

import dev.abgeo.secretic.model.Role;
import dev.abgeo.secretic.model.User;
import dev.abgeo.secretic.repository.RoleRepository;
import dev.abgeo.secretic.service.SecurityService;
import dev.abgeo.secretic.service.UserService;
import dev.abgeo.secretic.validator.UserValidator;
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
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(
            UserService userService,
            SecurityService securityService,
            UserValidator userValidator,
            RoleRepository roleRepository
    ) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model, User user) {
        model.addAttribute("user", user);

        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        // Set user role to ROLE_USER.
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(userRoles);

        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

        return "redirect:/";
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

        return "user/login";
    }

}
