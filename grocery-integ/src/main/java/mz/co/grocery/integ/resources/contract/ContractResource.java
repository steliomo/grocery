/**
 *
 */
package mz.co.grocery.integ.resources.contract;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.model.ContractType;
import mz.co.grocery.core.contract.service.ContractPaymentService;
import mz.co.grocery.core.contract.service.ContractQueryService;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.contract.dto.ContractDTO;
import mz.co.grocery.integ.resources.contract.dto.ContractPaymentDTO;
import mz.co.grocery.integ.resources.contract.dto.ContractsDTO;
import mz.co.grocery.integ.resources.util.EnumsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("contracts")
@Service(ContractResource.NAME)
public class ContractResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.contract.ContractResource";

	@Inject
	private ContractService contractService;

	@Inject
	private ContractPaymentService contractPaymentService;

	@Inject
	private ContractQueryService contractQueryService;

	@Inject
	private ApplicationTranslator translator;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response celebrateContract(final ContractDTO contractDTO) throws BusinessException {

		this.contractService.celebrateContract(this.getContext(), contractDTO.get());

		return Response.ok(contractDTO).build();
	}

	@Path("contract-payment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response performContractPayment(final ContractPaymentDTO contractPaymentDTO) throws BusinessException {

		this.contractPaymentService.performContractPayment(this.getContext(), contractPaymentDTO.get());

		return Response.ok(contractPaymentDTO).build();
	}

	@Path("types")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findContractTypes() {
		final EnumsDTO<ContractType> contractTypes = new EnumsDTO<>(this.translator, ContractType.values());
		return Response.ok(contractTypes).build();
	}

	@Path("by-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPendingContractsForPaymentByCustomer(@QueryParam("customerUuid") final String customerUuid,
			@QueryParam("currentDate") final String currentDate)
					throws BusinessException {

		final LocalDateAdapter adapter = new LocalDateAdapter();
		final LocalDate localDate = adapter.unmarshal(currentDate);

		final List<Contract> contracts = this.contractQueryService.findPendingContractsForPaymentByCustomerUuid(customerUuid, localDate);

		return Response.ok(new ContractsDTO(contracts, this.translator)).build();
	}
}
