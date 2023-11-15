/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class RentReport implements Document {

	public static final String REPORT_XML_NAME = "reports/quotation.jrxml";

	private String name;

	private String reportDate;

	private String address;

	private String phoneNumber;

	private String customerName;

	private BigDecimal totalDiscount;

	private BigDecimal grandTotal;

	private List<RentItemReport> rentItemsReport;

	public RentReport() {
	}

	public RentReport(final Rent rent) {
		final String time = String.valueOf(rent.getCreatedAt()).replace("-", "").replace("/", "").replace(".", "").replace(":", "").replace(":", "")
				.replace("T", "");

		this.name = "QUOTATION_" + time + ".pdf";
		this.reportDate = rent.getRentDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.address = rent.getCustomer().get().getAddress();
		this.phoneNumber = rent.getCustomer().get().getContact();
		this.customerName = rent.getCustomer().get().getName();
		this.totalDiscount = rent.getRentItems().get().stream().map(RentItem::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.rentItemsReport = rent.getRentItems().get().stream().map(rentItem -> new RentItemReport(rentItem)).collect(Collectors.toList());
		this.grandTotal = rent.getRentItems().get().stream().map(RentItem::getPlannedTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Map<String, Object> getParameters() {
		final HashMap<String, Object> parameters = new HashMap<>();

		parameters.put("quotationDate", this.reportDate);
		parameters.put("nuit", "");
		parameters.put("address", this.address);
		parameters.put("phoneNumber", this.phoneNumber);
		parameters.put("customerName", this.customerName);
		parameters.put("totalDiscount", this.totalDiscount);
		parameters.put("grandTotal", this.grandTotal);

		return parameters;
	}

	@Override
	public String getFilename() {
		return this.name;
	}

	@Override
	public Collection<?> getData() {
		return Collections.unmodifiableList(this.rentItemsReport);
	}

	@Override
	public String getXml() {
		return RentReport.REPORT_XML_NAME;
	}
}
