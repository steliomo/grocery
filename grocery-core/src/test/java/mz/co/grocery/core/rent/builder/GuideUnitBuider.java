/**
 *
 */
package mz.co.grocery.core.rent.builder;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.fixturefactory.GuideItemTemplate;
import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideItem;
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

	public GuideUnitBuider(final String type, final LocalDate issueDate) {
		this.guide = EntityFactory.gimme(Guide.class, type);
		this.guide.setIssueDate(issueDate);
		this.guide.getRent().setRentDate(issueDate);
	}

	public GuideUnitBuider withProducts(final int quantity) {
		final List<GuideItem> guideItems = EntityFactory.gimme(GuideItem.class, quantity, GuideItemTemplate.VALID);
		guideItems.forEach(guideItem -> this.guide.addGuideItem(guideItem));
		return this;
	}

	public Guide build() {
		return this.guide;
	}
}
