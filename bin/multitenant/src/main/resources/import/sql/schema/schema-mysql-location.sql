CREATE TABLE IF NOT EXISTS `tenant_location` (
  `uuid` binary(16) NOT NULL,
  `id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdBy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedBy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;