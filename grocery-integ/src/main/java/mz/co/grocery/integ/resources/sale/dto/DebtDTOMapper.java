/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class DebtDTOMapper implements DTOMapper<DebtDTO, Debt> {

	private CustomerDTOMapper customerDTOMapper;

	private DebtItemDTOMapper debtItemDTOMapper;

	public DebtDTOMapper(final CustomerDTOMapper customerDTOMapper, final DebtItemDTOMapper debtItemDTOMapper) {
		this.customerDTOMapper = customerDTOMapper;
		this.debtItemDTOMapper = debtItemDTOMapper;
	}

	@Override
	public DebtDTO toDTO(final Debt domain) {
		final DebtDTO dto = new DebtDTO();

		domain.getCustomer().ifPresent(customer -> dto.setCustomer(this.customerDTOMapper.toDTO(customer)));

		dto.setTotalToPay(domain.getTotalToPay());
		dto.setTotalPaid(domain.getTotalPaid());
		dto.setAmount(domain.getAmount());

		domain.getDebtItems().ifPresent(debtItems -> debtItems.forEach(debtItem -> dto.addDebtItem(this.debtItemDTOMapper.toDTO(debtItem))));

		return dto;
	}

	@Override
	public Debt toDomain(final DebtDTO dto) {

		final Debt domain = new Debt();

		dto.getCustomer().ifPresent(customer -> domain.setCustomer(this.customerDTOMapper.toDomain(customer)));

		domain.setTotalToPay(dto.getTotalToPay());
		domain.setTotalPaid(dto.getTotalPaid());
		domain.setAmount(dto.getAmount());

		dto.getDebtItems().ifPresent(debtItems -> debtItems.forEach(debtItem -> domain.addDebtItem(this.debtItemDTOMapper.toDomain(debtItem))));

		return domain;
	}

}
