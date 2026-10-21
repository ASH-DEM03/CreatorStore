package com.ash.creatorstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull(message ="Product id is required")
    private Long productId;

    @NotNull(message ="Quantity is required")
    @Min(value =1, message=" Qantity must be at least 1")
    private int quantity;
}
