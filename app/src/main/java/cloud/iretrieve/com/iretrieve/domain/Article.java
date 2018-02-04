package cloud.iretrieve.com.iretrieve.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 28/12/2015.
 */
public class Article implements Parcelable{

    private int zArticleID;
    private String zDescription;
    private String zPLU;
    private double zSellShop;
    private double zSellDelivery;
    private double zSellTable;
    private double zSellSpecial;
    private String zCategoryID;
    private String zSubCategoryID;
    private String zCategory;
    private String zSubCategory;
    private boolean zGSTExempt;
    private boolean zSpecial;
    private boolean zUsePercent;
    private String zArticleType;
    private boolean zExclude;
    private String zSubMenu;
    private boolean zLoyaltyItem;
    private boolean zPrintZeroAmt;
    private double zTotalSales;
    private int zQty;
    private double zUnitSell;



    public Article() {
        super();
    }

    public Article(String zPLU, String zDescription) {
        this.zPLU = zPLU;
        this.zDescription = zDescription;
    }

    /**
     * @return the zArticleID
     */
    public int getArticleID() {
        return zArticleID;
    }

    /**
     * @param zArticleID the zArticleID to set
     */
    public void setArticleID(int zArticleID) {
        this.zArticleID = zArticleID;
    }

    /**
     * @return the zQty
     */
    public int getQty() {
        return zQty;
    }

    /**
     * @param zQty the zArticleID to set
     */
    public void setQty(int zQty) {
        this.zQty = zQty;
    }

    /**
     * @return the zDescription
     */
    public String getDescription() {
        return zDescription;
    }

    /**
     * @param zDescription the zDescription to set
     */
    public void setDescription(String zDescription) {
        this.zDescription = zDescription;
    }

    /**
     * @return the zPLU
     */
    public String getPLU() {
        return zPLU;
    }

    /**
     * @param zPLU the zPLU to set
     */
    public void setPLU(String zPLU) {
        this.zPLU = zPLU;
    }

    /**
     * @return the zSellShop
     */
    public double getSellShop() {
        return zSellShop;
    }

    /**
     * @param zSellShop the zSellShop to set
     */
    public void setSellShop(double zSellShop) {
        this.zSellShop = zSellShop;
    }

    /**
     * @return the zSellDelivery
     */
    public double getSellDelivery() {
        return zSellDelivery;
    }

    /**
     * @param zSellDelivery the zSellDelivery to set
     */
    public void setSellDelivery(double zSellDelivery) {
        this.zSellDelivery = zSellDelivery;
    }

    /**
     * @return the zSellTable
     */
    public double getSellTable() {
        return zSellTable;
    }

    /**
     * @param zSellTable the zSellTable to set
     */
    public void setSellTable(double zSellTable) {
        this.zSellTable = zSellTable;
    }

    /**
     * @return the zSellSpecial
     */
    public double getSellSpecial() {
        return zSellSpecial;
    }

    /**
     * @param zSellSpecial the zSellSpecial to set
     */
    public void setSellSpecial(double zSellSpecial) {
        this.zSellSpecial = zSellSpecial;
    }

    /**
     * @return the zCategoryID
     */
    public String getCategoryID() {
        return zCategoryID;
    }

    /**
     * @param zCategoryID the zCategoryID to set
     */
    public void setCategoryID(String zCategoryID) {
        this.zCategoryID = zCategoryID;
    }

    /**
     * @return the zSubCategoryID
     */
    public String getSubCategoryID() {
        return zSubCategoryID;
    }

    /**
     * @param zSubCategoryID the zSubCategoryID to set
     */
    public void setSubCategoryID(String zSubCategoryID) {
        this.zSubCategoryID = zSubCategoryID;
    }

    public String getCategory() {
        return zCategory;
    }

    /**
     * @param zCategory the zCategoryID to set
     */
    public void setCategory(String zCategory) {
        this.zCategory = zCategory;
    }

    /**
     * @return the zSubCategoryID
     */
    public String getSubCategory() {
        return zSubCategory;
    }

