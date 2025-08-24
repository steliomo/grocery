/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class SaleReportDocument implements Document {

	public static final String REPORT_XML_NAME = "reports/sale_daily_report.jrxml";

	private Unit unit;

	private LocalDate saleDate;

	private List<SaleItemReport> saleItems;

	private String code;

	DecimalFormat formatter;

	public SaleReportDocument(final Unit unit, final LocalDate saleDate, final List<SaleItemReport> saleItems) {
		this.formatter = new DecimalFormat("#,###.00 MT");

		this.unit = unit;
		this.saleDate = saleDate;
		this.saleItems = saleItems;
		this.code = StringUtils.leftPad(String.valueOf(unit.getId()), Document.LEFT_PAD, Document.PAD_CHAR);
	}

	@Override
	public String getFilename() {
		final String time = String.valueOf(this.saleDate).replace("-", "");
		return "DAILY_SALE_" + this.code + "_" + time + "." + Document.PDF;
	}

	@Override
	public Map<String, Object> getParameters() {
		final Map<String, Object> parameters = new HashMap<>();

		parameters.put("code", this.code);
		parameters.put("saleDate", this.saleDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		parameters.put("unitName", this.unit.getName());
		parameters.put("phoneNumber", this.unit.getPhoneNumber());
		parameters.put("grandTotal", this.formatter.format(this.calculateGrandTotal()));
		parameters.put("logoPath", this.unit.getLogoPath());

		return parameters;
	}

	@Override
	public Collection<?> getData() {
		return this.saleItems;
	}

	@Override
	public String getXml() {
		return SaleReportDocument.REPORT_XML_NAME;
	}

	public BigDecimal calculateGrandTotal() {
		return this.saleItems.stream().map(SaleItemReport::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
