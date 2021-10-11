INSERT INTO CHARGES (SERVICE_TYPE,CALL_CATEGORY,SUBSCRIBER_TYPE,CHARGE_PER_UNIT) values
('1','1','1','0.3'),
('1','1','2','0.5'),
('1','2','1','1.5'),
('1','2','2','2'),
('2','1','1','0.15'),
('2','1','2','0.25'),
('2','2','1','2'),
('2','2','2','2.5'),
('3','1','1','1'),
('3','1','2','1.5'),
('3','2','1','5'),
('3','2','2','6');

Insert into CDR (ANUM,  BNUM, SERVICE_TYPE, CALL_CATEGORY,  SUBSCRIBER_TYPE, START_DATE_TIME,  USED_AMOUNT, CHARGE,  FILE_NAME, ROUNDED_USED_AMOUNT) values
('006596312796','6596312700',	'VOICE',	'ROAMING',	'PREPAID',	'20210704010510',	'70s',	'4.0',	'CDRs0003.json',	'2Minutes'),
('006596312794','6596312700',	'SMS',	'LOCAL',	'POSTPAID',	'20210708141050',	null,	'0.15','CDRs0003.json',null),
('006596312794',null,'GPRS',	'ROAMING',	'POSTPAID',	'20210708141050',	'3000KB',	'15.0',	'CDRs0003.json',	'3MB');
