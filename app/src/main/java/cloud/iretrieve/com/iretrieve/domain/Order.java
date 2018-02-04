package cloud.iretrieve.com.iretrieve.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 4/01/2016.
 */
public class Order  implements Parcelable {
    private String mOrderID;
    private String mOrderDate;
    private String mTakenBy;
    private String mDeliveredBy;
    private String mLocationID;
    private String mTenderAmt;
    private String mChangeAmt;
    private String mTenderTypeid;
    private String mTransactionStatus;
    private String mPickupTime;
    private int mCustomerID;
    private String mCustomerPhone;
    private String mCustomerName;



    private String mAptNumber;
    private String mFloor;
    private String mBldgName;

    private String mStreetNumber;
    private String mStreetName;
    private String mStreetComment;
    private String mSuburb;
    private String mMap;
    private String mMapRef;
    private String mDeliveryZone;
    private String mComments;
    private String mAddressComments;
    private int mTransactionType;
    private boolean mReprint;
    private boolean mPaid;
    private String mInTime;
    private String mOutTime;
    private String mNotes;
    private String mNewOrderID;
    private String mOldOrderID;
    private String mExcludedAmt;
    private String mSessionID;
    private String mTableNo;
    private String mTableBill;
    private String mCashier;
    private String mGuests;
    private int mLoyaltyRedeemed;
    private int mLoyaltyAccrued;
    private String mStatusComments;
    private String mComputerName;
    private String mTableDesc;
    private boolean mOnlineOrder;
    private String mOnlineRecID;
    private String mAmountPaid;
    private String mCommentsNoPrint;
    private String mCrossStreet;
    private String mWebAmountPaid;
    private boolean mIsAsap;
    private String mCCTransactionNumber;
    private String mPaidComputer;
    private String mPaidDate;
    private String mMakeDoneTime;
    private String mOnlinePaymentMethod;
    private boolean bReprintSession;
    private String mSessionName;
    private int mAccountNumber;
    private String mTransactionID;
    private String mTenderedValue;
    private boolean bMustBePaidFull;
    private int mPrinterNumber;
    private String mPrinterID;
    private boolean bSorted;
    /**
     * @return the mOrderID
     */
    public String getOrderID() {
        return mOrderID;
    }

    /**
     * @param mOrderID the mOrderID to set
     */
    public void setOrderID(String mOrderID) {
        this.mOrderID = mOrderID;
    }

    /**
     * @return the mOrderDate
     */
    public String getOrderDate() {
        return mOrderDate;
    }

