import java.time.LocalDate;

// Concrete Strategy
public class CreditCardStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String expiryDate;
    private final String cvv;

    public CreditCardStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        if (!validatePayment()) {
            return false;
        }

        System.out.print("Processing payment of " + FormatUtils.formatCurrency(amount) + " with credit card...");
        System.out.println(" Approved!");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    private boolean validateExpiryDate() {
        try {
            // Check format MM/YY
            if (expiryDate.length() != 5 || expiryDate.charAt(2) != '/') {
                System.out.println("Error: Invalid date format. Use MM/YY format (e.g., 12/25)");
                return false;
            }

            String[] parts = expiryDate.split("/");
            int inputMonth = Integer.parseInt(parts[0]);
            int inputYear = Integer.parseInt(parts[1]);

            // Month is in the interval 1-12
            if (inputMonth < 1 || inputMonth > 12) {
                System.out.println("Error: Month must be between 01 and 12");
                return false;
            }

            // Get Current Date
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear() % 100; // Convert 2025 to 25
            int currentMonth = today.getMonthValue();

            // Check Expiry
            if (inputYear < currentYear || (inputYear == currentYear && inputMonth < currentMonth)) {
                System.out.println("Error: Card has expired");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error: Invalid date format. Use MM/YY format (e.g., 12/25)");
            return false;
        }
    }

    @Override
    public boolean validatePayment() {
        if (cardNumber.length() != 16) {
            System.out.println("Error: Card number must be 16 digits");
            return false;
        }
        if (cvv.length() != 3) {
            System.out.println("Error: CVV must be 3 digits");
            return false;
        }
        return validateExpiryDate();
    }
}