// File: CashStrategy.java
public class CashStrategy implements PaymentStrategy {
    private double cashGiven;

    public CashStrategy(double cashGiven) {
        this.cashGiven = cashGiven;
    }

    @Override
    public boolean pay(double amount) {
        if (cashGiven >= amount) {
            double change = cashGiven - amount;
            System.out.println("Paid using cash: " + FormatUtils.formatCurrency(cashGiven) + ". Change: " + FormatUtils.formatCurrency(change));
            return true;
        } else {
            double needed = amount - cashGiven;
            System.out.println("Error: Insufficient cash. Need " + FormatUtils.formatCurrency(needed) + " more.");
            return false;
        }
    }

    @Override
    public String getPaymentMethodName() {
        return "Cash";
    }

    @Override
    public boolean validatePayment() {
        return cashGiven > 0;
    }
}