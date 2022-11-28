DROP DATABASE IF EXISTS `resource_web`;
CREATE DATABASE `resource_web`;

USE `resource_web`;

CREATE TABLE `user` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `password` CHAR(100) NOT NULL,
    `identity` VARCHAR(20) NOT NULL,
    `code` CHAR(100) NOT NULL,
    `point` INT NOT NULL DEFAULT 0,
    `last_login` DATE,
    `banned` DATE,
    `invitor_id` BIGINT,
    UNIQUE(`code`),
    UNIQUE(`name`),
    FOREIGN KEY (`invitor_id`) REFERENCES `user`(`id`)
);

INSERT INTO `user`(`name`,`password`,`identity`,`code`)
VALUES("admin","admin","ADMIN","admin"),
("user","user","USER","user");

CREATE TABLE `resource` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(128) NOT NULL,
    `description` VARCHAR(800) NOT NULL,
    `user_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `resource_file` (
	`file_id` CHAR(50) NOT NULL PRIMARY KEY,
	`resource_id` BIGINT NOT NULL,
    FOREIGN KEY (`resource_id`) REFERENCES `resource`(`id`)
);

CREATE TABLE `message` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `resource_id` BIGINT NOT NULL,
    `text` VARCHAR(400) NOT NULL,
    `index` INT NOT NULL,
    `post` DATE NOT NULL,
	FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`resource_id`) REFERENCES `resource`(`id`)
);