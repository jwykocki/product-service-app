package com.jw.controller;


import com.jw.entity.Product;
import com.jw.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseBody
    public Product saveProduct(@RequestBody  Product product) {
        return productService.saveProduct(product);
    }


}