    /**
     * @param mOrderDate the mOrderDate to set
     */
    public void setOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }

    /**
     * @return the mTakenBy
     */
    public String getTakenBy() {
        return mTakenBy;
    }

    /**
     * @param mTakenBy the mTakenBy to set
     */
    public void setTakenBy(String mTakenBy) {
        this.mTakenBy = mTakenBy;
    }

    /**
     * @return the mDeliveredBy
     */
    public String getDeliveredBy() {
        return mDeliveredBy;
    }

    /**
     * @param mDeliveredBy the mDeliveredBy to set
     */
    public void setDeliveredBy(String mDeliveredBy) {
        this.mDeliveredBy = mDeliveredBy;
    }

    /**
     * @return the mLocationID
     */
    public String getLocationID() {
        return mLocationID;
    }

    /**
     * @param mLocationID the mLocationID to set
     */
    public void setLocationID(String mLocationID) {
        this.mLocationID = mLocationID;
    }

    /**
     * @return the mTenderAmt
     */
    public String getTenderAmt() {
        return mTenderAmt;
    }

    /**
     * @param mTenderAmt the mTenderAmt to set
     */
    public void setTenderAmt(String mTenderAmt) {
        this.mTenderAmt = mTenderAmt;
    }

    /**
     * @return the mChangeAmt
     */
    public String getChangeAmt() {
        return mChangeAmt;
    }

    /**
     * @param mChangeAmt the mChangeAmt to set
     */
    public void setChangeAmt(String mChangeAmt) {
        this.mChangeAmt = mChangeAmt;
    }

    /**
     * @return the mTenderTypeid
     */
    public String getTenderTypeid() {
        return mTenderTypeid;
    }

    /**
     * @param mTenderTypeid the mTenderTypeid to set
     */
    public void setTenderTypeid(String mTenderTypeid) {
        this.mTenderTypeid = mTenderTypeid;
    }

    /**
     * @return the mTransactionStatus
     */
    public String getTransactionStatus() {
        return mTransactionStatus;
    }

    /**
     * @param mTransactionStatus the mTransactionStatus to set
     */
    public void setTransactionStatus(String mTransactionStatus) {
        this.mTransactionStatus = mTransactionStatus;
    }

    /**
     * @return the mPickupTime
     */
    public String getPickupTime() {
        return mPickupTime;
    }

    /**
     * @param mPickupTime the mPickupTime to set
     */
    public void setPickupTime(String mPickupTime) {
        this.mPickupTime = mPickupTime;
    }

    /**
     * @return the mCustomerID
     */
    public float getCustomerID() {
        return mCustomerID;
    }

    /**
     * @param mCustomerID the mCustomerID to set
     */
    public void setCustomerID(int mCustomerID) {
        this.mCustomerID = mCustomerID;
    }

    /**
     * @return the mCustomerPhone
     */
    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    /**
     * @param mCustomerPhone the mCustomerPhone to set
     */
    public void setCustomerPhone(String mCustomerPhone) {
        this.mCustomerPhone = mCustomerPhone;
    }

    /**
     * @return the mCustomerName
     */
    public String getCustomerName() {
        return mCustomerName;
    }

    /**
     * @param mCustomerName the mCustomerName to set
     */
    public void setCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    /**
     * @return the mAptNumber
     */
    public String getAptNumber() {
        return mAptNumber;
    }

    /**
     * @param mAptNumber the mAptNumber to set
     */
    public void setAptNumber(String mAptNumber) {
        this.mAptNumber = mAptNumber;
    }

    /**
     * @return the mFloor
     */
    public String getFloor() {
        return mFloor;
    }

    /**
     * @param mFloor the mFloor to set
     */
    public void setFloor(String mFloor) {
        this.mFloor = mFloor;
    }

    /**
     * @return the mBldgName
     */
    public String getBldgName() {
        return mBldgName;
    }

    /**
     * @param mBldgName the mBldgName to set
     */
    public void setBldgName(String mBldgName) {
        this.mBldgName = mBldgName;
    }

    /**
     * @return the mStreetNumber
     */
    public String getStreetNumber() {
        return mStreetNumber;
    }

    /**
     * @param mStreetNumber the mStreetNumber to set
     */
    public void setStreetNumber(String mStreetNumber) {
        this.mStreetNumber = mStreetNumber;
    }

    /**
     * @return the mStreetName
     */
    public String getStreetName() {
        return mStreetName;
    }

    /**
     * @param mStreetName the mStreetName to set
     */
    public void setStreetName(String mStreetName) {
        this.mStreetName = mStreetName;
    }

    /**
     * @return the mStreetComment
     */
    public String getStreetComment() {
        return mStreetComment;
    }

    /**
     * @param mStreetComment the mStreetComment to set
     */
    public void setStreetComment(String mStreetComment) {
        this.mStreetComment = mStreetComment;
    }

    /**
     * @return the mSuburb
     */
    public String getSuburb() {
        return mSuburb;
    }

    /**
     * @param mSuburb the mSuburb to set
     */
    public void setSuburb(String mSuburb) {
        this.mSuburb = mSuburb;
    }

    /**
     * @return the mMap
     */
    public String getMap() {
        return mMap;
    }

    /**
     * @param mMap the mMap to set
     */
    public void setMap(String mMap) {
        this.mMap = mMap;
    }

    /**
     * @return the mMapRef
     */
    public String getMapRef() {
        return mMapRef;
    }

    /**
     * @param mMapRef the mMapRef to set
     */
    public void setMapRef(String mMapRef) {
        this.mMapRef = mMapRef;
    }

    /**
     * @return the mDeliveryZone
     */
    public String getDeliveryZone() {
        return mDeliveryZone;
    }

    /**
     * @param mDeliveryZone the mDeliveryZone to set
     */
    public void setDeliveryZone(String mDeliveryZone) {
        this.mDeliveryZone = mDeliveryZone;
    }

    /**
     * @return the mComments
     */
    public String getComments() {
        return mComments;
    }

    /**
     * @param mComments the mComments to set
     */
    public void setComments(String mComments) {
        this.mComments = mComments;
    }

    /**
     * @return the mAddressComments
     */
    public String getAddressComments() {
        return mAddressComments;
    }

    /**
     * @param mAddressComments the mAddressComments to set
     */
    public void setAddressComments(String mAddressComments) {
        this.mAddressComments = mAddressComments;
    }

    /**
     * @return the mTransactionType
     */
    public int getTransactionType() {
        return mTransactionType;
    }

    /**
     * @param mTransactionType the mTransactionType to set
     */
    public void setTransactionType(int mTransactionType) {
        this.mTransactionType = mTransactionType;
    }

    /**
     * @return the mReprint
     */
    public boolean ismReprint() {
        return mReprint;
    }

    /**
     * @param mReprint the mReprint to set
     */
    public void setReprint(boolean mReprint) {
        this.mReprint = mReprint;
    }

    /**
     * @return the mPaid
     */
    public boolean ismPaid() {
        return mPaid;
    }

    /**
     * @param mPaid the mPaid to set
     */
    public void setPaid(boolean mPaid) {
        this.mPaid = mPaid;
    }

    /**
     * @return the mInTime
     */
    public String getInTime() {
        return mInTime;
    }

    /**
     * @param mInTime the mInTime to set
     */
    public void setInTime(String mInTime) {
        this.mInTime = mInTime;
    }

    /**
     * @return the mOutTime
     */
    public String getOutTime() {
        return mOutTime;
    }

    /**
     * @param mOutTime the mOutTime to set
     */
    public void setOutTime(String mOutTime) {
        this.mOutTime = mOutTime;
    }

    /**
     * @return the mNotes
     */
    public String getNotes() {
        return mNotes;
    }

    /**
     * @param mNotes the mNotes to set
     */
    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    /**
     * @return the mNewOrderID
     */
    public String getNewOrderID() {
        return mNewOrderID;
    }

    /**
     * @param mNewOrderID the mNewOrderID to set
     */
    public void setNewOrderID(String mNewOrderID) {
        this.mNewOrderID = mNewOrderID;
    }

    /**
     * @return the mOldOrderID
     */
    public String getOldOrderID() {
        return mOldOrderID;
    }

    /**
     * @param mOldOrderID the mOldOrderID to set
     */
    public void setOldOrderID(String mOldOrderID) {
        this.mOldOrderID = mOldOrderID;
    }

    /**
     * @return the mExcludedAmt
     */
    public String getExcludedAmt() {
        return mExcludedAmt;
    }

    /**
     * @param mExcludedAmt the mExcludedAmt to set
     */
    public void setExcludedAmt(String mExcludedAmt) {
        this.mExcludedAmt = mExcludedAmt;
    }

    /**
     * @return the mSessionID
     */
    public String getSessionID() {
        return mSessionID;
    }

    /**
     * @param mSessionID the mSessionID to set
     */
    public void setSessionID(String mSessionID) {
        this.mSessionID = mSessionID;
    }

    /**
     * @return the mTableNo
     */
    public String getTableNo() {
        return mTableNo;
    }

    /**
     * @param mTableNo the mTableNo to set
     */
    public void setTableNo(String mTableNo) {
        this.mTableNo = mTableNo;
    }

    /**
     * @return the mTableBill
     */
    public String getTableBill() {
        return mTableBill;
    }

    /**
     * @param mTableBill the mTableBill to set
     */
    public void setTableBill(String mTableBill) {
        this.mTableBill = mTableBill;
    }

    /**
     * @return the mCashier
     */
    public String getCashier() {
        return mCashier;
    }

    /**
     * @param mCashier the mCashier to set
     */
    public void setCashier(String mCashier) {
        this.mCashier = mCashier;
    }

    /**
     * @return the mGuests
     */
    public String getGuests() {
        return mGuests;
    }

    /**
     * @param mGuests the mGuests to set
     */
    public void setGuests(String mGuests) {
        this.mGuests = mGuests;
    }

    /**
     * @return the mLoyaltyRedeemed
     */
    public int getLoyaltyRedeemed() {
        return mLoyaltyRedeemed;
    }

    /**
     * @param mLoyaltyRedeemed the mLoyaltyRedeemed to set
     */
    public void setLoyaltyRedeemed(int mLoyaltyRedeemed) {
        this.mLoyaltyRedeemed = mLoyaltyRedeemed;
    }

    /**
     * @return the mLoyaltyAccrued
     */
    public int getLoyaltyAccrued() {
        return mLoyaltyAccrued;
    }

    /**
     * @param mLoyaltyAccrued the mLoyaltyAccrued to set
     */
    public void setLoyaltyAccrued(int mLoyaltyAccrued) {
        this.mLoyaltyAccrued = mLoyaltyAccrued;
    }

    /**
     * @return the mStatusComments
     */
    public String getStatusComments() {
        return mStatusComments;
    }

    /**
     * @param mStatusComments the mStatusComments to set
     */
    public void setStatusComments(String mStatusComments) {
        this.mStatusComments = mStatusComments;
    }

    /**
     * @return the mComputerName
     */
    public String getComputerName() {
        return mComputerName;
    }

    /**
     * @param mComputerName the mComputerName to set
     */
    public void setComputerName(String mComputerName) {
        this.mComputerName = mComputerName;
    }

    /**
     * @return the mTableDesc
     */
    public String getTableDesc() {
        return mTableDesc;
    }

    /**
     * @param mTableDesc the mTableDesc to set
     */
    public void setTableDesc(String mTableDesc) {
        this.mTableDesc = mTableDesc;
    }

    /**
     * @return the mOnlineOrder
     */
    public boolean ismOnlineOrder() {
        return mOnlineOrder;
    }

    /**
     * @param mOnlineOrder the mOnlineOrder to set
     */
    public void setOnlineOrder(boolean mOnlineOrder) {
        this.mOnlineOrder = mOnlineOrder;
    }

    /**
     * @return the mOnlineRecID
     */
    public String getOnlineRecID() {
        return mOnlineRecID;
    }

    /**
     * @param mOnlineRecID the mOnlineRecID to set
     */
    public void setOnlineRecID(String mOnlineRecID) {
        this.mOnlineRecID = mOnlineRecID;
    }

    /**
     * @return the mAmountPaid
     */
    public String getAmountPaid() {
        return mAmountPaid;
    }

    /**
     * @param mAmountPaid the mAmountPaid to set
     */
    public void setAmountPaid(String mAmountPaid) {
        this.mAmountPaid = mAmountPaid;
    }

    /**
     * @return the mCommentsNoPrint
     */
    public String getCommentsNoPrint() {
        return mCommentsNoPrint;
    }

    /**
     * @param mCommentsNoPrint the mCommentsNoPrint to set
     */
    public void setCommentsNoPrint(String mCommentsNoPrint) {
        this.mCommentsNoPrint = mCommentsNoPrint;
    }

    /**
     * @return the mCrossStreet
     */
    public String getCrossStreet() {
        return mCrossStreet;
    }

    /**
     * @param mCrossStreet the mCrossStreet to set
     */
    public void setCrossStreet(String mCrossStreet) {
        this.mCrossStreet = mCrossStreet;
    }

    /**
     * @return the mWebAmountPaid
     */
    public String getWebAmountPaid() {
        return mWebAmountPaid;
    }

    /**
     * @param mWebAmountPaid the mWebAmountPaid to set
     */
    public void setWebAmountPaid(String mWebAmountPaid) {
        this.mWebAmountPaid = mWebAmountPaid;
    }

    /**
     * @return the mIsAsap
     */
    public boolean ismIsAsap() {
        return mIsAsap;
    }

    /**
     * @param mIsAsap the mIsAsap to set
     */
    public void setIsAsap(boolean mIsAsap) {
        this.mIsAsap = mIsAsap;
    }

    /**
     * @return the mCCTransactionNumber
     */
    public String getCCTransactionNumber() {
        return mCCTransactionNumber;
    }

    /**
     * @param mCCTransactionNumber the mCCTransactionNumber to set
     */
    public void setCCTransactionNumber(String mCCTransactionNumber) {
        this.mCCTransactionNumber = mCCTransactionNumber;
    }

    /**
     * @return the mPaidComputer
     */
    public String getPaidComputer() {
        return mPaidComputer;
    }

    /**
     * @param mPaidComputer the mPaidComputer to set
     */
    public void setPaidComputer(String mPaidComputer) {
        this.mPaidComputer = mPaidComputer;
    }

    /**
     * @return the mPaidDate
     */
    public String getPaidDate() {
        return mPaidDate;
    }

    /**
     * @param mPaidDate the mPaidDate to set
     */
    public void setPaidDate(String mPaidDate) {
        this.mPaidDate = mPaidDate;
    }

    /**
     * @return the mMakeDoneTime
     */
    public String getMakeDoneTime() {
        return mMakeDoneTime;
    }

    /**
     * @param mMakeDoneTime the mMakeDoneTime to set
     */
    public void setMakeDoneTime(String mMakeDoneTime) {
        this.mMakeDoneTime = mMakeDoneTime;
    }

    /**
     * @return the mOnlinePaymentMethod
     */
    public String getOnlinePaymentMethod() {
        return mOnlinePaymentMethod;
    }

    /**
     * @param mOnlinePaymentMethod the mOnlinePaymentMethod to set
     */
    public void setOnlinePaymentMethod(String mOnlinePaymentMethod) {
        this.mOnlinePaymentMethod = mOnlinePaymentMethod;
    }

    /**
     * @return the bReprintSession
     */
    public boolean isReprintSession() {
        return bReprintSession;
    }

    /**
     * @param bReprintSession the bReprintSession to set
     */
    public void setReprintSession(boolean bReprintSession) {
        this.bReprintSession = bReprintSession;
    }

    /**
     * @return the mSessionName
     */
    public String getSessionName() {
        return mSessionName;
    }

    /**
     * @param mSessionName the mSessionName to set
     */
    public void setSessionName(String mSessionName) {
        this.mSessionName = mSessionName;
    }

    /**
     * @return the mAccountNumber
     */
    public int getAccountNumber() {
        return mAccountNumber;
    }

    /**
     * @param mAccountNumber the mAccountNumber to set
     */
    public void setAccountNumber(int mAccountNumber) {
        this.mAccountNumber = mAccountNumber;
    }

    /**
     * @return the mTransactionID
     */
    public String getTransactionID() {
        return mTransactionID;
    }

    /**
     * @param mTransactionID the mTransactionID to set
     */
    public void setTransactionID(String mTransactionID) {
        this.mTransactionID = mTransactionID;
    }

    /**
     * @return the mTenderedValue
     */
    public String getTenderedValue() {
        return mTenderedValue;
    }

    /**
     * @param mTenderedValue the mTenderedValue to set
     */
    public void setTenderedValue(String mTenderedValue) {
        this.mTenderedValue = mTenderedValue;
    }

    /**
     * @return the bMustBePaidFull
     */
    public boolean isMustBePaidFull() {
        return bMustBePaidFull;
    }

    /**
     * @param bMustBePaidFull the bMustBePaidFull to set
     */
    public void setMustBePaidFull(boolean bMustBePaidFull) {
        this.bMustBePaidFull = bMustBePaidFull;
    }

    /**
     * @return the mPrinterNumber
     */
    public int getPrinterNumber() {
        return mPrinterNumber;
    }

    /**
     * @param mPrinterNumber the mPrinterNumber to set
     */
    public void setPrinterNumber(int mPrinterNumber) {
        this.mPrinterNumber = mPrinterNumber;
    }

    /**
     * @return the mPrinterID
     */
    public String getPrinterID() {
        return mPrinterID;
    }

    /**
     * @param mPrinterID the mPrinterID to set
     */
    public void setPrinterID(String mPrinterID) {
        this.mPrinterID = mPrinterID;
    }

    /**
     * @return the bSorted
     */
    public boolean isSorted() {
        return bSorted;
    }

    /**
     * @param bSorted the bSorted to set
     */
    public void setSorted(boolean bSorted) {
        this.bSorted = bSorted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mOrderID);
        dest.writeString(mTenderAmt);
        dest.writeString(mCustomerName);
        dest.writeString(mPickupTime);
        dest.writeString(mTenderTypeid);

    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {

        public Order createFromParcel(Parcel source) {

            Order order = new Order();
            order.setOrderID(source.readString());
            order.setTenderAmt(source.readString());
            order.setCustomerName(source.readString());
            order.setPickupTime(source.readString());
            order.setTenderTypeid(source.readString());




            return order;

        }

        public Order[] newArray(int size) {

            return new Order[size];

        }

    };
}
