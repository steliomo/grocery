/**
 *
 */
package mz.co.grocery.persistence.contract.adapter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.persistence.contract.entity.ContractEntity;
import mz.co.grocery.persistence.contract.repository.ContractRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class ContractAdapter implements ContractPort {

	private ContractRepository repository;

	private EntityMapper<ContractEntity, Contract> mapper;

	public ContractAdapter(
			final ContractRepository repository,
			final EntityMapper<ContractEntity, Contract> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<Contract> findPendingContractsForPaymentByCustomerUuid(final String customerUuid, final LocalDate currentDate)
			throws BusinessException {
		return this.repository.findPendingContractsForPaymentByCustomerUuid(customerUuid, currentDate, EntityStatus.ACTIVE);
	}

	@Transactional
	@Override
	public Contract createContract(final UserContext context, final Contract contract) throws BusinessException {
		final ContractEntity entity = this.mapper.toEntity(contract);

		this.repository.create(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public Contract updateContract(final UserContext userContext, final Contract contract) throws BusinessException {
		final ContractEntity entity = this.mapper.toEntity(contract);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Contract findcontractById(final Long contractId) throws BusinessException {
		return this.mapper.toDomain(this.repository.findById(contractId));
	}
}
