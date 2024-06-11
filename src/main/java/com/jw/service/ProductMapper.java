package com.jw.service;

import com.jw.dto.ProductRequest;
import com.jw.dto.ProductResponse;
import com.jw.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest ProductRequest);

    ProductResponse toResponse(Product product);
}
