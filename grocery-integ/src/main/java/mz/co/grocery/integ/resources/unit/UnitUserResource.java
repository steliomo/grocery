/**
 *
 */
package mz.co.grocery.integ.resources.unit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.application.unit.out.UnitUserPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.core.domain.unit.UserRole;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.common.UrlTargets;
import mz.co.grocery.integ.resources.mail.Mail;
import mz.co.grocery.integ.resources.mail.MailSenderService;
import mz.co.grocery.integ.resources.unit.dto.UnitUserDTO;
import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.dto.UsersDTO;
import mz.co.grocery.integ.resources.user.service.ResourceOnwnerService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("unit-users")
@WebAdapter
public class UnitUserResource extends AbstractResource {

	@Inject
	private ResourceOnwnerService resourceOnwnerService;

	@Inject
	private UnitUserPort unitUserPort;

	@Inject
	private Mail mail;

	@Inject
	private MailSenderService mailSenderService;

	@Inject
	private UnitPort unitPort;

	@Autowired
	private DTOMapper<UnitUserDTO, UnitUser> unitUserMapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registUnitUser(final UserDTO userDTO) throws BusinessException {

		final String userUuid = this.resourceOnwnerService.createUser(this.getContext(), userDTO);

		UnitUser unitUser = this.unitUserMapper.toDomain(userDTO.getUnitUserDTO());
		unitUser.setUser(userUuid);

		unitUser = this.unitUserPort.createUnitUser(this.getContext(), unitUser);

		return Response.ok(this.unitUserMapper.toDTO(unitUser)).build();
	}

	@GET
	@Path("user-roles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserRoles() throws BusinessException {
		return Response.ok(UserRole.values()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUnitUsers(@QueryParam("currentPage") final int currentPage,
			@QueryParam("maxResult") final int maxResult) throws BusinessException {

		final Long totalItems = this.unitUserPort.count();

		if (totalItems == 0L) {
			return Response.ok(new ArrayList<>()).build();
		}

		final List<UnitUser> unitUsers = this.unitUserPort.fetchAllUnitUsers(currentPage,
				maxResult);

		final Client client = ClientBuilder.newClient();

		final List<UserDTO> userDTOs = unitUsers.stream().map(unitUser -> {

			final UserDTO userDTO = client.target(UrlTargets.ACCOUNT_MODULE)
					.path("users/by-uuid/" + unitUser.getUser()).request(MediaType.APPLICATION_JSON)
					.get(UserDTO.class);

			userDTO.setUnitUserDTO(this.unitUserMapper.toDTO(unitUser));

			return userDTO;

		}).collect(Collectors.toList());

		return Response.ok(new UsersDTO(userDTOs, totalItems)).build();
	}

	@POST
	@Path("add-saler")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSaler(final UserDTO userDTO) throws BusinessException {

		final String userUuid = this.resourceOnwnerService.createUser(this.getContext(), userDTO);
		final UnitUser unitUser = this.unitUserMapper.toDomain(userDTO.getUnitUserDTO());

		unitUser.setUser(userUuid);
		unitUser.setUserRole(UserRole.OPERATOR);
		unitUser.setExpiryDate(LocalDate.now().plusMonths(3));

		this.unitUserPort.createUnitUser(this.getContext(), unitUser);

		this.mail.setMailTemplate("signup-template.txt");
		this.mail.setMailTo(userDTO.getEmail());
		this.mail.setMailSubject(this.mail.getWelcome());

		final Unit grocery = this.unitPort.findByUuid(unitUser.getUnit().get().getUuid());

		this.mail.setParam("fullName", userDTO.getFullName());
		this.mail.setParam("username", userDTO.getUsername());
		this.mail.setParam("password", userDTO.getPassword());
		this.mail.setParam("email", userDTO.getEmail());
		this.mail.setParam("grocery", grocery.getName());
		this.mail.setParam("address", grocery.getAddress());

		this.mailSenderService.send(this.mail);

		return Response.ok(userDTO).build();
	}

	@GET
	@Path("unit-detail/{unitUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUntDetails(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final UnitDetail unitDetail = this.unitUserPort.findUnitDetailsByUuid(unitUuid);

		return Response.ok(unitDetail).build();
	}
}
