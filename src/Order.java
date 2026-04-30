import java.util.ArrayList;
import java.util.List;

// Strategy: Context
public class Order {
    private List<Pizza> pizzas = new ArrayList<>();
    private PaymentStrategy paymentMethod;
    private String customerName;

    public void setCustomerName(String name) { this.customerName = name; }
    public String getCustomerName() { return customerName; }

    public void setPaymentMethod(PaymentStrategy method) { this.paymentMethod = method; }
    public PaymentStrategy getPaymentMethod() { return paymentMethod; }

    public void addPizza(Pizza p) { pizzas.add(p); }
    public List<Pizza> getPizzas() { return new ArrayList<>(pizzas); }

    public double calculateTotal() {
        double total = 0;
        for (Pizza p : pizzas) {
            total += p.getCost();
        }
        return total;
    }

    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n=== Order Summary ===\n");
        summary.append("Customer: ").append(customerName).append("\nItems:\n");

        int i = 1;
        for (Pizza p : pizzas) {
            summary.append(i++).append(". ").append(p.getDescription())
                    .append(" - ").append(FormatUtils.formatCurrency(p.getCost())).append("\n");
        }
        summary.append("Total: ").append(FormatUtils.formatCurrency(calculateTotal())).append("\n");
        if (paymentMethod != null) {
            summary.append("Payment: ").append(paymentMethod.getPaymentMethodName()).append("\n");
        }
        return summary.toString();
    }

    public boolean processOrder() {
        if (paymentMethod == null) {
            System.out.println("Error: No payment method selected. Cannot process order.");
            return false;
        }
        System.out.println(getOrderSummary());
        if (paymentMethod.pay(calculateTotal())) {
            OrderHistory.saveOrder(this);
            return true;
        }
        return false;
    }
}
