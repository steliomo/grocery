/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SaleItemDTOMapper extends AbstractDTOMapper<SaleItemDTO, SaleItem> implements DTOMapper<SaleItemDTO, SaleItem> {

	private DTOMapper<StockDTO, Stock> stockMapper;

	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	public SaleItemDTOMapper(final DTOMapper<StockDTO, Stock> stockMapper, final DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper) {
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;

	}

	@Override
	public SaleItemDTO toDTO(final SaleItem domain) {
		final SaleItemDTO dto = new SaleItemDTO();

		domain.getStock().ifPresent(stock -> dto.setStockDTO(this.stockMapper.toDTO(stock)));
		domain.getServiceItem().ifPresent(serviceItem -> dto.setServiceItemDTO(this.serviceItemMapper.toDTO(serviceItem)));

		dto.setQuantity(domain.getQuantity());
		dto.setSaleItemValue(domain.getSaleItemValue());
		dto.setDiscount(domain.getDiscount());
		dto.setDeliveredQuantity(domain.getDeliveredQuantity());

		return this.toDTO(dto, domain);
	}

	@Override
	public SaleItem toDomain(final SaleItemDTO dto) {
		final SaleItem domain = new SaleItem();

		dto.getStockDTO().ifPresent(stock -> domain.setStock(this.stockMapper.toDomain(stock)));
		dto.getServiceItemDTO().ifPresent(serviceItem -> domain.setServiceItem(this.serviceItemMapper.toDomain(serviceItem)));

		domain.setQuantity(dto.getQuantity());
		domain.setSaleItemValue(dto.getSaleItemValue());
		domain.setDiscount(dto.getDiscount());
		domain.setDeliveredQuantity(dto.getDeliveredQuantity());

		return this.toDomain(dto, domain);
	}
}
