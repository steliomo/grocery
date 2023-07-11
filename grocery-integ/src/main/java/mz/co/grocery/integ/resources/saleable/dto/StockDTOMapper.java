/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.item.dto.ProductDescriptionDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class StockDTOMapper extends AbstractDTOMapper<StockDTO, Stock> implements DTOMapper<StockDTO, Stock> {

	private DTOMapper<UnitDTO, Unit> unitMapper;
	private DTOMapper<ProductDescriptionDTO, ProductDescription> productDescriptionMapper;

	public StockDTOMapper(final DTOMapper<UnitDTO, Unit> unitMapper,
			final DTOMapper<ProductDescriptionDTO, ProductDescription> productDescriptionMapper) {
		this.unitMapper = unitMapper;
		this.productDescriptionMapper = productDescriptionMapper;
	}

	@Override
	public StockDTO toDTO(final Stock domain) {
		final StockDTO dto = new StockDTO();

		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		domain.getProductDescription()
		.ifPresent(productDescription -> dto.setProductDescriptionDTO(this.productDescriptionMapper.toDTO(productDescription)));

		dto.setPurchasePrice(domain.getPurchasePrice());
		dto.setSalePrice(domain.getSalePrice());
		dto.setQuantity(domain.getQuantity());
		dto.setExpireDate(domain.getExpireDate());
		dto.setMinimumStock(domain.getMinimumStock());
		dto.setInventoryDate(domain.getInventoryDate());
		dto.setInventoryQuantity(domain.getInventoryQuantity());
		dto.setStockUpdateDate(domain.getStockUpdateDate());
		dto.setStockUpdateQuantity(domain.getStockUpdateQuantity());
		dto.setRentPrice(domain.getRentPrice());
		dto.setUnitPerM2(domain.getUnitPerM2());

		return this.toDTO(dto, domain);
	}

	@Override
	public Stock toDomain(final StockDTO dto) {
		final Stock domain = new Stock();

		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		dto.getProductDescriptionDTO()
		.ifPresent(productDescription -> domain.setProductDescription(this.productDescriptionMapper.toDomain(productDescription)));

		domain.setPurchasePrice(dto.getPurchasePrice());
		domain.setSalePrice(dto.getSalePrice());
		domain.setQuantity(dto.getQuantity());
		domain.setExpireDate(dto.getExpireDate());
		domain.setMinimumStock(dto.getMinimumStock());
		domain.setInventoryDate(dto.getInventoryDate());
		domain.setInventoryQuantity(dto.getInventoryQuantity());
		domain.setStockUpdateDate(dto.getStockUpdateDate());
		domain.setStockUpdateQuantity(dto.getStockUpdateQuantity());
		domain.setRentPrice(dto.getRentPrice());
		domain.setUnitPerM2(dto.getUnitPerM2());

		return this.toDomain(dto, domain);
	}
}
