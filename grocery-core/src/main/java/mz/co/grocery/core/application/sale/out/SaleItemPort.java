/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleItemPort {

	SaleItem createSaleItem(UserContext context, SaleItem saleItem) throws BusinessException;

	SaleItem updateSaleItem(UserContext userContext, SaleItem saleItem) throws BusinessException;

	SaleItem findByUuid(String uuid) throws BusinessException;

	Optional<SaleItem> findBySaleAndProductUuid(String saleUuid, String productUuid) throws BusinessException;

	Optional<SaleItem> findBySaleAndServiceUuid(String saleUuid, String serviceUuid) throws BusinessException;

	List<SaleItemReport> findSaleItemsByUnitAndPeriod(String uuid, LocalDate startDate, LocalDate endDate) throws BusinessException;
}
