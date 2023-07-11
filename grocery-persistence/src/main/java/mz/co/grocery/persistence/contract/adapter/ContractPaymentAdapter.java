/**
 *
 */
package mz.co.grocery.persistence.contract.adapter;

import javax.transaction.Transactional;

import mz.co.grocery.core.application.contract.out.ContractPaymentPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.persistence.contract.entity.ContractPaymentEntity;
import mz.co.grocery.persistence.contract.repository.ContractPaymentRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ContractPaymentAdapter implements ContractPaymentPort {

	private ContractPaymentRepository repository;

	private EntityMapper<ContractPaymentEntity, ContractPayment> mapper;

	public ContractPaymentAdapter(final ContractPaymentRepository repository,
			final EntityMapper<ContractPaymentEntity, ContractPayment> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public ContractPayment createContractPayment(final UserContext userContext, final ContractPayment contractPayment) throws BusinessException {
		final ContractPaymentEntity entity = this.mapper.toEntity(contractPayment);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}
}
