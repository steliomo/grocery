/**
 *
 */
package mz.co.grocery.core.domain.item;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */

public interface Item {

	ItemType getType();

	BigDecimal getSalePrice();

	Boolean isStockable();

	String getUuid();

	String getName();

	BigDecimal getRentPrice();
}
