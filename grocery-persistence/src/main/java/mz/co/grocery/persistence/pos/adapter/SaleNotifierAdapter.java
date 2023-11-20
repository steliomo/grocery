/**
 *
 */
package mz.co.grocery.persistence.pos.adapter;

import java.util.HashSet;
import java.util.Set;

import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.pos.out.SaleNotifier;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SaleNotifierAdapter implements SaleNotifier {

	private Set<SaleListner> listners;

	public SaleNotifierAdapter() {
		this.listners = new HashSet<>();
	}

	@Override
	public void registListner(final SaleListner listner) {
		this.listners.add(listner);
	}

	@Override
	public void removeListner(final SaleListner listner) {
		this.listners.remove(listner);
	}

	@Override
	public Sale notify(final UserContext context, Sale sale) throws BusinessException {
		if (this.listners.isEmpty()) {
			throw new BusinessException("regist.listners");
		}

		for (final SaleListner listner : this.listners) {
			sale = listner.send(context, sale);
		}

		return sale;
	}
}
