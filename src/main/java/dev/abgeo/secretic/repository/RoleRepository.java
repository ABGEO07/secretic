package dev.abgeo.secretic.repository;

import dev.abgeo.secretic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
