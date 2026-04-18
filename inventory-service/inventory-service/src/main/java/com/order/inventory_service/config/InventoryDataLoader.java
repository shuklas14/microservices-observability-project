package com.order.inventory_service.config;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.inventory_service.domain.Inventory;
import com.order.inventory_service.repository.InventoryRepository;

@Configuration
public class InventoryDataLoader {
    @Bean
    CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            repository.save(new Inventory(UUID.fromString("11111111-1111-1111-1111-111111111111"), 100));
            repository.save(new Inventory(UUID.fromString("22222222-2222-2222-2222-222222222222"), 150));
            repository.save(new Inventory(UUID.fromString("33333333-3333-3333-3333-333333333333"), 200));
        };
    }
}