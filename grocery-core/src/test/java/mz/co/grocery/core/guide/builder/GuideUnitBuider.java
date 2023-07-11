/**
 *
 */
package mz.co.grocery.core.guide.builder;

import java.util.List;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.fixturefactory.GuideItemTemplate;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class GuideUnitBuider {

	private final Guide guide;

	public GuideUnitBuider(final String type) {
		this.guide = EntityFactory.gimme(Guide.class, type);
	}

	public GuideUnitBuider withRentProducts(final int quantity) {
		final List<GuideItem> guideItems = EntityFactory.gimme(GuideItem.class, quantity, GuideItemTemplate.RENT_PRODUCTS);
		guideItems.forEach(guideItem -> this.guide.addGuideItem(guideItem));
		return this;
	}

	public GuideUnitBuider withSaleProducts(final int quantity) {
		final List<GuideItem> guideItems = EntityFactory.gimme(GuideItem.class, quantity, GuideItemTemplate.SALE_PRODUCTS);
		guideItems.forEach(guideItem -> this.guide.addGuideItem(guideItem));
		return this;
	}

	public Guide build() {
		return this.guide;
	}
}
