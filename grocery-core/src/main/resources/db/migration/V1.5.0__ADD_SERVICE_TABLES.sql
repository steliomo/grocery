-- SERVICES
CREATE TABLE `SERVICES` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `NAME` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- SERVICE_DESCRIPTIONS
CREATE TABLE `SERVICE_DESCRIPTIONS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `DESCRIPTION` VARCHAR(100) NOT NULL,
  `SERVICE_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SERVICE_DESCRIPTIONS_SERVICE_ID` (`SERVICE_ID`),
  CONSTRAINT `FK_SERVICE_DESCRIPTIONS_SERVICE_ID` FOREIGN KEY (`SERVICE_ID`) REFERENCES `SERVICES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- SERVICE_ITEMS
CREATE TABLE `SERVICE_ITEMS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `SERVICE_DESCRIPTION_ID` BIGINT(20) NOT NULL,
  `UNIT_ID` BIGINT(20) NOT NULL,
  `SALE_PRICE` DECIMAL(19,2) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SERVICE_ITEMS_SERVICE_DESCRIPTION_ID` (`SERVICE_DESCRIPTION_ID`),
  KEY `FK_SERVICE_ITEMS_UNIT_ID` (`UNIT_ID`),
  CONSTRAINT `FK_SERVICE_ITEMS_SERVICE_DESCRIPTION_ID` FOREIGN KEY (`SERVICE_DESCRIPTION_ID`) REFERENCES `SERVICE_DESCRIPTIONS` (`ID`),
  CONSTRAINT `FK_SERVICE_ITEMS_UNIT_ID` FOREIGN KEY (`UNIT_ID`) REFERENCES `GROCERIES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GROCERIES
ALTER TABLE GROCERIES ADD COLUMN UNIT_TYPE VARCHAR(30) NOT NULL DEFAULT 'GROCERY';

-- SALE_ITEMS
ALTER TABLE `SALE_ITEMS` MODIFY `STOCK_ID` BIGINT(20) NULL;

ALTER TABLE SALE_ITEMS ADD COLUMN SERVICE_ITEM_ID BIGINT(20);
ALTER TABLE `SALE_ITEMS` ADD CONSTRAINT `FK_SALE_ITEMS_SERVICE_ITEM_ID` FOREIGN KEY (`SERVICE_ITEM_ID`) REFERENCES `SERVICE_ITEMS` (`ID`);