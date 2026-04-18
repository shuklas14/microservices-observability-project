package retail_order.order_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import retail_order.order_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
