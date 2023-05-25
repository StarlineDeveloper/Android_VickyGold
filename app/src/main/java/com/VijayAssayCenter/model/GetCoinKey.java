package com.VijayAssayCenter.model;

public class GetCoinKey {


    /**
     "SymbolId":"40",
     "Symbol":"Gold 995 (Kolkata Delivery)",
     "Bid":"48106",
     "Ask":"48213",
     "High":"48647",
     "Low":"47800",
     "Status":"False",
     "ProductType":"1",
     "Source":"Gold",
     "Stock":"0",
     "OrderNo":"0",
     "Client":"vickygold\r",
     "SymbolRateType":"unfix",
     "DisplayRateType":"mcx",
     "symbolWeightType":"",
     "UnfixPremium":"0",
     "SymbolStocks":"100000",
     "Initialmargin":"10",
     "Time":"11:55:00 PM",
     "IsDisplay":"True",
     "IsDisplayTrading":"True",
     "IsDisplayTerminal":"True",
     "ContractSize":"",
     "BankInrGold":"",
     "BankInrSilver":"",
     "BankIsDisplay":"False",
     "Grams":"1"
     */


    private String CoinsId="";
    private String CoinsName="";
    private String CoinsBase="";
    private String Ask="";
    private String Bid="";
    private String Stock="";
    private String CoinsImagePath="";


    public String getCoinsId() {
        return CoinsId;
    }
    public String getCoinsName() {
        return CoinsName;
    }

    public String getCoinsBase() {
        return CoinsBase;
    }

    public String getAsk() {
        return Ask;
    }

    public String getBid() {
        return Bid;
    }

    public String getStock() {
        return Stock;
    }

    public String getCoinsImagePath() {
        return CoinsImagePath;
    }


}
