/**
 *
 */
package mz.co.grocery.persistence.unit.repository;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface UnitRepository extends GenericDAO<UnitEntity, Long> {

	class QUERY {
		public static final String findAllIds = "SELECT u.id FROM UnitEntity u WHERE u.entityStatus = :entityStatus";
		public static final String findAll = "SELECT u FROM UnitEntity u WHERE u.id IN (:unitIds)";
		public static final String findByName = "SELECT g FROM UnitEntity g WHERE g.name LIKE :unitName AND g.entityStatus = :entityStatus";
		public static final String findUnitsWithDailySales = "SELECT u FROM SaleEntity s INNER JOIN s.unit u WHERE s.saleDate = :saleDate AND s.entityStatus = :entityStatus AND u.entityStatus = :entityStatus GROUP BY u.id";
	}

	class QUERY_NAME {
		public static final String findAllIds = "UnitEntity.findAllIds";
		public static final String findAll = "UnitEntity.findAll";
		public static final String findByName = "UnitEntity.findByName";
		public static final String findUnitsWithDailySales = "UnitEntity.findUnitsWithDailySales";
	}

	List<UnitEntity> findAll(int currentPage, int maxResul, EntityStatus entityStatus) throws BusinessException;

	List<UnitEntity> findByName(String unitName, EntityStatus entityStatus) throws BusinessException;

	List<UnitEntity> findUnitsWithDailySales(LocalDate saleDate, EntityStatus entityStatus) throws BusinessException;
}
