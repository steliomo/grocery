/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.expense.Expense;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ExpenseDTOMapper extends AbstractDTOMapper<ExpenseDTO, Expense> implements DTOMapper<ExpenseDTO, Expense> {

	private DTOMapper<UnitDTO, Unit> unitMapper;
	private DTOMapper<ExpenseTypeDTO, ExpenseType> expenseTypeMapper;

	public ExpenseDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper, final DTOMapper<ExpenseTypeDTO, ExpenseType> expenseTypeMapper) {
		this.unitMapper = unitMapper;
		this.expenseTypeMapper = expenseTypeMapper;
	}

	@Override
	public ExpenseDTO toDTO(final Expense domain) {
		final ExpenseDTO dto = new ExpenseDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		domain.getExpenseType().ifPresent(expenseType -> dto.setExpenseTypeDTO(this.expenseTypeMapper.toDTO(expenseType)));
		dto.setDatePerformed(domain.getDatePerformed());
		dto.setExpenseValue(domain.getExpenseValue());
		dto.setDescription(domain.getDescription());

		return this.toDTO(dto, domain);
	}

	@Override
	public Expense toDomain(final ExpenseDTO dto) {
		final Expense domain = new Expense();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		dto.getExpenseTypeDTO().ifPresent(expenseType -> domain.setExpenseType(this.expenseTypeMapper.toDomain(expenseType)));
		domain.setDatePerformed(dto.getDatePerformed());
		domain.setExpenseValue(dto.getExpenseValue());
		domain.setDescription(dto.getDescription());

		return this.toDomain(dto, domain);
	}
}
