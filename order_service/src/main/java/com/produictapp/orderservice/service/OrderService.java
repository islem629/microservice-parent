package com.produictapp.orderservice.service;

import com.produictapp.orderservice.dto.InventoryResponse;
import com.produictapp.orderservice.dto.OrderLineItemsDto;
import com.produictapp.orderservice.dto.OrderRequest;
import com.produictapp.orderservice.model.Order;
import com.produictapp.orderservice.model.OrderLineItems;
import com.produictapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClientException;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;
    private final WebClient webClient; // Inject WebClient builder

    public void placeOrder(OrderRequest orderRequest) {
        // Create order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        // Map order items
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String>skucodes= order.getOrderLineItemsList().stream()
                .map(orderLineItems1 ->orderLineItems1.getSkuCode())
                                .toList();
        //cal inventory Service and place order if prodiuct is in stock
        try {
            InventoryResponse[] inventoryResponse = webClient.get()
                    .uri("http://inventory-service/api/inventory", uriBuilder ->
                            uriBuilder.queryParam("skuCode",skucodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
            boolean allproductinstock= Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isAvailable);
            if(allproductinstock){
                orderRepository.save(order);
            }
            else {
                logger.error("Order could not be placed. Some products are out of stock.");
                throw new RestClientException("Order could not be placed");
            }
        } catch (WebClientResponseException e) {
            logger.error("Error fetching inventory: {}", e.getMessage());
        }




    }
    private OrderLineItems mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}