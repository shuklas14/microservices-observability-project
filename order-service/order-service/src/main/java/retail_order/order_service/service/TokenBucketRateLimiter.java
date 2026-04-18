package retail_order.order_service.service;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class TokenBucketRateLimiter {
    public int MAX_TOKENS = 5;
    public final Semaphore semaphore = new Semaphore(MAX_TOKENS);

    public TokenBucketRateLimiter() {
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(() -> {

                    int missingTokens =
                            MAX_TOKENS - semaphore.availablePermits();

                    if (missingTokens > 0) {
                        semaphore.release(missingTokens);
                    }

                }, 1, 1, TimeUnit.SECONDS);
    }

     public boolean tryConsume() {
        return semaphore.tryAcquire();
    }
    
    }
