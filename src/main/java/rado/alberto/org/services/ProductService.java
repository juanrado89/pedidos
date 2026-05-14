package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.ProductCreateDto;
import rado.alberto.org.dto.ProductDto;
import rado.alberto.org.entities.Product;
import rado.alberto.org.exceptions.ProductAlreadyExistException;
import rado.alberto.org.exceptions.ProductNotFoundException;
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
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return Optional.of(productMapper.toDto(result.get()));
    }

    public Optional<ProductDto> getProductByName(String name) {
        Optional<Product> result = productRepository.findByName(name);
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return Optional.of(productMapper.toDto(result.get()));
    }

    public Optional<ProductDto> getProductBySku(String sku) {
        Optional<Product> result = productRepository.findBySku(sku);
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return Optional.of(productMapper.toDto(result.get()));
    }

    @Transactional
    public ProductDto createProduct(@Valid ProductCreateDto dto) {
        Optional<Product> search = productRepository.findBySku(dto.sku());
        if(search.isPresent()) {
            throw new ProductAlreadyExistException();
        }
        Product product = new Product();
        if(dto.name() != null && !dto.name().isEmpty()) {
            product.setName(dto.name());
        }
        if(dto.description() != null && !dto.description().isEmpty()) {
            product.setDescription(dto.description());
        }
        product.setPrice(dto.price());
        if(dto.category() != null) {
            product.setCategory(dto.category());
        }
        if(dto.image() != null && !dto.image().isEmpty()) {
            product.setImage(dto.image());
        }
        if(dto.sku() != null && !dto.sku().isEmpty()) {
            product.setSku(dto.sku());
        }
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setDiscount(dto.discount());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto updateProduct(@Valid ProductDto dto) {
        Optional<Product> searchResult = productRepository.findById(dto.id());
        if(searchResult.isEmpty()) {
            throw new ProductNotFoundException();
        }
        Product product = searchResult.get();
        if(dto.name() != null && !dto.name().isEmpty()) {
            product.setName(dto.name());
        }
        if(dto.description() != null && !dto.description().isEmpty()) {
            product.setDescription(dto.description());
        }
        if(dto.category() != null) {
            product.setCategory(dto.category());
        }
        if(dto.image() != null && !dto.image().isEmpty()) {
            product.setImage(dto.image());
        }
        if(dto.sku() != null && !dto.sku().isEmpty()) {
            product.setSku(dto.sku());
        }
        product.setStock(dto.stock());
        product.setDiscount(dto.discount());
        product.setPrice(dto.price());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public void deleteProductById(Long id) {
        Optional<Product> searchResult = productRepository.findById(id);
        if(searchResult.isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

}
