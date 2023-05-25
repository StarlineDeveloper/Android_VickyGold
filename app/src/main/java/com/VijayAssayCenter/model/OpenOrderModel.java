package com.VijayAssayCenter.model;

public class OpenOrderModel {


    /**
     * Bank_id : 6
     * AccountName : Mahesh Bullion
     * BankName : State Bank of India
     * AccountNo : 8655365625
     * Ifsc : IFSC Code.
     * BranchName : Branch Name
     * BankLogo : ../Bankimages/sbi.jpg
     * Cdate : /Date(1523357788350)/
     * Mdate : /Date(1538130124613)/
     */


    private String SymbolName;
    private String TradeType;
    private String TradeMode;
    private String Volume;
    private String DealNo ;
    private String OpenTradeDateTime  ;
    private String ClosePrice;
    private String Rate;
    private String Prize;
    private String Source;
    private String OpenOrderID ;
    private String SymbolID;



    public String getSymbolName() {
        return SymbolName;
    }
    public String getTradeMode() {
        return TradeMode;
    }
    public String getOpenTradeDateTime () {
        return OpenTradeDateTime ;
    }
    public String getTradeType() {
        return TradeType;
    }
    public String getVolume() {
        return Volume;
    }
    public String getDealNo () {
        return DealNo ;
    }

    public String getClosePrice () {
        return ClosePrice ;
    }
    public String getRate() {
        return Rate ;
    }
    public String getPrize() {
        return Prize ;
    }

    public String getSource() {
        return Source ;
    }
    public String getOpenOrderID() {
        return OpenOrderID  ;
    }
    public String getSymbolID() {
        return SymbolID  ;
    }


}
