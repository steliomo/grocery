/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.model.ContractType;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.customer.dto.CustomerDTO;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.util.EnumDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ContractDTO extends GenericDTO<Contract> {

	private EnumDTO contractType;

	private String description;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate startDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate endDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate referencePaymentDate;

	private BigDecimal monthlyPayment;

	private BigDecimal totalPaid;

	private GroceryDTO unitDTO;

	private CustomerDTO customerDTO;

	public ContractDTO() {
	}

	public ContractDTO(final Contract contract, final ApplicationTranslator translator) {
		super(contract);
		this.contractType = new EnumDTO(contract.getContractType().toString(), translator.getTranslation(contract.getContractType().toString()));
	}

	public ContractDTO(final Contract contract) {
		super(contract);
		this.contractType = new EnumDTO(contract.getContractType().toString(), null);
	}

	@Override
	public void mapper(final Contract contract) {
		this.description = contract.getDescription();
		this.startDate = contract.getStartDate();
		this.endDate = contract.getEndDate();
		this.referencePaymentDate = contract.getReferencePaymentDate();
		this.monthlyPayment = contract.getMonthlyPayment();
		this.totalPaid = contract.getTotalPaid();
		this.unitDTO = new GroceryDTO(contract.getUnit());
		this.customerDTO = new CustomerDTO(contract.getCustomer());
	}

	@Override
	public Contract get() {

		final Contract contract = this.get(new Contract());
		contract.setContractType(ContractType.valueOf(this.contractType.getValue()));
		contract.setDescription(this.description);
		contract.setStartDate(this.startDate);
		contract.setEndDate(this.endDate);
		contract.setMonthlyPayment(this.monthlyPayment);
		contract.setUnit(this.unitDTO.get());
		contract.setCustomer(this.customerDTO.get());

		return contract;
	}

	public EnumDTO getContractType() {
		return this.contractType;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public LocalDate getReferencePaymentDate() {
		return this.referencePaymentDate;
	}

	public BigDecimal getMonthlyPayment() {
		return this.monthlyPayment;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public GroceryDTO getUnitDTO() {
		return this.unitDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return this.customerDTO;
	}
}
