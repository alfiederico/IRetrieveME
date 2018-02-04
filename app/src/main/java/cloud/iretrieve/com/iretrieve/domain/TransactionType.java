package cloud.iretrieve.com.iretrieve.domain;

/**
 * Created by User on 28/12/2015.
 */
public class TransactionType {

    private String name;
    private int totalOrders;
    private double totalSales;

    /**
     * @return the name
     */

    public TransactionType() {
        super();
    }
    public TransactionType(String name, double totalSales, int totalOrders) {
        this.name = name;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the totalOrders
     */
    public int getTotalOrders() {
        return totalOrders;
    }

    /**
     * @param totalOrders the totalOrders to set
     */
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    /**
     * @return the totalSales
     */
    public double getTotalSales() {
        return totalSales;
    }

    /**
     * @param totalSales the totalSales to set
     */
    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}

