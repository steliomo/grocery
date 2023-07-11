/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.sale.SaleBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class DeliveryGuideTest extends AbstractIntegServiceTest {

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private IssueGuideUseCase issueGuideUseCase;

	@Inject
	@BeanQualifier(DeliveryGuideIssuer.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Inject
	private SaleItemPort saleItemPort;

	@Test
	public void shouldIssueDeliveryGuide() throws BusinessException {
		this.issueGuideUseCase.setGuideIssuer(this.deliveryGuideIssuer);

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now())
				.build();
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);

		guide.setSale(sale);

		for (SaleItem saleItem : sale.getItems().get()) {
			saleItem = this.saleItemPort.createSaleItem(this.getUserContext(), saleItem);

			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		}

		this.issueGuideUseCase.issueGuide(this.getUserContext(), guide);

		Assert.assertNotNull(guide.getIssueDate());
	}
}
