package com.VijayAssayCenter.model;

public class OpenOrderModelCoin {



//        "DealNo":199,
//        "SymbolName":"GOLD 1 GM",
//        "Rate":4660,
//        "Exchange":46600,
//        "Total":4660,
//        "OpenTradeDateTime":"06 Oct 2021 14:34:44:903",
//        "TradeType":"1",
//        "ModifiedDate":"06 Oct 2021 14:34:44:903",

    private String DealNo ;
    private String SymbolName;

    private String Rate;
    private String Exchange;

    private String Total;
    private String TradeType;

    private String OpenTradeDateTime  ;
    private String ModifiedDate  ;
    private String Volume  ;
    private String ClosePrice  ;



    public String getSymbolName() {
        return SymbolName;
    }

    public String getDealNo () {
        return DealNo ;
    }


    public String getClosePrice () {
        return ClosePrice ;
    }

    public String getVolume () {
        return Volume ;
    }


    public String getRate() {
        return Rate;
    }

    public String getExchange() {
        return Exchange;
    }
    public String getTradeType () {
        return TradeType ;
    }
    public String getTotal() {
        return Total;
    }

    public String getOpenTradeDateTime() {
        return OpenTradeDateTime;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }







}
