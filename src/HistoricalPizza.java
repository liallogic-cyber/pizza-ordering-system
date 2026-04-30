// Used to reconstruct pizzas from the database for history viewing
public class HistoricalPizza implements Pizza {
    private String description;
    private double cost;

    public HistoricalPizza(String description, double cost) {
        this.description = description;
        this.cost = cost;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getCost() {
        return cost;
    }
}