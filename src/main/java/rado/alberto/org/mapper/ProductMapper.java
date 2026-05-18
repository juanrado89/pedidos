package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.ProductCreateDto;
import rado.alberto.org.dto.ProductDto;
import rado.alberto.org.entities.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);

    List<ProductDto> toDtos(List<Product> products);
    List<Product> toEntities(List<ProductDto> productDtos);

    Product toEntity(ProductCreateDto o);
}
