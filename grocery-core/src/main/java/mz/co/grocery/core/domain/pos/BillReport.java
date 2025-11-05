/**
 *
 */
package mz.co.grocery.core.domain.pos;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.sale.Sale;

/**
 * @author Stélio Moiane
 *
 */

public class BillReport implements Document {

	public static final String DOCUMENT_XML_NAME = "reports/sale_bill.jrxml";

	private String filename;

	private Sale sale;

	private List<BillItem> items;

	private DecimalFormat formatter;

	private Map<String, Object> parameters;

	private Clock clock;

	public BillReport(final Sale sale, final Clock clock) {
		this.sale = sale;
		this.clock = clock;

		this.formatter = new DecimalFormat("#,###.00 MT");
		this.parameters = new HashMap<>();

		this.filename = this.generateFilename();
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

	@Override
	public Map<String, Object> getParameters() {

		this.parameters.put("code", StringUtils.leftPad(String.valueOf(this.sale.getId()), Document.LEFT_PAD, Document.PAD_CHAR));
		this.parameters.put("saleDate", this.sale.getSaleDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		this.parameters.put("customerName", this.sale.getCustomer().get().getName());
		this.parameters.put("phoneNumber", this.sale.getCustomer().get().getContact());
		this.parameters.put("grandTotal", this.formatter.format(this.sale.getTotal()));
		this.parameters.put("logoPath", this.sale.getUnit().get().getLogoPath());

		return this.parameters;
	}

	@Override
	public Collection<?> getData() {
		this.items = new ArrayList<>();

		this.sale.getItems().get().forEach(saleItem -> {
			this.items.add(new BillItem(saleItem.getName(), this.formatter.format(saleItem.getUnitPrice()), saleItem.getQuantity().toString(),
					this.formatter.format(saleItem.getTotalSaleItem())));
		});

		return this.items;
	}

	@Override
	public String getXml() {
		return BillReport.DOCUMENT_XML_NAME;
	}

	private String generateFilename() {
		final String time = String.valueOf(this.clock.todayDateTime()).replace("-", "").replace("/", "").replace(".", "").replace(":", "")
				.replace(":", "")
				.replace("T", "");
		return "BILL" + "_" + time + ".pdf";
	}
}
