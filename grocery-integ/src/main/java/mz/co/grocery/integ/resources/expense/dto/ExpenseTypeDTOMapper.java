/**
 *
 */
package mz.co.grocery.integ.resources.expense.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ExpenseTypeDTOMapper extends AbstractDTOMapper<ExpenseTypeDTO, ExpenseType> implements DTOMapper<ExpenseTypeDTO, ExpenseType> {

	@Override
	public ExpenseTypeDTO toDTO(final ExpenseType domain) {
		final ExpenseTypeDTO dto = new ExpenseTypeDTO();

		dto.setExpenseTypeCategory(domain.getExpenseTypeCategory());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());

		return this.toDTO(dto, domain);
	}

	@Override
	public ExpenseType toDomain(final ExpenseTypeDTO dto) {
		final ExpenseType domain = new ExpenseType();

		domain.setExpenseTypeCategory(dto.getExpenseTypeCategory());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());

		return this.toDomain(dto, domain);
	}
}
