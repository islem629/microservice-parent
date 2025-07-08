package com.produictapp.inventoryservice.controller;

import com.produictapp.inventoryservice.dto.InventoryResponse;
import com.produictapp.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam(value = "skuCode", required = false) String[] skuCode) {
        log.info("Received inventory check request for skuCode: {}", (Object) skuCode);
        if (skuCode == null) {
            // handle the case when skuCode is not provided (empty)
            return inventoryService.isInStock(List.of());
        }
        return inventoryService.isInStock(List.of(skuCode));
    }





}