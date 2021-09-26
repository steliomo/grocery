/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class RentReport {

	private String name;

	private String reportDate;

	private String unitName;

	private String address;

	private String phoneNumber;

	private String email;

	private String customerName;

	private BigDecimal totalDiscount;

	private BigDecimal grandTotal;

	private List<RentItemReport> rentItemsReport;

	public RentReport() {
	}

	public RentReport(final Rent rent) {
		this.name = "cotacao_" + LocalDateTime.now();

		this.name = this.name.replace("-", "");
		this.name = this.name.replace("/", "");
		this.name = this.name.replace(".", "");
		this.name = this.name.replace(":", "");
		this.name = this.name + ".pdf";

		this.reportDate = rent.getRentDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.unitName = rent.getUnit().getName();
		this.address = rent.getUnit().getAddress();
		this.phoneNumber = rent.getUnit().getPhoneNumber();
		this.email = rent.getUnit().getEmail();
		this.customerName = rent.getCustomer().getName();
		this.totalDiscount = rent.getRentItems().stream().map(RentItem::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.rentItemsReport = rent.getRentItems().stream().map(rentItem -> new RentItemReport(rentItem)).collect(Collectors.toList());
		this.grandTotal = rent.getTotalRent();
	}

	public String getName() {
		return this.name;
	}

	public String getReportDate() {
		return this.reportDate;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public BigDecimal getTotalDiscount() {
		return this.totalDiscount;
	}

	public BigDecimal getGrandTotal() {
		return this.grandTotal;
	}

	public List<RentItemReport> getRentItemsReport() {
		return Collections.unmodifiableList(this.rentItemsReport);
	}

}
