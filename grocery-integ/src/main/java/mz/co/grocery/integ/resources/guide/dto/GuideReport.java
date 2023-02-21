/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class GuideReport {

	public static final String REPORT_XML_NAME = "reports/guide.jrxml";

	private final List<GuideItemReport> guideItems = new ArrayList<>();

	private Map<String, Object> parameters;

	private String fileName;

	public GuideReport(final GuideDTO guideDTO, final ApplicationTranslator translator) {
		this.setParameters(guideDTO, translator);
		this.addItems(guideDTO);
		this.generateFileName(guideDTO);
	}

	private void generateFileName(final GuideDTO guideDTO) {
		final String time = String.valueOf(LocalDateTime.now()).replace("-", "").replace("/", "").replace(".", "").replace(":", "").replace(":", "")
				.replace("T", "");
		this.fileName = guideDTO.getType().toString() + "_" + time + ".pdf";

	}

	private void addItems(final GuideDTO guideDTO) {
		Integer item = 1;

		for (final GuideItemDTO guideItemDTO : guideDTO.getGuideItemsDTO()) {
			final GuideItemReport guideItemReport = new GuideItemReport(item, guideItemDTO.getQuantity(), guideItemDTO.getName());
			this.guideItems.add(guideItemReport);
			item++;
		}
	}

	private void setParameters(final GuideDTO guideDTO, final ApplicationTranslator translator) {
		this.parameters = new HashMap<>();
		this.parameters.put("guideCode", guideDTO.getCode());
		this.parameters.put("guideType", translator.getTranslation(guideDTO.getType().toString()));
		this.parameters.put("guideDate", guideDTO.getIssueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		this.parameters.put("customerName", guideDTO.getCustomerDTO().getName());
		this.parameters.put("address", guideDTO.getCustomerDTO().getAddress());
		this.parameters.put("nuit", "");
		this.parameters.put("phoneNumber", guideDTO.getCustomerDTO().getContact());
	}

	public List<GuideItemReport> getGuideItems() {
		return this.guideItems;
	}

	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public String getFileName() {
		return this.fileName;
	}
}
