
-- SALES
ALTER TABLE `SALES` ADD COLUMN `CUSTOMER_ID` BIGINT(20) DEFAULT NULL;
ALTER TABLE `SALES` ADD CONSTRAINT `FK_SALES_CUSTOMER_ID` FOREIGN KEY(`CUSTOMER_ID`) REFERENCES `CUSTOMERS` (`ID`);

ALTER TABLE `SALES` ADD COLUMN `SALE_TYPE` VARCHAR(20) NOT NULL DEFAULT 'CASH';
ALTER TABLE `SALES` ADD COLUMN `SALE_STATUS` VARCHAR(20) NOT NULL DEFAULT 'COMPLETE';
ALTER TABLE `SALES` ADD COLUMN `TOTAL_PAID` DECIMAL(19,2) DEFAULT NULL;
ALTER TABLE `SALES` ADD COLUMN `DUE_DATE` DATE DEFAULT NULL;

-- SALE_PAYMENTS
CREATE TABLE `SALE_PAYMENTS` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CREATED_AT` DATETIME NOT NULL,
  `CREATED_BY` VARCHAR(50) NOT NULL,
  `ENTITY_STATUS` VARCHAR(15) NOT NULL,
  `UPDATED_AT` DATETIME DEFAULT NULL,
  `UPDATED_BY` VARCHAR(50) DEFAULT NULL,
  `UUID` VARCHAR(50) NOT NULL,
  `SALE_ID` BIGINT(20) NOT NULL,
  `PAYMENT_VALUE` DECIMAL(19,2) NOT NULL,
  `PAYMENT_DATE` DATE NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SALE_PAYMENTS_SALE_ID` (`SALE_ID`),
  CONSTRAINT `FK_SALE_PAYMENTS_SALE_ID` FOREIGN KEY (`SALE_ID`) REFERENCES `SALES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


