package com.app.apnabazar.controller;

import com.app.apnabazar.model.Product;
import com.app.apnabazar.service.ProductService;
import com.app.apnabazar.dto.ProductDto;
import com.app.apnabazar.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseMessage createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return new ResponseMessage("Product created successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseMessage updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProductDto){
        productService.updateProduct(id, updatedProductDto);
        return new ResponseMessage("Product updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseMessage deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseMessage("Product deleted Successfully.");
    }

}
