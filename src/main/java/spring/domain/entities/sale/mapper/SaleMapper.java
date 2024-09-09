package spring.domain.entities.sale.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.ResponseProductDTO;
import spring.domain.entities.product.dto.UpdateProductDTO;
import spring.domain.entities.sale.dto.CreateSaleDTO;
import spring.domain.entities.sale.model.Sale;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    spring.domain.entities.product.mapper.ProductMapper INSTANCE = Mappers.getMapper(spring.domain.entities.product.mapper.ProductMapper.class);
//    CreateSaleDTO toCreateProduct(Sale product);

//    @InheritInverseConfiguration
//    Sale toProduct(CreateSaleDTO createProductDTO);

//    ResponseProductDTO toResponseProductDTO(Sale product);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateProductFromDto(UpdateProductDTO patchProductDTO, @MappingTarget Sale product);

}
