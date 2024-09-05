package spring.domain.product.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import spring.domain.product.dto.CreateProductDTO;
import spring.domain.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "name", target = "productName")
    CreateProductDTO toCreateProduct(Product product);

    @InheritInverseConfiguration
    @Mapping(source = "productName", target = "name")
    Product toProduct(CreateProductDTO createProductDTO);

}
