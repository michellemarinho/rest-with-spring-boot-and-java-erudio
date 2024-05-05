CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `author` longtext,
  `launchDate` datetime(6) NOT NULL,
  `price` decimal(65,2) NOT NULL DEFAULT (0),
  `title` longtext,
  PRIMARY KEY (`id`)
);