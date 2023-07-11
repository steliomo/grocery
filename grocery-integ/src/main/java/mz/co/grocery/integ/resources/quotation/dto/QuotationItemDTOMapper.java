/**
 *
 */
package mz.co.grocery.integ.resources.quotation.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
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
public class QuotationItemDTOMapper extends AbstractDTOMapper<QuotationItemDTO, QuotationItem> implements DTOMapper<QuotationItemDTO, QuotationItem> {

	private DTOMapper<QuotationDTO, Quotation> quotationMapper;

	private DTOMapper<StockDTO, Stock> stockMapper;

	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	public QuotationItemDTOMapper(final DTOMapper<StockDTO, Stock> stockMapper, final DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper) {
		this.stockMapper = stockMapper;
		this.serviceItemMapper = serviceItemMapper;
	}

	@Override
	public QuotationItemDTO toDTO(final QuotationItem domain) {
		final QuotationItemDTO dto = new QuotationItemDTO();

		domain.getQuotation().ifPresent(quotation -> dto.setQuotationDTO(this.quotationMapper.toDTO(quotation)));

		domain.getItem().ifPresent(item -> {
			if (item.isStockable()) {
				dto.setStockDTO(this.stockMapper.toDTO((Stock) item));
				return;
			}
			dto.setServiceItemDTO(this.serviceItemMapper.toDTO((ServiceItem) item));
		});

		dto.setQuantity(domain.getQuantity());
		dto.setDays(domain.getDays());

		return this.toDTO(dto, domain);
	}

	@Override
	public QuotationItem toDomain(final QuotationItemDTO dto) {
		final QuotationItem domain = new QuotationItem();

		dto.getQuotationDTO().ifPresent(quotationDTO -> domain.setQuotation(this.quotationMapper.toDomain(quotationDTO)));

		dto.getStockDTO().ifPresent(stockDTO -> domain.setItem(this.stockMapper.toDomain(stockDTO)));
		dto.getServiceItemDTO().ifPresent(serviceItem -> domain.setItem(this.serviceItemMapper.toDomain(serviceItem)));

		domain.setQuantity(dto.getQuantity());
		domain.setDays(dto.getDays());

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setQuotationMapper(final DTOMapper<QuotationDTO, Quotation> quotationMapper) {
		this.quotationMapper = quotationMapper;
	}
}
