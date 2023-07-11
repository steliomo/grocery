/**
 *
 */
package mz.co.grocery.core.application.sale.in;

import java.math.BigDecimal;
import java.time.LocalDate;

import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SalePaymentUseCase {

	SalePayment payInstallmentSale(UserContext userContext, String saleUuid, BigDecimal paymentValue, LocalDate paymentDate) throws BusinessException;

}
