package dev.abgeo.secretic.util;

import dev.abgeo.secretic.model.Notification;
import dev.abgeo.secretic.service.DefaultUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

@ControllerAdvice(annotations = Controller.class)
public class AnnotationAdvice {

    final
    DefaultUserService defaultUserService;

    public AnnotationAdvice(DefaultUserService defaultUserService) {
        this.defaultUserService = defaultUserService;
    }

    @ModelAttribute("notifications")
    public Set<Notification> getNotifications() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return defaultUserService.findByUsername(userDetails.getUsername()).getNotifications();
    }

}
