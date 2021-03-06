-- INVENTORIES
CREATE TABLE `INVENTORIES` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `GROCERY_ID` BIGINT NOT NULL,
  `INVENTORY_DATE` DATE NOT NULL, 
  `INVENTORY_STATUS` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_INVENTORIES_GROCERY_ID` (`GROCERY_ID`),
  CONSTRAINT `FK_INVENTORIES_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `GROCERIES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- STOCK_INVENTORIES
CREATE TABLE `STOCK_INVENTORIES` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `INVENTORY_ID` BIGINT NOT NULL,
  `STOCK_ID` BIGINT NOT NULL,
  `FISICAL_INVENTORY` DECIMAL(19,2) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STOCK_INVENTORIES_INVENTORY_ID` (`INVENTORY_ID`),
  KEY `FK_STOCK_INVENTORIES_STOCK_ID` (`STOCK_ID`),
  CONSTRAINT `FK_STOCK_INVENTORIES_INVENTORY_ID` FOREIGN KEY (`INVENTORY_ID`) REFERENCES `INVENTORIES` (`ID`),
  CONSTRAINT `FK_STOCK_INVENTORIES_STOCK_ID` FOREIGN KEY (`STOCK_ID`) REFERENCES `STOCKS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- STOCKS
ALTER TABLE `STOCKS` MODIFY `GROCERY_ID` BIGINT(20) NOT NULL;
ALTER TABLE `STOCKS` ADD CONSTRAINT `FK_STOCKS_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `STOCKS` (`ID`);

-- SALES
ALTER TABLE `SALES` MODIFY `GROCERY_ID` BIGINT(20) NOT NULL;
ALTER TABLE `SALES` ADD CONSTRAINT `FK_SALES_GROCERY_ID` FOREIGN KEY (`GROCERY_ID`) REFERENCES `SALES` (`ID`);