    /**
     * @param zSubCategory the zSubCategoryID to set
     */
    public void setSubCategory(String zSubCategory) {
        this.zSubCategory = zSubCategory;
    }

    /**
     * @return the zGSTExempt
     */
    public boolean isGSTExempt() {
        return zGSTExempt;
    }

    /**
     * @param zGSTExempt the zGSTExempt to set
     */
    public void setGSTExempt(boolean zGSTExempt) {
        this.zGSTExempt = zGSTExempt;
    }

    /**
     * @return the zSpecial
     */
    public boolean isSpecial() {
        return zSpecial;
    }

    /**
     * @param zSpecial the zSpecial to set
     */
    public void setSpecial(boolean zSpecial) {
        this.zSpecial = zSpecial;
    }

    /**
     * @return the zUsePercent
     */
    public boolean isUsePercent() {
        return zUsePercent;
    }

    /**
     * @param zUsePercent the zUsePercent to set
     */
    public void setUsePercent(boolean zUsePercent) {
        this.zUsePercent = zUsePercent;
    }

    /**
     * @return the zArticleType
     */
    public String getArticleType() {
        return zArticleType;
    }

    /**
     * @param zArticleType the zArticleType to set
     */
    public void setArticleType(String zArticleType) {
        this.zArticleType = zArticleType;
    }

    /**
     * @return the zExclude
     */
    public boolean isExclude() {
        return zExclude;
    }

    /**
     * @param zExclude the zExclude to set
     */
    public void setExclude(boolean zExclude) {
        this.zExclude = zExclude;
    }

    /**
     * @return the zSubMenu
     */
    public String getSubMenu() {
        return zSubMenu;
    }

    /**
     * @param zSubMenu the zSubMenu to set
     */
    public void setSubMenu(String zSubMenu) {
        this.zSubMenu = zSubMenu;
    }

    /**
     * @return the zLoyaltyItem
     */
    public boolean isLoyaltyItem() {
        return zLoyaltyItem;
    }

    /**
     * @param zLoyaltyItem the zLoyaltyItem to set
     */
    public void setLoyaltyItem(boolean zLoyaltyItem) {
        this.zLoyaltyItem = zLoyaltyItem;
    }

    /**
     * @return the zPrintZeroAmt
     */
    public boolean isPrintZeroAmt() {
        return zPrintZeroAmt;
    }

    /**
     * @param zPrintZeroAmt the zPrintZeroAmt to set
     */
    public void setPrintZeroAmt(boolean zPrintZeroAmt) {
        this.zPrintZeroAmt = zPrintZeroAmt;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Article other = (Article) obj;
        if (zPLU == null) {
            if (other.getArticleID() != 0) {
                return false;
            }
        } else if (!zPLU.equals(other.getPLU())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((zPLU == null) ? 0 : zPLU.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Product [PLU=" + zPLU + ", name=" + zDescription
                + "]";
    }

    /**
     * @return the zUnitSell
     */
    public double getUnitSell() {
        return zUnitSell;
    }

    /**
     * @param zUnitSell the zUnitSell to set
     */
    public void setUnitSell(double zUnitSell) {
        this.zUnitSell = zUnitSell;
    }

    public double getTotalSales() {
        return zTotalSales;
    }

    public void setTotalSales(double zTotalSales) {
        this.zTotalSales = zTotalSales;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zCategory);
        dest.writeString(zSubCategory);
        dest.writeString(zPLU);
        dest.writeString(zDescription);
        dest.writeInt(zQty);
        dest.writeDouble(zTotalSales);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {

        public Article createFromParcel(Parcel source) {

            Article mArt = new Article();

            mArt.setCategory(source.readString());
            mArt.setSubCategory(source.readString());
            mArt.setPLU(source.readString());
            mArt.setDescription(source.readString());
            mArt.setQty(source.readInt());
            mArt.setTotalSales(source.readDouble());

            return mArt;

        }

        public Article[] newArray(int size) {

            return new Article[size];

        }

    };
}
