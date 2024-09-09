package spring.domain.entities.product.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.ResponseProductDTO;
import spring.domain.entities.product.dto.UpdateProductDTO;
import spring.domain.entities.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    CreateProductDTO toCreateProduct(Product product);

    @InheritInverseConfiguration
    Product toProduct(CreateProductDTO createProductDTO);

    ResponseProductDTO toResponseProductDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(UpdateProductDTO patchProductDTO, @MappingTarget Product product);

}
