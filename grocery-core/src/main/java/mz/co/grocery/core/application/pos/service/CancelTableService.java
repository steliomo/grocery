/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.util.Set;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.pos.in.CancelTableUseCase;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class CancelTableService implements CancelTableUseCase {

	private SalePort salePort;

	private StockPort stockPort;

	private PaymentUseCase paymentUseCase;

	public CancelTableService(final SalePort salePort, final StockPort stockPort, final PaymentUseCase paymentUseCase) {
		this.salePort = salePort;
		this.stockPort = stockPort;
		this.paymentUseCase = paymentUseCase;
	}

	@Override
	public Sale cancel(final UserContext context, Sale table) throws BusinessException {

		final Set<SaleItem> items = table.getItems().get();
		final String unit = table.getUnit().get().getUuid();

		if (items.isEmpty()) {
			table = this.salePort.findByUuid(table.getUuid());

			table.setSaleStatus(SaleStatus.CANCELLED);

			table = this.salePort.updateSale(context, table);

			this.paymentUseCase.debitTransaction(context, unit);

			return table;
		}

		for (final SaleItem saleItem : items) {

			if (saleItem.isProduct()) {
				final Stock stock = this.stockPort.findStockByUuid(saleItem.getStock().get().getUuid());
				stock.addQuantity(saleItem.getQuantity());

				this.stockPort.updateStock(context, stock);
			}
		}

		table = this.salePort.findByUuid(table.getUuid());

		table.setSaleStatus(SaleStatus.CANCELLED);

		this.salePort.updateSale(context, table);

		this.paymentUseCase.debitTransaction(context, unit);

		return table;
	}
}
