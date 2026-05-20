package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.ProductCreateDto;
import rado.alberto.org.dto.ProductDto;
import rado.alberto.org.entities.Product;
import rado.alberto.org.exceptions.InvalidProductException;
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

    public ProductDto getProductById(Long id) {
        Optional<Product> result = productRepository.findById(id);
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return productMapper.toDto(result.get());
    }

    public ProductDto getProductByName(String name) {
        Optional<Product> result = productRepository.findByName(name);
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return productMapper.toDto(result.get());
    }

    public ProductDto getProductBySku(String sku) {
        Optional<Product> result = productRepository.findBySku(sku);
        if(result.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return productMapper.toDto(result.get());
    }

    @Transactional
    public ProductDto createProduct(@Valid ProductCreateDto dto) {
        Optional<Product> search = productRepository.findBySku(dto.sku());
        if(search.isPresent()) {
            throw new ProductAlreadyExistException();
        }
        Product product = new Product();
        return productMapper.toDto(productRepository.save(verifyProduct(dto, product)));
    }

    @Transactional
    public ProductDto updateProduct(@Valid ProductDto dto) {
        Optional<Product> searchResult = productRepository.findById(dto.id());
        if(searchResult.isEmpty()) {
            throw new ProductNotFoundException();
        }
        Product product = searchResult.get();
        return productMapper.toDto(productRepository.save(verifyProduct(dto, product)));
    }

    @Transactional
    public void deleteProductById(Long id) {
        Optional<Product> searchResult = productRepository.findById(id);
        if(searchResult.isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    private Product verifyProduct(Object o, Product product){
        Product productDto;
        if(o instanceof ProductDto) {
            productDto = productMapper.toEntity((ProductDto) o);
            product.setId(productDto.getId());
        }else if(o instanceof ProductCreateDto) {
            productDto = productMapper.toEntity((ProductCreateDto) o);
        }else{
            throw new InvalidProductException();
        }
        if(productDto.getName() != null && !productDto.getName().isEmpty()) {
            product.setName(productDto.getName());
        }
        if(productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            product.setDescription(productDto.getDescription());
        }
        if(productDto.getCategory() != null) {
            product.setCategory(productDto.getCategory());
        }
        if(productDto.getImage() != null && !productDto.getImage().isEmpty()) {
            product.setImage(productDto.getImage());
        }
        if(productDto.getSku() != null && !productDto.getSku().isEmpty()) {
            product.setSku(productDto.getSku());
        }
        product.setStock(productDto.getStock());
        product.setDiscount(productDto.getDiscount());
        product.setPrice(productDto.getPrice());
        return product;
    }

}
