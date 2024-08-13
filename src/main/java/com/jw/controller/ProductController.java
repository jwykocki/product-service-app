package com.jw.controller;

import com.jw.dto.ProductRequest;
import com.jw.dto.ProductResponse;
import com.jw.dto.ProductsResponse;
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
    public ProductResponse saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @GetMapping
    @ResponseBody
    public ProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public ProductResponse getProductsById(@PathVariable Long productId) {

        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    @ResponseBody
    public ProductResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long productId) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseBody
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
