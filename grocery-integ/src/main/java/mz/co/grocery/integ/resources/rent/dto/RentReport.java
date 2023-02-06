/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class RentReport {

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
		final String time = String.valueOf(LocalDateTime.now()).replace("-", "").replace("/", "").replace(".", "").replace(":", "").replace(":", "")
				.replace("T", "");

		this.name = "QUOTATION_" + time + ".pdf";
		this.reportDate = rent.getRentDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.address = rent.getCustomer().getAddress();
		this.phoneNumber = rent.getUnit().getPhoneNumber();
		this.customerName = rent.getCustomer().getName();
		this.totalDiscount = rent.getRentItems().stream().map(RentItem::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.rentItemsReport = rent.getRentItems().stream().map(rentItem -> new RentItemReport(rentItem)).collect(Collectors.toList());
		this.grandTotal = rent.getRentItems().stream().map(RentItem::getPlannedTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public String getName() {
		return this.name;
	}

	public List<RentItemReport> getRentItemsReport() {
		return Collections.unmodifiableList(this.rentItemsReport);
	}

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
}
