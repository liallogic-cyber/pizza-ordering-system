import java.util.List;

public class Main {
    private static final String[] PIZZA_TYPES = {"Margherita", "Pepperoni", "Veggie"};
    private static final String[] PIZZA_SIZES = {Pizza.SMALL, Pizza.MEDIUM, Pizza.LARGE};

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== CSS 217 Pizza Shop ===");
            System.out.println("1. New Order");
            System.out.println("2. Order History");
            System.out.println("3. Exit");

            int choice = InputUtils.getIntInput("Choose: ", 1, 3);
            switch (choice) {
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    viewOrderHistory();
                    break;
                case 3:
                    running = false;
                    break;
            }
        }
    }

    private static void createNewOrder() {
        String name = InputUtils.getValidStringInput("Customer Name: ");
        OrderBuilder builder = new OrderBuilder().setCustomer(name);

        boolean addingPizzas = true;
        while (addingPizzas) {
            System.out.println("\n--- Select Base Pizza ---");
            for (int i = 0; i < PIZZA_TYPES.length; i++) System.out.println((i+1) + ". " + PIZZA_TYPES[i]);
            int typeIdx = InputUtils.getIntInput("Type: ", 1, PIZZA_TYPES.length) - 1;

            for (int i = 0; i < PIZZA_SIZES.length; i++) System.out.println((i+1) + ". " + PIZZA_SIZES[i]);
            int sizeIdx = InputUtils.getIntInput("Size: ", 1, PIZZA_SIZES.length) - 1;
            String selectedSize = PIZZA_SIZES[sizeIdx];
            Pizza pizza = null;
            switch (typeIdx) {
                case 0:
                    pizza = new MargheritaPizza(selectedSize);
                    break;
                case 1:
                    pizza = new PepperoniPizza(selectedSize);
                    break;
                case 2:
                    pizza = new VeggiePizza(selectedSize);
                    break;
            }

            boolean addingToppings = true;
            while (addingToppings) {
                System.out.println("Current: " + pizza.getDescription() + " (" + FormatUtils.formatCurrency(pizza.getCost()) + ")");
                System.out.println("1. CheeseTopping (+$1.50) | 2. PepperoniTopping (+$2.00) | 3. Done");
                int top = InputUtils.getIntInput("Choice: ", 1, 3);
                if (top == 1) pizza = new CheeseTopping(pizza);
                else if (top == 2) pizza = new PepperoniTopping(pizza);
                else addingToppings = false;
            }
            builder.addProduct(pizza);

            if (InputUtils.getIntInput("Add another pizza? (1: Yes, 2: No): ", 1, 2) == 2) addingPizzas = false;
        }

        while (true) {
            System.out.println("\n--- Payment ---");
            System.out.println("1. Credit Card | 2. Cash | 3. Cancel");
            int pChoice = InputUtils.getIntInput("Method: ", 1, 3);

            if (pChoice == 3) return;

            if (pChoice == 1) {
                String num = InputUtils.getValidStringInput("Card Num: ");
                String exp = InputUtils.getValidStringInput("Expiry (MM/YY): ");
                String cvv = InputUtils.getValidStringInput("CVV: ");
                builder.setPaymentMethod(new CreditCardStrategy(num, exp, cvv));
            } else {
                double total = builder.build().calculateTotal(); // Temp build to show total
                System.out.println("Total: " + FormatUtils.formatCurrency(total));
                double cash = InputUtils.getDoubleInput("Pay using cash: $", total);
                builder.setPaymentMethod(new CashStrategy(cash));
            }

            Order order = builder.build();
            if (order.processOrder()) {
                return; // Success
            }

            System.out.println("Payment failed. Try again.");
        }
    }

    private static void viewOrderHistory() {
        List<Order> orders = OrderHistory.getOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("No history.");
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i+1) + ". " + orders.get(i).getCustomerName() + " - " + FormatUtils.formatCurrency(orders.get(i).calculateTotal()));
        }
        int v = InputUtils.getIntInput("View detail (0 back): ", 0, orders.size());
        if (v > 0) System.out.println(orders.get(v-1).getOrderSummary());
    }
}
