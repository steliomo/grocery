/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.integ.resources.common.EnumDTO;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class SaleDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private CustomerDTO customerDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate saleDate;

	private BigDecimal billing;

	private BigDecimal total;

	private SaleType saleType;

	private BigDecimal totalPaid;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate dueDate;

	private EnumDTO saleStatus;

	private EnumDTO deliveryStatus;

	private Set<SaleItemDTO> saleItemsDTO;

	private List<GuideDTO> guidesDTO;

	public SaleDTO() {
		this.saleItemsDTO = new HashSet<>();
		this.guidesDTO = new ArrayList<>();
	}

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public void setBilling(final BigDecimal billing) {
		this.billing = billing;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public Optional<Set<SaleItemDTO>> getSaleItemsDTO() {
		return Optional.ofNullable(this.saleItemsDTO);
	}

	public void addSaleItemsDTO(final SaleItemDTO saleItemDTO) {
		this.saleItemsDTO.add(saleItemDTO);
	}

	public Optional<CustomerDTO> getCustomerDTO() {
		return Optional.ofNullable(this.customerDTO);
	}

	public void setCustomerDTO(final CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public SaleType getSaleType() {
		return this.saleType;
	}

	public void setSaleType(final SaleType saleType) {
		this.saleType = saleType;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public LocalDate getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Optional<EnumDTO> getSaleStatus() {
		return Optional.ofNullable(this.saleStatus);
	}

	public void setSaleStatus(final EnumDTO saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Optional<EnumDTO> getDeliveryStatus() {
		return Optional.ofNullable(this.deliveryStatus);
	}

	public void setDeliveryStatus(final EnumDTO deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Optional<List<GuideDTO>> getGuidesDTO() {

		this.guidesDTO.sort(Comparator.comparing(GuideDTO::getId));
		return Optional.ofNullable(this.guidesDTO);
	}

	public void addGuideDTO(final GuideDTO guideDTO) {
		this.guidesDTO.add(guideDTO);
	}
}
