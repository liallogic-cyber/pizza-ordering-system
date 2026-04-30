import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    private static OrderDAO orderDAO = new OrderDAO();

    public static void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    public static List<Order> getOrderHistory() {
        return orderDAO.getAllOrders();
    }
}