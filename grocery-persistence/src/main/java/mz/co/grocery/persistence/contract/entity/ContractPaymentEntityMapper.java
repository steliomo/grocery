/**
 *
 */
package mz.co.grocery.persistence.contract.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ContractPaymentEntityMapper extends AbstractEntityMapper<ContractPaymentEntity, ContractPayment>
implements EntityMapper<ContractPaymentEntity, ContractPayment> {

	private EntityMapper<ContractEntity, Contract> contractMapper;

	public ContractPaymentEntityMapper(final EntityMapper<ContractEntity, Contract> contractMapper) {
		this.contractMapper = contractMapper;
	}

	@Override
	public ContractPaymentEntity toEntity(final ContractPayment domain) {
		final ContractPaymentEntity entity = new ContractPaymentEntity();

		domain.getContract().ifPresent(contract -> entity.setContract(this.contractMapper.toEntity(contract)));
		entity.setPaymentDate(domain.getPaymentDate());
		entity.setReferenceDate(domain.getReferenceDate());

		return this.toEntity(entity, domain);
	}

	@Override
	public ContractPayment toDomain(final ContractPaymentEntity entity) {

		final ContractPayment domain = new ContractPayment();

		entity.getContract().ifPresent(contract -> domain.setContract(this.contractMapper.toDomain(contract)));
		domain.setPaymentDate(entity.getPaymentDate());
		domain.setReferenceDate(entity.getReferenceDate());

		return this.toDomain(entity, domain);
	}

}
