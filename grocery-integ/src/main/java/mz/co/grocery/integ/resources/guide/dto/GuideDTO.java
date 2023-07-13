/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.rent.dto.RentDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class GuideDTO extends GenericDTO {

	private static final int LEFT_PAD = 5;

	private static final char PAD_CHAR = '0';

	private GuideType type;

	private RentDTO rentDTO;

	private SaleDTO saleDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate issueDate;

	private List<GuideItemDTO> guideItemsDTO;

	private String filePath;

	public GuideDTO() {
		this.guideItemsDTO = new ArrayList<>();
	}

	public GuideType getType() {
		return this.type;
	}

	public void setType(final GuideType type) {
		this.type = type;
	}

	public Optional<RentDTO> getRentDTO() {
		return Optional.ofNullable(this.rentDTO);
	}

	public void setRentDTO(final RentDTO rentDTO) {
		this.rentDTO = rentDTO;
	}

	public Optional<SaleDTO> getSaleDTO() {
		return Optional.ofNullable(this.saleDTO);
	}

	public void setSaleDTO(final SaleDTO saleDTO) {
		this.saleDTO = saleDTO;
	}

	public LocalDate getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(final LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public Optional<List<GuideItemDTO>> getGuideItemsDTO() {
		return Optional.ofNullable(this.guideItemsDTO);
	}

	public void addGuideItemDTO(final GuideItemDTO guideItemDTO) {
		this.guideItemsDTO.add(guideItemDTO);
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public String getCode() {
		return StringUtils.leftPad(String.valueOf(this.getId()), GuideDTO.LEFT_PAD, GuideDTO.PAD_CHAR);
	}
}
