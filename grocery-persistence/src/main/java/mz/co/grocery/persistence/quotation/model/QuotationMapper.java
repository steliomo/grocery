/**
 *
 */
package mz.co.grocery.persistence.quotation.model;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.quotation.Quotation;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class QuotationMapper {

	public QuotationEntity mapToEntity(final Quotation quotation) {

		final QuotationEntity quotationEntity = new QuotationEntity();
		quotationEntity.setId(quotation.getId());
		quotationEntity.setUuid(quotation.getUuid());
		quotationEntity.setCustomer(quotation.getCustomer());
		quotationEntity.setType(quotation.getType());
		quotationEntity.setIssueDate(quotation.getIssueDate());
		quotationEntity.setStatus(quotation.getStatus());

		return quotationEntity;
	}

	public Quotation mapToDomain(final QuotationEntity quotationEntity) {

		final Quotation quotation = new Quotation(quotationEntity.getType(), quotationEntity.getStatus());
		quotation.setId(quotationEntity.getId());
		quotation.setUuid(quotationEntity.getUuid());
		quotation.setCustomer(quotationEntity.getCustomer());
		quotation.setIssueDate(quotationEntity.getIssueDate());
		// quotation.setItems(quotationEntity.getItems());

		return quotation;
	}
}
