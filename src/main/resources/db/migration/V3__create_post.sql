CREATE TABLE `post`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `public`         bit(1)                                   DEFAULT NULL,
    `anonym`         bit(1)                                   DEFAULT NULL,
    `created_at`     datetime(6)                              DEFAULT NULL,
    `text`           varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `author_id`      bigint(20)                               DEFAULT NULL,
    `destination_id` bigint(20)                               DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK12njtf8e0jmyb45lqfpt6ad89` (`author_id`),
    KEY `FKaxurrjwuph8l5su7q8c8ihw73` (`destination_id`),
    CONSTRAINT `FK12njtf8e0jmyb45lqfpt6ad89` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKaxurrjwuph8l5su7q8c8ihw73` FOREIGN KEY (`destination_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
