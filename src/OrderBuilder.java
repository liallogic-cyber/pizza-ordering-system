// Builder: The Builder
public class OrderBuilder {
    private Order order = new Order();

    public OrderBuilder setCustomer(String name) {
        order.setCustomerName(name);
        return this;
    }

    public OrderBuilder addProduct(Pizza pizza) {
        order.addPizza(pizza);
        return this;
    }

    public OrderBuilder setPaymentMethod(PaymentStrategy method) {
        order.setPaymentMethod(method);
        return this;
    }

    public Order build() {
        if (order.getCustomerName() == null) throw new IllegalStateException("Name required");
        if (order.getPizzas().isEmpty()) throw new IllegalStateException("Order empty");
        return order;
    }
}