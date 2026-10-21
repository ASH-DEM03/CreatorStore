package com.ash.creatorstore.Services;

import com.ash.creatorstore.dto.OrderItemRequest;
import com.ash.creatorstore.dto.OrderRequest;
import com.ash.creatorstore.entities.Order;
import com.ash.creatorstore.entities.OrderItems;
import com.ash.creatorstore.entities.Product;
import com.ash.creatorstore.repositories.OrderRepository;
import com.ash.creatorstore.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Transactional

    public Order createOrder(OrderRequest orderRequest) {
        List<OrderItems> orderItemsList = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setStatus("CONFİRMED");

        for (OrderItemRequest itemRequest: orderRequest.getItems()){

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(()-> new RuntimeException("Product not found with id "+ itemRequest
                            .getProductId()));
            // Check the product stock
            if (product.getStockQuantity() < itemRequest.getQuantity()){
                throw new RuntimeException("Product quantity less than stock quantity");
            }
            //Calculate the total price
            BigDecimal priceOfItem = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice  = totalPrice.add(product.getPrice());

            //Update the product table table with latest stock quantity
            product.setStockQuantity(
                    product.getStockQuantity() - itemRequest.getQuantity()
            );
            productRepository.save(product);
            //Builder pattern to make object
            OrderItems orderItem = OrderItems.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItemsList.add(orderItem);
        }
        order.setOrderItems(orderItemsList);
        order.setTotalPrice(totalPrice);
        order.setCustomerPhone(orderRequest.getCustomerPhone());
        order.setCustomerAddress(orderRequest.getCustomerAddress());
        return orderRepository.save(order);
    }
}
