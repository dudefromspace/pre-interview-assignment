create table if not exists CDR (
    ID int not null  auto_increment,
    ANUM varchar(12) not null,
    BNUM varchar(10),
    SERVICE_TYPE varchar(25) not null,
    CALL_CATEGORY varchar(25) not null,
    SUBSCRIBER_TYPE varchar(25) not null,
    START_DATE_TIME varchar(14) not null,
    USED_AMOUNT varchar(25),
    CHARGE varchar(25) not null,
    FILE_NAME varchar(25) not null,
    ROUNDED_USED_AMOUNT varchar (25),
    primary key(id)
);

create table if not exists CHARGES (
    ID int not null auto_increment,
    SERVICE_TYPE varchar(25) not null,
    CALL_CATEGORY varchar(25) not null,
    SUBSCRIBER_TYPE varchar(25) not null,
    CHARGE_PER_UNIT varchar(25) not null,
    primary key(id)
);