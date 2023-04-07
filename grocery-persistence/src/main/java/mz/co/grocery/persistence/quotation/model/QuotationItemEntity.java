/**
 *
 */
package mz.co.grocery.persistence.quotation.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

//@Entity
//@Table(name = "QUOTATION_ITEMS")
public class QuotationItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUOTATION_ID", nullable = false)
	private QuotationEntity quotation;

}
