package com.jw.controller;

import com.jw.dto.ProductRequest;
import com.jw.dto.ProductResponse;
import com.jw.dto.ProductsResponse;
import com.jw.entity.Product;
import com.jw.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseBody
    public Product saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @GetMapping
    @ResponseBody
    public ProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductResponse getProductsById(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    @PutMapping
    @ResponseBody
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
