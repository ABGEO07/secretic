package dev.abgeo.secretic.repository;

import dev.abgeo.secretic.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
