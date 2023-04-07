/**
 *
 */
package mz.co.grocery.persistence.guide;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GuideTemplate;
import mz.co.grocery.persistence.sale.SaleBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class DeliveryGuideTest extends AbstractIntegServiceTest {

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private GuideService guideService;

	@Inject
	@Qualifier(DeliveryGuideIssuerImpl.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Test
	public void shouldIssueDeliveryGuide() throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);

		final Sale sale = this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.INSTALLMENT)
				.dueDate(LocalDate.now())
				.build();
		final Guide guide = EntityFactory.gimme(Guide.class, GuideTemplate.DELIVERY);

		guide.setSale(sale);

		sale.getItems().forEach(saleItem -> {
			final GuideItem guideItem = new GuideItem();
			guideItem.setQuantity(saleItem.getQuantity());
			guideItem.setSaleItem(saleItem);
			guide.addGuideItem(guideItem);
		});

		this.guideService.issueGuide(this.getUserContext(), guide);

		TestUtil.assertCreation(guide);

		guide.getGuideItems().forEach(guideItem -> {
			TestUtil.assertCreation(guideItem);
		});
	}
}
