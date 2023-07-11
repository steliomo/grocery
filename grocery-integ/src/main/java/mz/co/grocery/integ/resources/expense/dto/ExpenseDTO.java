/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ExpenseDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private ExpenseTypeDTO expenseTypeDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate datePerformed;

	private BigDecimal expenseValue;

	private String description;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public Optional<ExpenseTypeDTO> getExpenseTypeDTO() {
		return Optional.ofNullable(this.expenseTypeDTO);
	}

	public void setExpenseTypeDTO(final ExpenseTypeDTO expenseTypeDTO) {
		this.expenseTypeDTO = expenseTypeDTO;
	}

	public LocalDate getDatePerformed() {
		return this.datePerformed;
	}

	public void setDatePerformed(final LocalDate datePerformed) {
		this.datePerformed = datePerformed;
	}

	public BigDecimal getExpenseValue() {
		return this.expenseValue;
	}

	public void setExpenseValue(final BigDecimal expenseValue) {
		this.expenseValue = expenseValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
