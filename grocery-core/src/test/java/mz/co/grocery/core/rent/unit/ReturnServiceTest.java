/**
 *
 */
package mz.co.grocery.core.rent.unit;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.ReturnItemTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.dao.ReturnItemDAO;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.grocery.core.rent.model.ReturnStatus;
import mz.co.grocery.core.rent.service.ReturnService;
import mz.co.grocery.core.rent.service.ReturnServiceImpl;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class ReturnServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final ReturnService returnService = new ReturnServiceImpl();

	@Mock
	private RentItemDAO rentItemDAO;

	@Mock
	private ReturnItemDAO returnItemDAO;

	@Mock
	private StockDAO stockDAO;

	@Captor
	private ArgumentCaptor<Stock> stockCaptor;

	@Captor
	private ArgumentCaptor<RentItem> rentItemCaptor;

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldReturnRentItems() throws BusinessException {

		final List<ReturnItem> returnItems = EntityFactory.gimme(ReturnItem.class, 10, ReturnItemTemplate.VALID, processor -> {
			if (processor instanceof ReturnItem) {
				final ReturnItem item = (ReturnItem) processor;
				item.setQuantity(new BigDecimal(10));
				item.getRentItem().setReturnable();
			}
		});

		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT, processor -> {
			if (processor instanceof RentItem) {
				final RentItem item = (RentItem) processor;
				item.setQuantity(new BigDecimal(10));
				item.setReturnable();
				item.setItem(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
				item.addReturnItem(returnItems.get(0));
			}
		});

		Mockito.when(this.rentItemDAO.fetchByUuid(null)).thenReturn(rentItem);

		final UserContext context = this.getUserContext();
		final List<ReturnItem> returnedItens = this.returnService.returnItems(context, returnItems);

		Assert.assertEquals(10, returnedItens.size());
		Mockito.verify(this.rentItemDAO, Mockito.times(10)).update(context, rentItem);

		for (final ReturnItem returnedItem : returnedItens) {
			Assert.assertEquals(ReturnStatus.COMPLETE, returnedItem.getRentItem().getReturnStatus());

			Mockito.verify(this.rentItemDAO, Mockito.times(returnedItens.size())).update(ArgumentMatchers.eq(context), this.rentItemCaptor.capture());
			Mockito.verify(this.stockDAO, Mockito.times(returnedItens.size())).update(ArgumentMatchers.eq(context), this.stockCaptor.capture());

			Mockito.verify(this.returnItemDAO).create(context, returnedItem);
		}
	}

	@Test(expected = BusinessException.class)
	public void shouldNotReturnInvalidItem() throws BusinessException {

		final List<ReturnItem> returnItems = EntityFactory.gimme(ReturnItem.class, 1, ReturnItemTemplate.INVALID, processor -> {
			if (processor instanceof ReturnItem) {
				final ReturnItem item = (ReturnItem) processor;
				item.getRentItem().setReturnable();
			}
		});

		this.returnService.returnItems(this.getUserContext(), returnItems);
	}
}
