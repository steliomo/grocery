/**
 *
 */
package mz.co.grocery.integ.resources.rent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentQueryService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.rent.service.ReturnService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.rent.dto.RentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentPaymentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentReport;
import mz.co.grocery.integ.resources.rent.dto.RentsDTO;
import mz.co.grocery.integ.resources.rent.dto.ReturnItemDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Stélio Moiane
 *
 */

@Path("rents")
@Service(RentResource.NAME)
public class RentResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.rent.RentResource";

	private final String FILE_DIR = "/opt/grocery/data/";

	@Inject
	private RentService rentService;

	@Inject
	private RentQueryService rentQueryService;

	@Inject
	private RentPaymentService rentPaymentService;

	@Inject
	private ReturnService returnService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rent(final RentDTO rentDTO) throws BusinessException {

		this.rentService.rent(this.getContext(), rentDTO.get());

		return Response.ok(rentDTO).build();
	}

	@Path("pending-payments-by-customer{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPendingPaymentRentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentQueryService.findPendingPaymentRentsByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents)).build();
	}

	@Path("payments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePayment(final RentPaymentDTO rentPaymentDTO) throws BusinessException {

		this.rentPaymentService.makeRentPayment(this.getContext(), rentPaymentDTO.get());

		return Response.ok(rentPaymentDTO).build();
	}

	@Path("pending-devolutions-by-customer{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchPendingDevolutionRentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentQueryService.fetchPendingDevolutionRentsByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents)).build();
	}

	@Path("return-items")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnItems(final List<ReturnItemDTO> returnItemsDTO) throws BusinessException {

		final List<ReturnItem> returnItems = returnItemsDTO.stream().map(returnItem -> returnItem.get()).collect(Collectors.toList());

		this.returnService.returnItems(this.getContext(), returnItems);

		return Response.ok(returnItemsDTO).build();
	}

	@Path("process-quotation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processQuotation(final RentDTO rentDTO) throws BusinessException, JRException, FileNotFoundException {

		final RentReport rentReport = new RentReport(rentDTO.get());

		final JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(this.FILE_DIR + "reports/quotation.jrxml"));

		final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rentReport.getRentItemsReport());

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("quotationDate", rentReport.getReportDate());
		parameters.put("unitName", rentReport.getUnitName());
		parameters.put("address", rentReport.getAddress());
		parameters.put("phoneNumber", rentReport.getPhoneNumber());
		parameters.put("email", rentReport.getEmail());
		parameters.put("customerName", rentReport.getCustomerName());
		parameters.put("totalDiscount", rentReport.getTotalDiscount());
		parameters.put("grandTotal", rentReport.getGrandTotal());

		final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint, this.FILE_DIR + rentReport.getName());

		return Response.ok(rentReport).build();
	}

	@Path("quotation/{fileName}")
	@GET
	@Produces("application/pdf")
	public Response displayQuotation(@PathParam("fileName") final String fileName) throws BusinessException {

		final File file = new File(this.FILE_DIR + fileName);
		return Response.ok(file).header("Content-Disposition", "attachment; filename=" + fileName).build();
	}
}
