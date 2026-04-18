package retail_order.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import retail_order.order_service.api.CreateOrderRequest;
import retail_order.order_service.service.OrderService;
import retail_order.order_service.service.TokenBucketRateLimiter;

@RestController
@RequestMapping("/orders")
public class OrderController {
public final OrderService orderService;
public final TokenBucketRateLimiter rateLimiter;
    public OrderController(OrderService orderService, TokenBucketRateLimiter rateLimiter) {
        this.orderService = orderService;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        if(!rateLimiter.tryConsume()) {
            return ResponseEntity.status(429).body("Too many requests. Please try again later."); // Too Many Requests
        }
        return ResponseEntity.ok(orderService.create(request).toString());
    }
}
