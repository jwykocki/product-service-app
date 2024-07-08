package com.jw.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    Long productid;
    String name;
    int reserved;
    int available;
}
