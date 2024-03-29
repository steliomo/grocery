-- QUOTATIONS
CREATE TABLE `QUOTATIONS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `CUSTOMER_ID` BIGINT(20) NOT NULL,
  `TYPE` VARCHAR(15) NOT NULL, 
  `ISSUE_DATE` DATE NOT NULL,
  `STATUS` VARCHAR(15) NOT NULL,
  `UNIT_ID` BIGINT(20) NOT NULL,
  `TOTAL_VALUE` DECIMAL(19,2) NOT NULL,
  `DISCOUNT` DECIMAL(19,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`ID`),
  KEY `FK_QUOTATIONS_CUSTOMER_ID` (`CUSTOMER_ID`),
  KEY `FK_QUOTATIONS_UNIT_ID` (`UNIT_ID`),
  CONSTRAINT `FK_QUOTATIONS_UNIT_ID` FOREIGN KEY (`UNIT_ID`) REFERENCES `GROCERIES` (`ID`),
  CONSTRAINT `FK_QUOTATIONS_CUSTOMER_ID` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- QUOTATION_ITEMS
CREATE TABLE `QUOTATION_ITEMS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `QUOTATION_ID` BIGINT(20) NOT NULL,
  `STOCK_ID` BIGINT(20) DEFAULT NULL,
  `SERVICE_ITEM_ID` BIGINT(20) DEFAULT NULL,
  `QUANTITY` DECIMAL(19,2) NOT NULL,
  `DAYS` DECIMAL(19,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUOTATION_ITEMS_QUOTATION_ID` (`QUOTATION_ID`),
  KEY `FK_QUOTATION_ITEMS_STOCK_ID` (`STOCK_ID`),
  KEY `FK_QUOTATION_ITEMS_SERVICE_ITEM_ID` (`SERVICE_ITEM_ID`),
  CONSTRAINT `FK_QUOTATION_ITEMS_QUOTATION_ID` FOREIGN KEY (`QUOTATION_ID`) REFERENCES `QUOTATIONS` (`ID`),
  CONSTRAINT `FK_QUOTATION_ITEMS_STOCK_ID` FOREIGN KEY (`STOCK_ID`) REFERENCES `STOCKS` (`ID`),
  CONSTRAINT `FK_QUOTATION_ITEMS_SERVICE_ITEM_ID` FOREIGN KEY (`SERVICE_ITEM_ID`) REFERENCES `SERVICE_ITEMS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- RENTS
ALTER TABLE RENTS ADD COLUMN RENT_STATUS VARCHAR(15) DEFAULT NULL;