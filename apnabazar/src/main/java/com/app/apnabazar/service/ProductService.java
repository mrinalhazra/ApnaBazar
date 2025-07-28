package com.app.apnabazar.service;

import com.app.apnabazar.exception.NotFoundException;
import com.app.apnabazar.model.Product;
import com.app.apnabazar.repository.ProductRepository;
import com.app.apnabazar.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public void createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDto updatedProductDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setCategory(updatedProductDto.getCategory());
            product.setName(updatedProductDto.getName());
            product.setDescription(updatedProductDto.getDescription());
            product.setPrice(updatedProductDto.getPrice());

            productRepository.save(product);
        }
        else throw new NotFoundException("Product with id "+id+ " does not exists.");
    }

    public void deleteProduct(Long id) {
        if(productRepository.findById(id).isPresent() ){
            productRepository.deleteById(id);
        }else throw new NotFoundException("product not found");
    }
}
