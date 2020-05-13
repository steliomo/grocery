package mz.co.grocery.core.expense.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

@Repository(ExpenseDAOImpl.NAME)
public class ExpenseDAOImpl extends GenericDAOImpl<Expense, Long> implements ExpenseDAO {

	public static final String NAME = "mz.co.grocery.core.expense.dao.ExpenseDAOImpl";

	@Override
	public BigDecimal findValueByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {

		return this.findSingleByNamedQuery(ExpenseDAO.QUERY_NAME.findValueByGroceryAndPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate)
				.add("endDate", endDate).add("entityStatus", entityStatus).process(),
				BigDecimal.class);
	}

	@Override
	public List<ExpenseReport> findMonthlyByGroceryAndPeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {

		return this.findByNamedQuery(ExpenseDAO.QUERY_NAME.findMonthlyByGroceryAndPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process(),
				ExpenseReport.class);
	}

}
