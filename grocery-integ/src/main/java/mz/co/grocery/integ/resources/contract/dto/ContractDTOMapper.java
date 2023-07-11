/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractType;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.common.EnumDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ContractDTOMapper extends AbstractDTOMapper<ContractDTO, Contract> implements DTOMapper<ContractDTO, Contract> {

	private DTOMapper<UnitDTO, Unit> unitMapper;
	private DTOMapper<CustomerDTO, Customer> customerMapper;
	private ApplicationTranslator translator;

	public ContractDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper, final DTOMapper<CustomerDTO, Customer> customerMapper,
			final ApplicationTranslator translator) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
		this.translator = translator;
	}

	@Override
	public Contract toDomain(final ContractDTO dto) {
		final Contract domain = new Contract();

		domain.setContractType(ContractType.valueOf(dto.getContractType().getValue()));
		domain.setDescription(dto.getDescription());
		domain.setStartDate(dto.getStartDate());
		domain.setEndDate(dto.getEndDate());
		domain.setReferencePaymentDate(dto.getReferencePaymentDate());
		domain.setTotalPaid(dto.getTotalPaid());

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		dto.getCustomerDTO().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		return this.toDomain(dto, domain);
	}

	@Override
	public ContractDTO toDTO(final Contract domain) {
		final ContractDTO dto = new ContractDTO();

		final EnumDTO contractType = new EnumDTO(domain.getContractType().toString(), this.translator.getTranslation(domain.getContractType().toString()));

		dto.setContractType(contractType);
		dto.setDescription(dto.getDescription());
		dto.setStartDate(dto.getStartDate());
		dto.setEndDate(dto.getEndDate());
		dto.setReferencePaymentDate(dto.getReferencePaymentDate());
		dto.setTotalPaid(dto.getTotalPaid());

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		domain.getCustomer().ifPresent(customer -> dto.setCustomerDTO(this.customerMapper.toDTO(customer)));

		return this.toDTO(dto, domain);
	}
}
