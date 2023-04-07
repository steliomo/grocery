/**
 *
 */
package mz.co.grocery.core.guide.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.domain.report.Report;
import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class GuideReport implements Report {

	public static final String REPORT_XML_NAME = "reports/guide.jrxml";

	private final List<GuideItemReport> guideItems = new ArrayList<>();

	private Map<String, Object> parameters;

	private String fileName;

	private final String code;

	public GuideReport(final Guide guide, final ApplicationTranslator translator) {
		this.code = StringUtils.leftPad(String.valueOf(guide.getId()), Report.LEFT_PAD, Report.PAD_CHAR);
		this.setParameters(guide, translator);
		this.addItems(guide);
		this.generateFileName(guide);
	}

	private void generateFileName(final Guide guide) {
		final String time = String.valueOf(guide.getCreatedAt()).replace("-", "").replace("/", "").replace(".", "").replace(":", "").replace(":", "")
				.replace("T", "");
		this.fileName = guide.getType().toString() + "_" + time + ".pdf";
	}

	private void addItems(final Guide guide) {
		Integer item = 1;

		for (final GuideItem guideItem : guide.getGuideItems()) {
			final GuideItemReport guideItemReport = new GuideItemReport(item, guideItem.getQuantity(), guideItem.getName());
			this.guideItems.add(guideItemReport);
			item++;
		}
	}

	private void setParameters(final Guide guide, final ApplicationTranslator translator) {
		this.parameters = new HashMap<>();
		this.parameters.put("guideCode", this.getCode());
		this.parameters.put("guideType", translator.getTranslation(guide.getType().toString()));
		this.parameters.put("guideDate", guide.getIssueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		this.parameters.put("customerName", guide.getCustomer().getName());
		this.parameters.put("address", guide.getCustomer().getAddress());
		this.parameters.put("nuit", "");
		this.parameters.put("phoneNumber", guide.getCustomer().getContact());
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public Collection<?> getData() {
		return this.guideItems;
	}

	@Override
	public String getXml() {
		return GuideReport.REPORT_XML_NAME;
	}
}
