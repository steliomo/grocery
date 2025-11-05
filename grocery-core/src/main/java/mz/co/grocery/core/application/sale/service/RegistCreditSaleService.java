/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import mz.co.grocery.core.application.sale.in.RegistCreditSaleUseCase;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class RegistCreditSaleService implements RegistCreditSaleUseCase {

	private SalePort salePort;

	public RegistCreditSaleService(final SalePort salePort) {
		this.salePort = salePort;
	}

	@Override
	public Sale regist(final UserContext context, final String saleUuid) throws BusinessException {

		final Sale sale = this.salePort.findByUuid(saleUuid);

		sale.setSaleType(SaleType.CREDIT);
		sale.updateSaleStatus();

		this.salePort.updateSale(context, sale);

		return sale;
	}

}
