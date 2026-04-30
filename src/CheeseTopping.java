// Concrete Decorator
public class CheeseTopping extends ToppingDecorator {
    public CheeseTopping(Pizza pizza) {
        super(pizza, "Extra Cheese", 1.50);
    }
}