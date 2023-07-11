/**
 *
 */
package mz.co.grocery.persistence.contract.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.contract.entity.ContractPaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class ContractPaymentRepositoryImpl extends GenericDAOImpl<ContractPaymentEntity, Long> implements ContractPaymentRepository {

}
