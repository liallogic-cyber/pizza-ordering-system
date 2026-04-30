public class FormatUtils {
    public static String formatCurrency(double amount) {
        long cents = Math.round(amount * 100);
        long dollars = cents / 100;
        cents = Math.abs(cents % 100);
        return "$" + dollars + "." + (cents < 10 ? "0" : "") + cents; // add leading zero
    }
}
