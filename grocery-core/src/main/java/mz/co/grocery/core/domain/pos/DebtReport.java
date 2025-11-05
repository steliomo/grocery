/**
 *
 */
package mz.co.grocery.core.domain.pos;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.document.Document;

/**
 * @author Stélio Moiane
 *
 */
public class DebtReport implements Document {

	public static final String DOCUMENT_XML_NAME = "reports/debt_report.jrxml";

	private Debt debt;

	private Clock clock;

	private String filename;

	private DecimalFormat formatter;

	public DebtReport(final Debt debt, final Clock clock) {
		this.debt = debt;
		this.clock = clock;

		this.formatter = new DecimalFormat("#,###.00 MT");

		final String time = String.valueOf(this.clock.todayDateTime()).replace("-", "").replace("/", "").replace(".", "").replace(":", "")
				.replace(":", "")
				.replace("T", "");

		this.filename = "DEBT" + "_" + time + ".pdf";
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

	@Override
	public Map<String, Object> getParameters() {

		final Map<String, Object> parameters = new HashMap<>();

		final Customer customer = this.debt.getCustomer().get();

		parameters.put("code", StringUtils.leftPad(String.valueOf(customer.getId()), Document.LEFT_PAD, Document.PAD_CHAR));
		parameters.put("reportDate", this.clock.todayDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		parameters.put("customerName", customer.getName());
		parameters.put("phoneNumber", customer.getContact());
		parameters.put("subTotal", this.formatter.format(this.debt.getTotalToPay()));
		parameters.put("totalPaid", this.formatter.format(this.debt.getTotalPaid()));
		parameters.put("totalInDebt", this.formatter.format(this.debt.getTotalInDebt()));
		parameters.put("logoPath", customer.getUnit().get().getLogoPath());

		return parameters;
	}

	@Override
	public Collection<?> getData() {
		return this.debt.getDebtItems().get().stream()
				.map(deptItem -> new DebtItemReport(deptItem.getDebtDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), deptItem.getName(),
						deptItem.getQuantity().toString(), this.formatter.format(deptItem.getPrice()),
						this.formatter.format(deptItem.getDebtItemValue())))
				.collect(Collectors.toList());
	}

	@Override
	public String getXml() {
		return DebtReport.DOCUMENT_XML_NAME;
	}
}
