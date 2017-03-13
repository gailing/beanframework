CREATE TABLE IF NOT EXISTS `location` (
  `uuid` binary(16) NOT NULL,
  `id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdBy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `lastModifiedBy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO `location` (`uuid`, `id`, `name`) VALUES (UNHEX(  '110E8400E29B11D4A716446655440000' ), 't1','T1-LOCATION') ON DUPLICATE KEY UPDATE name = 'T1-LOCATION';