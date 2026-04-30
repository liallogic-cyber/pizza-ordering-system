// Concrete Decorator
public class PepperoniTopping extends ToppingDecorator {
    public PepperoniTopping(Pizza pizza) {
        super(pizza, "Extra Pepperoni", 2.00);
    }
}