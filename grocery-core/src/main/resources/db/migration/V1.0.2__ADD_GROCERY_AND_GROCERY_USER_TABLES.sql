-- GROCERIES
CREATE TABLE `GROCERIES` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `NAME` VARCHAR(140) NOT NULL, 
  `ADDRESS` VARCHAR(255) NOT NULL, 
  `PHONE_NUMBER` VARCHAR(30) NOT NULL,
  `PHONE_NUMBER_OPTIONAL` VARCHAR(30) DEFAULT NULL,
  `EMAIL` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GROCERY_USERS
CREATE TABLE `GROCERY_USERS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `GROCERY_ID` BIGINT NOT NULL,
  `USER_UUID` VARCHAR(50) NOT NULL, 
  `USER_ROLE` VARCHAR(30) NOT NULL, 
  `EXPIRY_DATE` DATE NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_GROCERY_USERS_GROCERY_ID` (`GROCERY_ID`),
  CONSTRAINT `FK_GROCERY_USERS_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `GROCERIES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- STOCKS
ALTER TABLE `STOCKS` ADD COLUMN `GROCERY_ID` BIGINT(20);
--ALTER TABLE `STOCKS` CHANGE `GROCERY_ID` BIGINT(20) NOT NULL;
--ALTER TABLE `STOCKS` ADD CONSTRAINT `FK_STOCKS_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `STOCKS` (`ID`);

-- SALES
ALTER TABLE `SALES` ADD COLUMN `GROCERY_ID` BIGINT(20);
--ALTER TABLE `SALES` CHANGE `GROCERY_ID` BIGINT(20) NOT NULL;
--ALTER TABLE `SALES` ADD CONSTRAINT `FK_SALES_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `SALES` (`ID`);
