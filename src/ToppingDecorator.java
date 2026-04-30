// Base Decorator
public abstract class ToppingDecorator implements Pizza {
    protected Pizza decoratedPizza;
    protected String toppingName;
    protected double toppingCost;

    public ToppingDecorator(Pizza pizza, String name, double cost) {
        this.decoratedPizza = pizza;
        this.toppingName = name;
        this.toppingCost = cost;
    }

    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", " + toppingName;
    }

    @Override
    public double getCost() {
        return decoratedPizza.getCost() + toppingCost;
    }
}
