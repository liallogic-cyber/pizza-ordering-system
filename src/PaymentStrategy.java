// Strategy Interface
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethodName();
    boolean validatePayment();
}