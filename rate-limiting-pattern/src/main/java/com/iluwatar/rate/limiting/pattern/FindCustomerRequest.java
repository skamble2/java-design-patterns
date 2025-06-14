package com.iluwatar.rate.limiting.pattern;

/**
 * A rate-limited customer lookup operation. This class wraps the rate limiting logic and represents
 * an executable business request.
 */
public class FindCustomerRequest implements RateLimitOperation<String> {
  private final String customerId;
  private final RateLimiter rateLimiter;

  public FindCustomerRequest(String customerId, RateLimiter rateLimiter) {
    this.customerId = customerId;
    this.rateLimiter = rateLimiter;
  }

  @Override
  public String getServiceName() {
    return "CustomerService";
  }

  @Override
  public String getOperationName() {
    return "FindCustomer";
  }

  @Override
  public String execute() throws RateLimitException {
    // Ensure the operation respects the assigned rate limiter
    rateLimiter.check(getServiceName(), getOperationName());

    // Simulate actual operation
    try {
      Thread.sleep(50); // Simulate processing time
      return "Customer-" + customerId;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ServiceUnavailableException(getServiceName(), 1000);
    }
  }
}
