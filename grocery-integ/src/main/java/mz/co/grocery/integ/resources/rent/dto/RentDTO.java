/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.grocery.integ.resources.util.EnumDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentDTO extends GenericDTO<Rent> {

	private GroceryDTO unitDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate rentDate;

	private CustomerDTO customerDTO;

	private BigDecimal totalEstimated;

	private BigDecimal totalCalculated;

	private BigDecimal totalPaid;

	private BigDecimal totalToPay;

	private BigDecimal totalToRefund;

	private int totalItems;

	private EnumDTO paymentStatus;

	private List<RentItemDTO> rentItemsDTO;

	private List<RentPaymentDTO> rentPaymentsDTO;

	private List<GuideDTO> guidesDTO;

	public RentDTO() {
	}

	public RentDTO(final Rent rent) {
		super(rent);
	}

	public RentDTO(final Rent rent, final ApplicationTranslator translator) {
		super(rent);
		this.paymentStatus = new EnumDTO(rent.getPaymentStatus().toString(), translator.getTranslation(rent.getPaymentStatus().toString()));
	}

	@Override
	public void mapper(final Rent rent) {
		this.totalEstimated = rent.getTotalEstimated();
		this.totalCalculated = rent.getTotalCalculated();
		this.totalPaid = rent.getTotalPayment();
		this.totalToPay = rent.getTotalToPay();
		this.totalToRefund = rent.totalToRefund();
		this.totalItems = rent.getRentItems().size();

		if (ProxyUtil.isInitialized(rent.getUnit())) {
			this.unitDTO = new GroceryDTO(rent.getUnit());
		}

		if (ProxyUtil.isInitialized(rent.getCustomer())) {
			this.customerDTO = new CustomerDTO(rent.getCustomer());
		}

		this.rentDate = rent.getRentDate();

		if (this.rentItemsDTO == null) {
			this.rentItemsDTO = new ArrayList<>();
		}

		rent.getRentItems().forEach(rentItem -> this.rentItemsDTO.add(new RentItemDTO(rentItem)));

		if (this.guidesDTO == null) {
			this.guidesDTO = new ArrayList<GuideDTO>();
		}

		rent.getGuides().forEach(guide -> this.guidesDTO.add(new GuideDTO(guide)));

		if (this.rentPaymentsDTO == null) {
			this.rentPaymentsDTO = new ArrayList<>();
		}

		rent.getRentPayments().forEach(payment -> this.rentPaymentsDTO.add(new RentPaymentDTO(payment)));
	}

	@Override
	public Rent get() {

		final Rent rent = this.get(new Rent());

		rent.setUnit(this.unitDTO == null ? null : this.unitDTO.get());
		rent.setRentDate(this.rentDate);
		rent.setCustomer(this.customerDTO == null ? null : this.customerDTO.get());
		this.rentItemsDTO.forEach(rentItemDTO -> rent.addRentItem(rentItemDTO.get()));

		if (this.rentPaymentsDTO == null) {
			this.rentPaymentsDTO = new ArrayList<>();
		}

		this.rentPaymentsDTO.forEach(rentPaymentsDTO -> rent.addRentPayment(rentPaymentsDTO.get()));

		return rent;
	}

	public GroceryDTO getUnitDTO() {
		return this.unitDTO;
	}

	public LocalDate getRentDate() {
		return this.rentDate;
	}

	public CustomerDTO getCustomerDTO() {
		return this.customerDTO;
	}

	public int getTotalItems() {
		return this.totalItems;
	}

	public BigDecimal getTotalEstimated() {
		return this.totalEstimated;
	}

	public BigDecimal getTotalCalculated() {
		return this.totalCalculated;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public BigDecimal getTotalToPay() {
		return this.totalToPay;
	}

	public BigDecimal getTotalToRefund() {
		return this.totalToRefund;
	}

	public List<RentItemDTO> getRentItemsDTO() {
		return this.rentItemsDTO;
	}

	public List<RentPaymentDTO> getRentPaymentsDTO() {
		if (this.rentPaymentsDTO == null) {
			return this.rentPaymentsDTO;
		}

		this.rentPaymentsDTO.sort(Comparator.comparing(RentPaymentDTO::getId));
		return this.rentPaymentsDTO;
	}

	public EnumDTO getPaymentStatus() {
		return this.paymentStatus;
	}

	public List<GuideDTO> getGuidesDTO() {
		if (this.guidesDTO == null) {
			return this.guidesDTO;
		}

		this.guidesDTO.sort(Comparator.comparing(GuideDTO::getId));
		return this.guidesDTO;
	}
}
