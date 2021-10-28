/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
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

	private BigDecimal totalRent;

	private BigDecimal totalPaid;

	private BigDecimal totalToPay;

	private int totalItems;

	private List<RentItemDTO> rentItemsDTO = new ArrayList<>();

	private final List<RentPaymentDTO> rentPaymentsDTO = new ArrayList<>();

	public RentDTO() {
	}

	public RentDTO(final Rent rent) {
		super(rent);
		this.mapper(rent);
	}

	@Override
	public void mapper(final Rent rent) {
		this.totalRent = rent.getTotalRent();
		this.totalPaid = rent.getTotalPayment();
		this.totalToPay = rent.getTotalToPay();
		this.totalItems = rent.getRentItems().size();

		if (ProxyUtil.isInitialized(rent.getUnit())) {
			this.unitDTO = new GroceryDTO(rent.getUnit());
		}

		if (ProxyUtil.isInitialized(rent.getCustomer())) {
			this.customerDTO = new CustomerDTO(rent.getCustomer());
		}

		this.rentDate = rent.getRentDate();

		this.rentItemsDTO = rent.getRentItems().stream().map(rentItem -> new RentItemDTO(rentItem)).collect(Collectors.toList());
	}

	@Override
	public Rent get() {

		final Rent rent = this.get(new Rent());

		rent.setUnit(this.unitDTO.get());
		rent.setRentDate(this.rentDate);
		rent.setCustomer(this.customerDTO.get());
		this.rentItemsDTO.forEach(rentItemDTO -> rent.addRentItem(rentItemDTO.get()));
		this.rentPaymentsDTO.forEach(rentPaymentsDTO -> rent.addRentPayment(rentPaymentsDTO.get()));

		rent.setTotalRent();
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

	public BigDecimal getTotalRent() {
		return this.totalRent;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public BigDecimal getTotalToPay() {
		return this.totalToPay;
	}

	public List<RentItemDTO> getRentItemsDTO() {
		return this.rentItemsDTO;
	}

	public List<RentPaymentDTO> getRentPaymentsDTO() {
		return this.rentPaymentsDTO;
	}
}
