package com.jw.controller;

import com.jw.dto.ProductRequest;
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

    @GetMapping
    @ResponseBody
    public ProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseBody
    public Product saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }
}
