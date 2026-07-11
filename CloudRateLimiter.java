import java.util.concurrent.TimeUnit;

public class CloudRateLimiter {
    private final int maxCapacity;
    private int currentTokens;
    private long lastRefillTime;
    private final long refillIntervalNanos;

    public CloudRateLimiter(int maxCapacity, int tokensPerSecond) {
        this.maxCapacity = maxCapacity;
        this.currentTokens = maxCapacity;
        this.lastRefillTime = System.nanoTime();
        this.refillIntervalNanos = TimeUnit.SECONDS.toNanos(1) / tokensPerSecond;
    }

    // Core logic to check and consume tokens atomically
    public synchronized boolean allowRequest() {
        refillTokens();
        if (currentTokens > 0) {
            currentTokens--;
            return true; // Request Allowed
        }
        return false; // Request Throttled (429 Too Many Requests)
    }

    private void refillTokens() {
        long now = System.nanoTime();
        long elapsed = now - lastRefillTime;
        
        if (elapsed >= refillIntervalNanos) {
            int tokensToAdd = (int) (elapsed / refillIntervalNanos);
            if (tokensToAdd > 0) {
                currentTokens = Math.min(maxCapacity, currentTokens + tokensToAdd);
                lastRefillTime = now;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Cloud API Gateway Rate Limiter Active ---");
        CloudRateLimiter limiter = new CloudRateLimiter(3, 1); // Max 3 tokens, refills 1 per second

        // Simulate a burst spike of incoming client requests
        String[] incomingIPs = {"192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4", "192.168.1.5"};

        System.out.println("\n[SIMULATION] Processing rapid burst of 5 requests...");
        for (String ip : incomingIPs) {
            if (limiter.allowRequest()) {
                System.out.println("  -> [200 OK] Request processed for IP: " + ip);
            } else {
                System.out.println("  -> [429 TOO MANY REQUESTS] Dropped packet from IP: " + ip);
            }
        }

        // Wait 1.5 seconds to simulate an idle window for a bucket token refill
        System.out.println("\n[SIMULATION] Sleeping for 1.5 seconds to allow token regeneration...");
        Thread.sleep(1500);

        System.out.println("\n[SIMULATION] New request arrives after window cool-down:");
        if (limiter.allowRequest()) {
            System.out.println("  -> [200 OK] Request processed for IP: 192.168.1.6");
        } else {
            System.out.println("  -> [429] Throttled.");
        }
    }
}
