/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.application.pos.in.RegistTableItemsUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class RegistTableItemsService extends AbstractService implements RegistTableItemsUseCase {

	private SaleItemPort saleItemPort;

	private SalePort salePort;

	public RegistTableItemsService(final SaleItemPort saleItemPort, final SalePort salePort) {
		this.saleItemPort = saleItemPort;
		this.salePort = salePort;
	}

	@Override
	public Sale registTableItems(final UserContext context, Sale table) throws BusinessException {

		final Set<SaleItem> items = table.getItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("the.item.list.must.not.be.empty");
		}

		for (final SaleItem saleItem : items) {

			saleItem.setSale(table);

			if (saleItem.isProduct()) {

				final Optional<SaleItem> product = this.saleItemPort.findBySaleAndProductUuid(table.getUuid(), saleItem.getStock().get().getUuid());

				if (product.isPresent()) {
					this.updateSaleItem(context, saleItem, product);
				} else {
					this.registSaleItem(context, saleItem);
				}
			} else {
				final Optional<SaleItem> service = this.saleItemPort.findBySaleAndServiceUuid(table.getUuid(),
						saleItem.getServiceItem().get().getUuid());
				if (service.isPresent()) {
					this.updateSaleItem(context, saleItem, service);
				} else {
					this.registSaleItem(context, saleItem);
				}
			}
		}

		table = this.salePort.fetchByUuid(table.getUuid());

		table.calculateTotal();
		table.calculateBilling();
		table.updateDeliveryStatus();
		table.setSaleStatus(SaleStatus.IN_PROGRESS);

		table = this.salePort.updateSale(context, table);

		return table;
	}

	private void registSaleItem(final UserContext context, final SaleItem saleItem) throws BusinessException {
		saleItem.addDeliveredQuantity(saleItem.getQuantity());
		saleItem.setDeliveryStatus();

		this.saleItemPort.createSaleItem(context, saleItem);
	}

	private void updateSaleItem(final UserContext context, final SaleItem saleItem, final Optional<SaleItem> product) throws BusinessException {
		final SaleItem item = product.get();
		item.addQuantity(saleItem.getQuantity());
		item.addDeliveredQuantity(saleItem.getQuantity());
		item.setDeliveryStatus();

		this.saleItemPort.updateSaleItem(context, item);
	}
}
