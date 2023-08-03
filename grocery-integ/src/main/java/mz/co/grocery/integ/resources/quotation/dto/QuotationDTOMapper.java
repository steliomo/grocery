/**
 *
 */
package mz.co.grocery.integ.resources.quotation.dto;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class QuotationDTOMapper extends AbstractDTOMapper<QuotationDTO, Quotation> implements DTOMapper<QuotationDTO, Quotation> {

	private DTOMapper<CustomerDTO, Customer> customerMapper;

	private DTOMapper<UnitDTO, Unit> unitMapper;

	private DTOMapper<QuotationItemDTO, QuotationItem> quotationItemMapper;

	public QuotationDTOMapper(final DTOMapper<CustomerDTO, Customer> customerMapper, final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.customerMapper = customerMapper;
		this.unitMapper = unitMapper;
	}

	@Override
	public QuotationDTO toDTO(final Quotation domain) {
		final QuotationDTO dto = new QuotationDTO();

		domain.getCustomer().ifPresent(customer -> dto.setCustomerDTO(this.customerMapper.toDTO(customer)));
		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));

		dto.setName(domain.getName());
		dto.setType(domain.getType());
		dto.setIssueDate(domain.getIssueDate());
		dto.setStatus(domain.getStatus());
		dto.setTotalValue(domain.getTotalValue());
		dto.setDiscount(domain.getDiscount());

		domain.getItems().forEach(item -> {
			item.setQuotation(null);
			dto.addItem(this.quotationItemMapper.toDTO(item));
		});

		return this.toDTO(dto, domain);
	}

	@Override
	public Quotation toDomain(final QuotationDTO dto) {
		final Quotation domain = new Quotation();

		dto.getCustomerDTO().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));
		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));

		domain.setName(dto.getName());
		domain.setType(dto.getType());
		domain.setIssueDate(dto.getIssueDate());
		domain.setStatus(dto.getStatus());
		domain.setTotalValue(dto.getTotalValue());

		dto.getItems().forEach(item -> {
			final QuotationItem quotationItem = this.quotationItemMapper.toDomain(item);
			quotationItem.setQuotation(domain);
			domain.addItem(quotationItem);
		});

		return this.toDomain(dto, domain);
	}

	@Inject
	public void setQuotationItemMapper(final DTOMapper<QuotationItemDTO, QuotationItem> quotationItemMapper) {
		this.quotationItemMapper = quotationItemMapper;
	}
}
