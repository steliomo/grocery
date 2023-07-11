/**
 *
 */
package mz.co.grocery.persistence.contract.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.persistence.contract.entity.ContractEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class ContractRepositoryImpl extends GenericDAOImpl<ContractEntity, Long> implements ContractRepository {

	private EntityMapper<ContractEntity, Contract> mapper;

	public ContractRepositoryImpl(final EntityMapper<ContractEntity, Contract> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Contract> findPendingContractsForPaymentByCustomerUuid(final String customerUuid, final LocalDate currentDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ContractRepository.QUERY_NAME.findPendingContractsForPaymentByCustomer, new ParamBuilder()
				.add("customerUuid", customerUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}
}
