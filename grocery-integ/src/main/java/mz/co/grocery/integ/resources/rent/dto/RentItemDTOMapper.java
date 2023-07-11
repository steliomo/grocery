/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.rent.RentItem;
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
public class RentItemDTOMapper extends AbstractDTOMapper<RentItemDTO, RentItem> implements DTOMapper<RentItemDTO, RentItem> {

	private DTOMapper<StockDTO, Stock> stockMapper;

	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	public RentItemDTOMapper(final DTOMapper<StockDTO, Stock> stockMapper, final DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper) {
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;
	}

	@Override
	public RentItemDTO toDTO(final RentItem domain) {
		final RentItemDTO dto = new RentItemDTO();

		domain.getItem().ifPresent(item -> {
			if (item.isStockable()) {
				dto.setStockDTO(this.stockMapper.toDTO((Stock) item));
				return;
			}

			dto.setServiceItemDTO(this.serviceItemMapper.toDTO((ServiceItem) item));
		});

		dto.setPlannedQuantity(domain.getPlannedQuantity());
		dto.setPlannedDays(domain.getPlannedDays());
		dto.setDiscount(domain.getDiscount());
		dto.setLoadedQuantity(domain.getLoadedQuantity());
		dto.setLoadingDate(domain.getLoadingDate());
		dto.setReturnedQuantity(domain.getReturnedQuantity());
		dto.setReturnDate(domain.getReturnDate());

		return this.toDTO(dto, domain);
	}

	@Override
	public RentItem toDomain(final RentItemDTO dto) {
		final RentItem domain = new RentItem();

		dto.getStockDTO().ifPresent(stock -> domain.setItem(this.stockMapper.toDomain(stock)));
		dto.getServiceItemDTO().ifPresent(serviceItem -> domain.setItem(this.serviceItemMapper.toDomain(serviceItem)));

		domain.setPlannedQuantity(dto.getPlannedQuantity());
		domain.setPlannedDays(dto.getPlannedDays());

		return this.toDomain(dto, domain);
	}
}
