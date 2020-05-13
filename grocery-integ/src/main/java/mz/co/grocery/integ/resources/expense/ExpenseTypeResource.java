package mz.co.grocery.integ.resources.expense;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.core.expense.service.ExpenseTypeQueryService;
import mz.co.grocery.core.expense.service.ExpenseTypeService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.expense.dto.ExpenseTypeDTO;
import mz.co.grocery.integ.resources.expense.dto.ExpensesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Path("expenses-type")
@Service(ExpenseTypeResource.NAME)
public class ExpenseTypeResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.expense.ExpenseTypeResource";

	@Inject
	private ExpenseTypeService expenseTypeService;

	@Inject
	private ExpenseTypeQueryService expenseTypeQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEpenseType(final ExpenseTypeDTO expenseTypeDTO) throws BusinessException {
		this.expenseTypeService.createExpenseType(this.getContext(), expenseTypeDTO.get());
		return Response.ok(expenseTypeDTO).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExpenseType(final ExpenseTypeDTO expenseTypeDTO) throws BusinessException {
		final ExpenseType expenseType = this.expenseTypeService.updateExpenseType(this.getContext(),
				expenseTypeDTO.get());
		return Response.ok(new ExpenseTypeDTO(expenseType)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllExpensesType(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<ExpenseType> expensesType = this.expenseTypeQueryService.findAllExpensesType(currentPage, maxResult);
		final Long totalItems = this.expenseTypeQueryService.count();

		final List<ExpenseTypeDTO> expenseTypeDTOs = expensesType.stream()
				.map(expenseType -> new ExpenseTypeDTO(expenseType))
				.collect(Collectors.toList());

		final ExpensesDTO expensesDTO = new ExpensesDTO().setExpenseTypeDTOs(expenseTypeDTOs).setTotalItems(totalItems);

		return Response.ok(expensesDTO).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{expenseTypeUuid}")
	public Response findExpenseTypeByUuid(@PathParam("expenseTypeUuid") final String expenseTypeUuid)
			throws BusinessException {

		final ExpenseType expenseType = this.expenseTypeQueryService.findExpensesTypeByUuid(expenseTypeUuid);

		return Response.ok(new ExpenseTypeDTO(expenseType)).build();
	}

}
