package com.jw.service;

import com.jw.dto.request.ProductRequest;
import com.jw.dto.response.ProductResponse;
import com.jw.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    Product toProduct(ProductResponse productResponse);
}
