package mz.co.grocery.persistence.expense.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.expense.ExpenseReport;
import mz.co.grocery.core.domain.expense.ExpenseTypeCategory;
import mz.co.grocery.persistence.expense.entity.ExpenseEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

@Repository
public class ExpenseRepositoryImpl extends GenericDAOImpl<ExpenseEntity, Long> implements ExpenseRepository {

	@Override
	public BigDecimal findValueByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate, final ExpenseTypeCategory expenseTypeCategory,
			final EntityStatus entityStatus) throws BusinessException {

		return this.findSingleByNamedQuery(ExpenseRepository.QUERY_NAME.findValueByGroceryAndPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate)
				.add("endDate", endDate).add("expenseTypeCategory", expenseTypeCategory)
				.add("entityStatus", entityStatus).process(),
				BigDecimal.class);
	}

	@Override
	public List<ExpenseReport> findMonthlyByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate, final ExpenseTypeCategory expenseTypeCategory,
			final EntityStatus entityStatus) throws BusinessException {

		return this.findByNamedQuery(ExpenseRepository.QUERY_NAME.findMonthlyByGroceryAndPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("expenseTypeCategory", expenseTypeCategory)
				.add("entityStatus", entityStatus).process(),
				ExpenseReport.class);
	}

	@Override
	public List<ExpenseReport> findExpensesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {

		return this.findByNamedQuery(ExpenseRepository.QUERY_NAME.findExpensesByUnitAndPeriod, new ParamBuilder().add("unitUuid", unitUuid)
				.add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus).process(), ExpenseReport.class);
	}

}
