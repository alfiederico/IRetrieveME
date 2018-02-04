package cloud.iretrieve.com.iretrieve.domain;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by User on 28/12/2015.
 */
public class PaymentType {

    private String name;
    private int totalOrders;
    private double totalSales;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public PaymentType() {
        super();
    }

    public PaymentType(String name, double totalSales) {
        this.name = name;
        this.totalSales = Double.parseDouble(formatter.format(totalSales));
    }

    /**
     * @return the name
     */
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