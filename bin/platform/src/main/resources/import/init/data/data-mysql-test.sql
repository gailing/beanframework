INSERT INTO `location` (`uuid`, `id`, `name`) VALUES (UNHEX(  '110E8400E29B11D4A716446655440000' ), 't1','T1-LOCATION') ON DUPLICATE KEY UPDATE name = name;
INSERT INTO `location` (`uuid`, `id`, `name`) VALUES (UNHEX(  '110E8400E29B11D4A716446655440001' ), 't2','T2-LOCATION') ON DUPLICATE KEY UPDATE name = name;