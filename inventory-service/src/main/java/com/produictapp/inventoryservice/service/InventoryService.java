package com.produictapp.inventoryservice.service;

import com.produictapp.inventoryservice.dto.InventoryResponse;
import com.produictapp.inventoryservice.model.Inventory;
import com.produictapp.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
@Transactional
        public List<InventoryResponse> isInStock(List<String> skuCode)
{
    return inventoryRepository.findBySkuCodeIn(skuCode).stream()

            .map(inventory ->
                InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isAvailable(inventory.getQuantity() > 0)
                        .build()
            ).toList();
        }

        }


