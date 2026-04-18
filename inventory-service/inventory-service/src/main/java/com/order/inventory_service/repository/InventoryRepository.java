package com.order.inventory_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.inventory_service.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
}