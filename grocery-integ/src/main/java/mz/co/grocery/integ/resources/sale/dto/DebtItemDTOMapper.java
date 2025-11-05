/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class DebtItemDTOMapper implements DTOMapper<DebtItemDTO, DebtItem> {

	@Override
	public DebtItemDTO toDTO(final DebtItem domain) {
		final DebtItemDTO dto = new DebtItemDTO();

		dto.setDebtDate(domain.getDebtDate());
		dto.setName(domain.getName());
		dto.setQuantity(domain.getQuantity());
		dto.setPrice(domain.getPrice());
		dto.setDebtItemValue(domain.getDebtItemValue());

		return dto;
	}

	@Override
	public DebtItem toDomain(final DebtItemDTO dto) {
		final DebtItem domain = new DebtItem(dto.getDebtDate(), dto.getName(), dto.getQuantity(), dto.getPrice(), dto.getDebtItemValue());
		return domain;
	}

}
