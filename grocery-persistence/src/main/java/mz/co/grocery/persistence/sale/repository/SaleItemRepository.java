/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.util.Optional;

import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleItemRepository extends GenericDAO<SaleItemEntity, Long> {

	class QUERY {
		public static final String findBySaleAndProductUuid = "SELECT si FROM SaleItemEntity si WHERE si.sale.uuid = :saleUuid AND si.stock.uuid = :productUuid AND si.entityStatus = :entityStatus";
		public static final String findBySaleAndServiceUuid = "SELECT si FROM SaleItemEntity si WHERE si.sale.uuid = :saleUuid AND si.serviceItem.uuid = :serviceUuid AND si.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findBySaleAndProductUuid = "SaleItemEntity.findBySaleAndProductUuid";
		public static final String findBySaleAndServiceUuid = "SaleItemEntity.findBySaleAndServiceUuid";
	}

	Optional<SaleItemEntity> findBySaleAndProductUuid(String saleUuid, String productUuid, EntityStatus entityStatus) throws BusinessException;

	Optional<SaleItemEntity> findBySaleAndServiceUuid(String saleUuid, String serviceUuid, EntityStatus entityStatus) throws BusinessException;

}
