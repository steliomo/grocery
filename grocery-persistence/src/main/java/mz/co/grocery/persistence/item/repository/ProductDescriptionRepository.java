/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import mz.co.grocery.persistence.item.entity.ProductDescriptionEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDescriptionRepository extends GenericDAO<ProductDescriptionEntity, Long> {

	class QUERY {
		public static final String fetchdAll = "SELECT pd FROM ProductDescriptionEntity pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.entityStatus = :entityStatus GROUP BY pd.id ORDER BY pd.product.name";

		public static final String count = "SELECT COUNT(pd.id) FROM ProductDescriptionEntity pd WHERE pd.entityStatus = :entityStatus";

		public static final String fetchByDescription = "SELECT pd FROM ProductDescriptionEntity pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE CONCAT(p.name, ' ', pd.description) LIKE :description AND pd.entityStatus = :entityStatus GROUP BY pd.id ORDER BY pd.product.name";

		public static final String fetchByUuid = "SELECT pd FROM ProductDescriptionEntity pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.uuid = :productDescriptionUuid AND pd.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String fetchdAll = "ProductDescriptionEntity.fetchdAll";

		public static final String count = "ProductDescriptionEntity.count";

		public static final String fetchByDescription = "ProductDescriptionEntity.fetchByDescription";

		public static final String fetchByUuid = "ProductDescriptionEntity.fetchByUuid";
	}

	List<ProductDescriptionEntity> fetchdAll(int currentPage, int maxResult, EntityStatus entityStatus)
			throws BusinessException;

	@Override
	Long count(EntityStatus entityStatus) throws BusinessException;

	List<ProductDescriptionEntity> fetchByDescription(String description, EntityStatus entityStatus) throws BusinessException;

	ProductDescriptionEntity fetchByUuid(String productDescriptionUuid, EntityStatus entityStatus) throws BusinessException;
}
