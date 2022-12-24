/**
 *
 */
package mz.co.grocery.core.sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.sale.model.SalePayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SalePaymentService {

	SalePayment payInstallmentSale(UserContext userContext, String saleUuid, BigDecimal paymentValue, LocalDate paymentDate) throws BusinessException;

}
