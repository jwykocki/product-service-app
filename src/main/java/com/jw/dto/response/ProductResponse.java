package com.jw.dto.response;

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
