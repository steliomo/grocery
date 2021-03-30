/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class SaleableDTO {

	private List<StockDTO> stocks;

	private List<ServiceItemDTO> serviceItems;

	public List<StockDTO> getStocks() {

		if (this.stocks == null) {
			return new ArrayList<StockDTO>();
		}

		return this.stocks;
	}

	public List<ServiceItemDTO> getServiceItems() {

		if (this.serviceItems == null) {
			return new ArrayList<ServiceItemDTO>();
		}

		return this.serviceItems;
	}
}
