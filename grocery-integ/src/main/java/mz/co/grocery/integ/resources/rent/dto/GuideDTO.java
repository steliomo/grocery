/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GuideDTO extends GenericDTO<Guide> {

	private static final int LEFT_PAD = 5;

	private static final char PAD_CHAR = '0';

	private GuideType type;

	private RentDTO rentDTO;

	private LocalDate issueDate;

	private List<GuideItemDTO> guideItemsDTO;

	private String fileName;

	private String code;

	public GuideDTO() {
	}

	public GuideDTO(final Guide guide) {
		super(guide);
	}

	@Override
	public void mapper(final Guide guide) {

		if (guide.getRent() == null || guide.getRent().getGuides() == null || !guide.getRent().getGuides().isEmpty()) {
			guide.setRent(null);
		}

		if (ProxyUtil.isInitialized(guide.getRent())) {
			this.rentDTO = new RentDTO(guide.getRent());
		}

		this.type = guide.getType();
		this.issueDate = guide.getIssueDate();
		this.guideItemsDTO = guide.getGuideItems().stream().map(guideItem -> new GuideItemDTO(guideItem)).collect(Collectors.toList());
	}

	@Override
	public Guide get() {

		final Guide guide = this.get(new Guide());
		guide.setRent(this.rentDTO.get());
		guide.setIssueDate(this.issueDate);
		guide.setType(this.type);
		this.guideItemsDTO.forEach(guideItemDTO -> {
			guide.addGuideItem(guideItemDTO.get());
		});

		return guide;
	}

	public GuideType getType() {
		return this.type;
	}

	public RentDTO getRentDTO() {
		return this.rentDTO;
	}

	public LocalDate getIssueDate() {
		return this.issueDate;
	}

	public List<GuideItemDTO> getGuideItemsDTO() {
		return Collections.unmodifiableList(this.guideItemsDTO);
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setIssueDate(final LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getCode() {

		if (this.getId() == null) {
			return this.code;
		}

		this.code = StringUtils.leftPad(String.valueOf(this.getId()), GuideDTO.LEFT_PAD, GuideDTO.PAD_CHAR);
		return this.code;
	}
}