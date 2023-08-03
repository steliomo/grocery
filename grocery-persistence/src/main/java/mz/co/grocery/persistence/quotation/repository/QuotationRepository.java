/**
 *
 */
package mz.co.grocery.persistence.quotation.repository;

import java.util.List;

import mz.co.grocery.persistence.quotation.entity.QuotationEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface QuotationRepository extends GenericDAO<QuotationEntity, Long> {

	class QUERY {
		public static final String fetchQuotationsByCustomer = "SELECT q FROM QuotationEntity q INNER JOIN FETCH q.items i LEFT JOIN FETCH i.stock s LEFT JOIN FETCH s.productDescription pd "
				+ "LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH i.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "INNER JOIN FETCH q.customer "
				+ "WHERE q.customer.uuid = :customerUuid AND q.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String fetchQuotationsByCustomer = "QuotationEntity.fetchQuotationsByCustomer";
	}

	List<QuotationEntity> fetchQuotationsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

}
