/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.quotation.QuotationType;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationReport implements Document {

	private static final BigDecimal VAT = new BigDecimal(0.16);

	private static final String SALE_REPORT_XML_NAME = "reports/sale_quotation.jrxml";

	private static final String RENT_REPORT_XML_NAME = "reports/rent_quotation.jrxml";

	private Quotation quotation;

	private String fileName;

	private LocalDateTime quotationDateTime;

	private DecimalFormat formatter;

	public QuotationReport(final Quotation quotation, final LocalDateTime quotationDateTime) {
		this.formatter = new DecimalFormat("#,###.00 MT");
		this.quotation = quotation;
		this.quotationDateTime = quotationDateTime;

		final String time = String.valueOf(this.quotationDateTime).replace("-", "").replace("/", "").replace(".", "").replace(":", "")
				.replace(":", "")
				.replace("T", "");
		this.fileName = this.quotation.getType().toString() + "_" + time + "." + Document.PDF;

	}

	@Override
	public String getFilename() {
		return this.fileName;
	}

	@Override
	public Map<String, Object> getParameters() {
		final Map<String, Object> parameters = new HashMap<>();

		final String code = StringUtils.leftPad(String.valueOf(this.quotation.getId()), Document.LEFT_PAD, Document.PAD_CHAR);

		final BigDecimal vat = this.quotation.getTotalValue().multiply(QuotationReport.VAT);
		final BigDecimal grandTotal = this.quotation.getTotalValue().add(vat).subtract(this.quotation.getDiscount());

		parameters.put("code", code);
		parameters.put("quotationDate", this.quotation.getIssueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		parameters.put("customerName", this.quotation.getCustomer().get().getName());
		parameters.put("address", this.quotation.getCustomer().get().getAddress());
		parameters.put("nuit", "NA");
		parameters.put("phoneNumber", this.quotation.getCustomer().get().getContact());
		parameters.put("subTotal", this.formatter.format(this.quotation.getTotalValue()));
		parameters.put("vat", this.formatter.format(vat));
		parameters.put("totalWithVat", this.formatter.format(this.quotation.getTotalValue().add(vat)));
		parameters.put("discount", this.formatter.format(this.quotation.getDiscount()));
		parameters.put("grandTotal", this.formatter.format(grandTotal));

		return parameters;
	}

	@Override
	public Collection<?> getData() {

		Integer order = 1;

		final List<QuotationItemReport> data = new ArrayList<>();

		for (final QuotationItem quotationItem : this.quotation.getItems()) {

			final String price = this.formatter.format(quotationItem.getPrice());
			final String days = quotationItem.getDays().toString();
			final String value = this.formatter.format(quotationItem.getTotal());

			final QuotationItemReport quotationItemReport = new QuotationItemReport(order.toString(), quotationItem.getName(), price,
					quotationItem.getQuantity().toString(), days, value);

			data.add(quotationItemReport);

			order++;
		}

		return data;
	}

	@Override
	public String getXml() {

		if (QuotationType.SALE.equals(this.quotation.getType())) {
			return QuotationReport.SALE_REPORT_XML_NAME;
		}

		return QuotationReport.RENT_REPORT_XML_NAME;
	}
}
