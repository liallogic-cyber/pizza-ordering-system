// Concrete Component
public abstract class BasePizza implements Pizza {
    private String type;
    private String size;
    private double cost;

    public BasePizza(String type, String size, double baseCost) {
        this.type = type;
        this.size = size;
        this.cost = baseCost;
        switch (size) {
            case Pizza.MEDIUM:
                cost *= 1.3;
                break;
            case Pizza.LARGE:
                cost *= 1.6;
                break;
        }
    }

    @Override
    public String getDescription() {
        return size + " " + type + " Pizza";
    }

    @Override
    public double getCost() {
        return cost;
    }
}
