package rado.alberto.org.services;

import org.springframework.stereotype.Service;
import rado.alberto.org.dto.ProductDto;
import rado.alberto.org.entities.Product;
import rado.alberto.org.mapper.ProductMapper;
import rado.alberto.org.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> result = productRepository.findAll();
        return productMapper.toDtos(result);
    }

    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if(result.isPresent() &&  result.get().getId() != null) {
            return Optional.of(productMapper.toDto(result.get()));
        }
        return Optional.empty();
    }


}
