/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class SalePaymentDTOMapper extends AbstractDTOMapper<SalePaymentDTO, SalePayment> implements DTOMapper<SalePaymentDTO, SalePayment> {

	@Override
	public SalePaymentDTO toDTO(final SalePayment domain) {
		final SalePaymentDTO dto = new SalePaymentDTO();

		domain.getSale().ifPresent(sale -> dto.setSaleUuid(sale.getUuid()));
		dto.setPaymentValue(domain.getPaymentValue());
		dto.setPaymentDate(domain.getPaymentDate());

		return this.toDTO(dto, domain);
	}

	@Override
	public SalePayment toDomain(final SalePaymentDTO dto) {
		final Sale sale = new Sale();

		sale.setUuid(dto.getSaleUuid());

		final SalePayment domain = new SalePayment(sale, dto.getPaymentValue(), dto.getPaymentDate());

		return this.toDomain(dto, domain);
	}
}
