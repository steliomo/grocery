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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.expense.in.ExpenseTypePort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.expense.ExpenseType;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.expense.dto.ExpenseTypeDTO;
import mz.co.grocery.integ.resources.expense.dto.ExpensesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Path("expenses-type")
@WebAdapter
public class ExpenseTypeResource extends AbstractResource {

	@Inject
	private ExpenseTypePort expenseTypePort;

	@Autowired
	private DTOMapper<ExpenseTypeDTO, ExpenseType> expenseTypeMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEpenseType(final ExpenseTypeDTO expenseTypeDTO) throws BusinessException {

		ExpenseType expenseType = this.expenseTypeMapper.toDomain(expenseTypeDTO);

		expenseType = this.expenseTypePort.createExpenseType(this.getContext(), expenseType);

		return Response.ok(this.expenseTypeMapper.toDTO(expenseType)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExpenseType(final ExpenseTypeDTO expenseTypeDTO) throws BusinessException {

		ExpenseType expenseType = this.expenseTypeMapper.toDomain(expenseTypeDTO);

		expenseType = this.expenseTypePort.updateExpenseType(this.getContext(), expenseType);

		return Response.ok(this.expenseTypeMapper.toDTO(expenseType)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllExpensesType(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final List<ExpenseType> expensesType = this.expenseTypePort.findAllExpensesType(currentPage, maxResult);
		final Long totalItems = this.expenseTypePort.count();

		final List<ExpenseTypeDTO> expenseTypeDTOs = expensesType.stream().map(expenseType -> this.expenseTypeMapper.toDTO(expenseType))
				.collect(Collectors.toList());

		final ExpensesDTO expensesDTO = new ExpensesDTO().setExpenseTypeDTOs(expenseTypeDTOs).setTotalItems(totalItems);

		return Response.ok(expensesDTO).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{expenseTypeUuid}")
	public Response findExpenseTypeByUuid(@PathParam("expenseTypeUuid") final String expenseTypeUuid)
			throws BusinessException {

		final ExpenseType expenseType = this.expenseTypePort.findExpensesTypeByUuid(expenseTypeUuid);

		return Response.ok(this.expenseTypeMapper.toDTO(expenseType)).build();
	}
}
