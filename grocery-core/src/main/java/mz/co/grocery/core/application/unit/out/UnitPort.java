/**
 *
 */
package mz.co.grocery.core.application.unit.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface UnitPort {

	Unit createUnit(UserContext userContext, Unit unit) throws BusinessException;

	Unit updateUnit(UserContext context, Unit unit) throws BusinessException;

	List<Unit> findAllGroceries(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	Unit findByUuid(String groceryUuid) throws BusinessException;

	List<Unit> findUnitsByName(String unitName) throws BusinessException;

	List<Unit> findUnitsWithDailySales(LocalDate saleDate) throws BusinessException;
}
