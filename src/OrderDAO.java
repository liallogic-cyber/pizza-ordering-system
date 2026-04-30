import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void saveOrder(Order order) {
        String insertOrderSQL = "INSERT INTO orders (customer_name, total_amount, payment_method) VALUES (?, ?, ?)";
        String insertItemSQL = "INSERT INTO order_items (order_id, description, cost) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // 1. Insert Order
                long orderId;
                try (PreparedStatement statement = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, order.getCustomerName());
                    statement.setDouble(2, order.calculateTotal());
                    statement.setString(3, order.getPaymentMethod().getPaymentMethodName());
                    statement.executeUpdate();

                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            orderId = generatedKeys.getLong(1);
                        } else {
                            throw new SQLException("No ID obtained.");
                        }
                    }
                }

                // 2. Insert Order Items (Pizzas)
                try (PreparedStatement statement = conn.prepareStatement(insertItemSQL)) {
                    for (Pizza pizza : order.getPizzas()) {
                        statement.setLong(1, orderId);
                        statement.setString(2, pizza.getDescription());
                        statement.setDouble(3, pizza.getCost());
                        statement.addBatch(); // Group inserts
                    }
                    statement.executeBatch();
                }
                conn.commit(); // Save everything

            } catch (SQLException e) {
                conn.rollback(); // Undo if something fails
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String selectOrdersSQL = "SELECT * FROM orders";
        String selectItemsSQL = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectOrdersStatement = conn.prepareStatement(selectOrdersSQL);
             PreparedStatement selectItemsStatement = conn.prepareStatement(selectItemsSQL)) {

            ResultSet rsOrders = selectOrdersStatement.executeQuery();

            while (rsOrders.next()) {
                Order order = new Order();
                order.setCustomerName(rsOrders.getString("customer_name"));
                String method = rsOrders.getString("payment_method");
                order.setPaymentMethod(new PaymentStrategy() {
                    @Override
                    public boolean pay(double amount) {
                        return false;
                    }

                    @Override
                    public String getPaymentMethodName() {
                        return method;
                    }

                    @Override
                    public boolean validatePayment() {
                        return false;
                    }
                });

                // Retrieve items for this order
                int orderId = rsOrders.getInt("id");
                selectItemsStatement.setInt(1, orderId);
                ResultSet rsItems = selectItemsStatement.executeQuery();

                while(rsItems.next()) {
                    String desc = rsItems.getString("description");
                    double cost = rsItems.getDouble("cost");
                    // Use the helper class to add it back to the order
                    order.addPizza(new HistoricalPizza(desc, cost));
                }

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}