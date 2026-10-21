package com.ash.creatorstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank(message= "Customer name is required")
    private String customerName;

    @NotBlank(message= "Customer email is required")
    @Email(message = "Enter a valid email")
    private String customerEmail;

    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    @NotBlank(message = "Customer address is required")
    private String customerAddress;

    @Valid
    @NotEmpty(message= "Order must contain at least one item")
    private List<OrderItemRequest> items;
}
