package cloud.iretrieve.com.iretrieve.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28/12/2015.
 */
public class Transaction {
    private String date;
    private double totalSales;
    private int totalOrders;
    private int weekNumber;
    private int monthNumber;
    private List<Article> orderItems = new ArrayList<Article>();
    private List<TransactionType> transType = new ArrayList<TransactionType>();
    private List<PaymentType> paymentType = new ArrayList<PaymentType>();

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
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
     * @return the orderItems
     */
    public List<Article> getOrderItems() {
        return orderItems;
    }

    /**
     * @param orderItems the orderItems to set
     */
    public void setOrderItems(List<Article> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * @return the transTypes
     */
    public List<TransactionType> getTransType() {
        return transType;
    }

    /**
     * @param transType the transTypes to set
     */
    public void setTransType(List<TransactionType> transType) {
        this.transType = transType;
    }

    /**
     * @return the paymenyType
     */
    public List<PaymentType> getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymenyType the paymenyTypes to set
     */
    public void setPaymentType(List<PaymentType> paymenyType) {
        this.paymentType = paymenyType;
    }


    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }
}