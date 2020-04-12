CREATE TABLE `user_roles`
(
    `users_id` bigint(20) NOT NULL,
    `roles_id` bigint(20) NOT NULL,
    PRIMARY KEY (`users_id`, `roles_id`),
    KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
    CONSTRAINT `FK7ecyobaa59vxkxckg6t355l86` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
