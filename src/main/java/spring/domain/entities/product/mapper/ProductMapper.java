package spring.domain.entities.product.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import spring.domain.entities.product.dto.CreateProductDTO;
import spring.domain.entities.product.dto.ResponseProductDTO;
import spring.domain.entities.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    CreateProductDTO toCreateProduct(Product product);

    @InheritInverseConfiguration
    Product toProduct(CreateProductDTO createProductDTO);


    ResponseProductDTO toResponseProductDTO(Product product);

}
