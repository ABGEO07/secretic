CREATE TABLE `notification`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)                             DEFAULT NULL,
    `link`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `message`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `seen`       bit(1)                                  DEFAULT NULL,
    `title`      varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `user_id`    bigint(20)                              DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`),
    CONSTRAINT `FKb0yvoep4h4k92ipon31wmdf7e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
