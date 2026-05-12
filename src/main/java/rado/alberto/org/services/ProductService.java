package rado.alberto.org.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.ProductCreateDto;
import rado.alberto.org.dto.ProductDto;
import rado.alberto.org.entities.Product;
import rado.alberto.org.mapper.ProductMapper;
import rado.alberto.org.repositories.ProductRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if(result.isPresent() &&  result.get().getId() != null) {
            return Optional.of(productMapper.toDto(result.get()));
        }
        return Optional.empty();
    }

    public Optional<ProductDto> getProductByName(String name) {
        Optional<Product> result = productRepository.findByName(name);
        if(result.isPresent() &&  result.get().getId() != null) {
            return Optional.of(productMapper.toDto(result.get()));
        }
        return Optional.empty();
    }

    public Optional<ProductDto> getProductBySku(String sku) {
        Optional<Product> result = productRepository.findBySku(sku);
        if(result.isPresent() &&  result.get().getId() != null) {
            return Optional.of(productMapper.toDto(result.get()));
        }
        return Optional.empty();
    }

    public ProductDto createProduct(@Valid ProductCreateDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCategory(dto.category());
        product.setImage(dto.image());
        product.setSku(dto.sku());
        product.setStock(dto.stock());
        product.setDiscount(dto.discount());
        productRepository.save(product);
        return productMapper.toDto(product);
    }
}
