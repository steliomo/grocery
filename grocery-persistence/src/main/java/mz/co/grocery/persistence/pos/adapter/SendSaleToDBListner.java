/**
 *
 */
package mz.co.grocery.persistence.pos.adapter;

import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter(SendSaleToDBListner.NAME)
public class SendSaleToDBListner implements SaleListner {

	public static final String NAME = "mz.co.grocery.persistence.pos.adapter.SendSaleToDBListner";

	private SalePort salePort;

	public SendSaleToDBListner(final SalePort salePort) {
		this.salePort = salePort;
	}

	@Override
	public Sale send(final UserContext context, final Sale sale) throws BusinessException {
		return this.salePort.createSale(context, sale);
	}
}
