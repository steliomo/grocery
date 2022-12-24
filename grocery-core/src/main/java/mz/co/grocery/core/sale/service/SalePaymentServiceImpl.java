/**
 *
 */
package mz.co.grocery.core.sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SalePayment;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(SalePaymentServiceImpl.NAME)
public class SalePaymentServiceImpl extends AbstractService implements SalePaymentService {

	public static final String NAME = "mz.co.grocery.core.sale.service.SalePaymentServiceImpl";

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private SaleDAO saleDAO;

	@Inject
	private SalePaymentDAO salePaymentDAO;

	@Override
	public SalePayment payInstallmentSale(final UserContext userContext, final String saleUuid, final BigDecimal paymentValue,
			final LocalDate paymentDate)
					throws BusinessException {

		final Sale sale = this.saleDAO.findByUuid(saleUuid);

		if (!SaleType.INSTALLMENT.equals(sale.getSaleType())) {
			throw new BusinessException(this.translator.getTranslation("cannot.perform.payment.for.non.installment.sale"));
		}

		if (paymentValue.compareTo(sale.getRemainingPayment()) == BigDecimal.ONE.intValue()) {
			throw new BusinessException(this.translator.getTranslation("the.payment.value.is.higher.than.expected"));
		}

		sale.updateTotalPaid(paymentValue);
		sale.updateSaleStatus();
		this.saleDAO.update(userContext, sale);

		final SalePayment salePayment = new SalePayment(sale, paymentValue, paymentDate);

		this.salePaymentDAO.create(userContext, salePayment);

		return salePayment;
	}

}
