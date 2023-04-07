/**
 *
 */
package mz.co.grocery.persistence.quotation.adapter;

import mz.co.grocery.core.application.quotation.out.SaveQuotationItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class QuotationItemAdapter implements SaveQuotationItemPort {

	@Override
	public QuotationItem save(final UserContext context, final QuotationItem quotationItem) throws BusinessException {
		return null;
	}
}
