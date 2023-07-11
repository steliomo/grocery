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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.contract.in.PaymentContractUseCase;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.core.domain.contract.ContractType;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.common.EnumsDTO;
import mz.co.grocery.integ.resources.contract.dto.ContractDTO;
import mz.co.grocery.integ.resources.contract.dto.ContractPaymentDTO;
import mz.co.grocery.integ.resources.contract.dto.ContractsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
@Path("contracts")
@WebAdapter
public class ContractResource extends AbstractResource {

	@Inject
	private CelebrateContractUseCase contractUseCase;

	@Inject
	private PaymentContractUseCase paymentContractUseCase;

	@Inject
	private ContractPort contractPort;

	@Inject
	private ApplicationTranslator translator;

	@Autowired
	private DTOMapper<ContractDTO, Contract> contractMapper;

	@Autowired
	private DTOMapper<ContractPaymentDTO, ContractPayment> contractPaymentMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response celebrateContract(final ContractDTO contractDTO) throws BusinessException {

		Contract contract = this.contractMapper.toDomain(contractDTO);

		contract = this.contractUseCase.celebrateContract(this.getContext(), contract);

		return Response.ok(this.contractMapper.toDTO(contract)).build();
	}

	@Path("contract-payment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response performContractPayment(final ContractPaymentDTO contractPaymentDTO) throws BusinessException {

		ContractPayment contractPayment = this.contractPaymentMapper.toDomain(contractPaymentDTO);

		contractPayment = this.paymentContractUseCase.performContractPayment(this.getContext(), contractPayment);

		return Response.ok(this.contractPaymentMapper.toDTO(contractPayment)).build();
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

		final List<Contract> contracts = this.contractPort.findPendingContractsForPaymentByCustomerUuid(customerUuid, localDate);

		return Response.ok(new ContractsDTO(contracts, this.contractMapper)).build();
	}
}
