package com.jw.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    List<ProductResponse> products;
}
