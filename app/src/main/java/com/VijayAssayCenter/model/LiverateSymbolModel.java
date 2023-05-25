package com.VijayAssayCenter.model;

import java.util.List;

public class LiverateSymbolModel {

    /**
     * Comex : [{"SymbolName":"GOLD SPOT","Symbol":"XAUUSD","Bid":"1272.15","Ask":"1272.55","High":"1277.69","Low":"1271.16","DateTime":"2019/05/02 12:52:28","BidStatus":"up","AskStatus":"up","Date":"2019/05/02","Time":"12:52:28"},{"SymbolName":"SILVER SPOT","Symbol":"XAGUSD","Bid":"14.65","Ask":"14.67","High":"14.71","Low":"14.61","DateTime":"2019/05/02 12:51:57","BidStatus":"up","AskStatus":"eq","Date":"2019/05/02","Time":"12:51:57"},{"SymbolName":"INR SPOT","Symbol":"INRSpot","Bid":"69.5925","Ask":"69.6000","High":"69.6225","Low":"69.4875","DateTime":"2019/05/02 12:52:26","BidStatus":"up","AskStatus":"eq","Date":"2019/05/02","Time":"12:52:26"}]
     * MCX : [{"SymbolName":"GOLD FUTURE","Symbol":"GLD","Bid":"31475","Ask":"31481","High":"31530","Low":"31430","DateTime":"2019/05/02 12:52:16","BidStatus":"eq","AskStatus":"up","Date":"2019/05/02","Time":"12:52:16"},{"SymbolName":"GLDHL","Symbol":"GLDHL","Bid":"31430","Ask":"31681","High":"31545","Low":"31430","DateTime":"2019/05/02 11:44:31","BidStatus":"down","AskStatus":"eq","Date":"2019/05/02","Time":"11:44:31"},{"SymbolName":"SILVER FUTURE","Symbol":"SLR","Bid":"36935","Ask":"36940","High":"37021","Low":"36861","DateTime":"2019/05/02 12:52:29","BidStatus":"down","AskStatus":"down","Date":"2019/05/02","Time":"12:52:29"},{"SymbolName":"SLRHL","Symbol":"SLRHL","Bid":"36861","Ask":"37105","High":"37022","Low":"36861","DateTime":"2019/05/02 11:43:32","BidStatus":"down","AskStatus":"eq","Date":"2019/05/02","Time":"11:43:32"}]
     * Next : [{"Symbol":"GLDBullionN","Bid":"30320","Ask":"30321","High":"30418","Low":"30252","DateTime":"2018-11-30 18:15:22","BidStatus":"up","AskStatus":"down","Date":"2018-11-30","Time":"18:15:22"},{"Symbol":"SLRBullionN","Bid":"36367","Ask":"36372","High":"36564","Low":"36307","DateTime":"2018-11-30 18:15:09","BidStatus":"down","AskStatus":"eq","Date":"2018-11-30","Time":"18:15:09"}]
     * Data : [{"DisplayRate":true,"DisplayRateMessage":"Live Rates Currently Not Available.","ProductHeaderDetails":"BUY~true~SELL~true~HIGH/LOW~true~LOW~true~PRODUCTS~True~GOLD FUTURE~SILVER FUTURE~GOLD SPOT~SILVER SPOT~INR SPOT\r\n"}]
     * GeneralPremium : [{"SymbolId":"3","Symbol":"GOLD995","Bid":"--","Ask":"34455","High":"34661","Low":"34403","Status":"eq","DateTime":"2019/05/02 12:52:16","Date":"2019/05/02","Time":"12:52:16","IsDisplay":"True","Source":"GLD","Stock":"1","OrderNo":"1","chkIsTola":"False","DisplayType":"1"},{"SymbolId":"4","Symbol":"GOLD 999","Bid":"32060","Ask":"33069","High":"33189","Low":"33041","Status":"up","DateTime":"2019/05/02 12:52:16","Date":"2019/05/02","Time":"12:52:16","IsDisplay":"True","Source":"GLD","Stock":"1","OrderNo":"4","chkIsTola":"False","DisplayType":"2"},{"SymbolId":"5","Symbol":"SILVER 1 KG","Bid":"0","Ask":"0","High":"37105","Low":"36861","Status":"eq","DateTime":"2019/05/02 12:52:29","Date":"2019/05/02","Time":"12:52:29","IsDisplay":"True","Source":"SLR","Stock":"1","OrderNo":"5","chkIsTola":"False","DisplayType":"3"}]
     * marquee : Welcome to HBR 123...
     */

    private String marquee;
    private List<ComexBean> Comex;
    private List<ComexBean2> Comex2;
    private List<MCXBean> MCX;
    private List<NextBean> Next;
    private List<DataBean> Data;
    private List<GeneralPremiumBean> GeneralPremium;

    public String getMarquee() {
        return marquee;
    }

    public void setMarquee(String marquee) {
        this.marquee = marquee;
    }

    public List<ComexBean> getComex() {
        return Comex;
    }

    public List<ComexBean2> getComex2() {
        return Comex2;
    }



    public void setComex(List<ComexBean> Comex) {
        this.Comex = Comex;
    }

    public void setComex2(List<ComexBean2> Comex2) {
        this.Comex2 = Comex2;
    }
    public List<MCXBean> getMCX() {
        return MCX;
    }

    public void setMCX(List<MCXBean> MCX) {
        this.MCX = MCX;
    }

    public List<NextBean> getNext() {
        return Next;
    }

    public void setNext(List<NextBean> Next) {
        this.Next = Next;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public List<GeneralPremiumBean> getGeneralPremium() {
        return GeneralPremium;
    }

    public void setGeneralPremium(List<GeneralPremiumBean> GeneralPremium) {
        this.GeneralPremium = GeneralPremium;
    }

    public static class ComexBean {
        /**
         * SymbolName : GOLD SPOT
         * Symbol : XAUUSD
         * Bid : 1272.15
         * Ask : 1272.55
         * High : 1277.69
         * Low : 1271.16
         * DateTime : 2019/05/02 12:52:28
         * BidStatus : up
         * AskStatus : up
         * Date : 2019/05/02
         * Time : 12:52:28
         */

        /*"Referance_Id":1,
                "Bullion_Id":1,
                "Source":"gold",
                "Symbol_Name":"gold fut",
                "IsDisplay":true*/

        private String Referance_Id;
        private String Bullion_Id;
        private String Source;
        private String Symbol_Name;
        private String IsDisplay;


        private String Symbol;
        private String Bid;
        private String Ask;
        private String High;
        private String Low;




        public String getReferance_Id() {
            return Referance_Id;
        }

        public void setReferance_Id(String Referance_Id) {
            this.Referance_Id = Referance_Id;
        }

        public String getBullion_Id() {
            return Bullion_Id;
        }

        public void setBullion_Id(String Bullion_Id) {
            this.Bullion_Id = Bullion_Id;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getSymbol_Name() {
            return Symbol_Name;
        }

        public void setSymbol_Name(String Symbol_Name) {
            this.Symbol_Name = Symbol_Name;
        }

        public String getIsDisplay() {
            return IsDisplay;
        }

        public void setIsDisplay(String IsDisplay) {
            this.IsDisplay = IsDisplay;
        }


        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }

        public String getBid() {
            return Bid;
        }

        public void setBid(String Bid) {
            this.Bid = Bid;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String Ask) {
            this.Ask = Ask;
        }

        public String getHigh() {
            return High;
        }

        public void setHigh(String High) {
            this.High = High;
        }

        public String getLow() {
            return Low;
        }

        public void setLow(String Low) {
            this.Low = Low;
        }


    }

    public static class ComexBean2 {
        /**
         * SymbolName : GOLD SPOT
         * Symbol : XAUUSD
         * Bid : 1272.15
         * Ask : 1272.55
         * High : 1277.69
         * Low : 1271.16
         * DateTime : 2019/05/02 12:52:28
         * BidStatus : up
         * AskStatus : up
         * Date : 2019/05/02
         * Time : 12:52:28
         */

        /*"Referance_Id":1,
                "Bullion_Id":1,
                "Source":"gold",
                "Symbol_Name":"gold fut",
                "IsDisplay":true*/

        private String Referance_Id;
        private String Bullion_Id;
        private String Source;
        private String Symbol_Name;
        private String IsDisplay;


        private String Symbol;
        private String Bid;
        private String Ask;
        private String High;
        private String Low;




        public String getReferance_Id() {
            return Referance_Id;
        }

        public void setReferance_Id(String Referance_Id) {
            this.Referance_Id = Referance_Id;
        }

        public String getBullion_Id() {
            return Bullion_Id;
        }

        public void setBullion_Id(String Bullion_Id) {
            this.Bullion_Id = Bullion_Id;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getSymbol_Name() {
            return Symbol_Name;
        }

        public void setSymbol_Name(String Symbol_Name) {
            this.Symbol_Name = Symbol_Name;
        }

        public String getIsDisplay() {
            return IsDisplay;
        }

        public void setIsDisplay(String IsDisplay) {
            this.IsDisplay = IsDisplay;
        }


        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }

        public String getBid() {
            return Bid;
        }

        public void setBid(String Bid) {
            this.Bid = Bid;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String Ask) {
            this.Ask = Ask;
        }

        public String getHigh() {
            return High;
        }

        public void setHigh(String High) {
            this.High = High;
        }

        public String getLow() {
            return Low;
        }

        public void setLow(String Low) {
            this.Low = Low;
        }


    }


    public static class MCXBean {

        private String Referance_Id;
        private String Bullion_Id;
        private String Source;
        private String Symbol_Name;
        private String IsDisplay;


        private String Symbol;
        private String Bid;
        private String Ask;
        private String High;
        private String Low;




        public String getReferance_Id() {
            return Referance_Id;
        }

        public void setReferance_Id(String Referance_Id) {
            this.Referance_Id = Referance_Id;
        }

        public String getBullion_Id() {
            return Bullion_Id;
        }

        public void setBullion_Id(String Bullion_Id) {
            this.Bullion_Id = Bullion_Id;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getSymbol_Name() {
            return Symbol_Name;
        }

        public void setSymbol_Name(String Symbol_Name) {
            this.Symbol_Name = Symbol_Name;
        }

        public String getIsDisplay() {
            return IsDisplay;
        }

        public void setIsDisplay(String IsDisplay) {
            this.IsDisplay = IsDisplay;
        }


        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }

        public String getBid() {
            return Bid;
        }

        public void setBid(String Bid) {
            this.Bid = Bid;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String Ask) {
            this.Ask = Ask;
        }

        public String getHigh() {
            return High;
        }

        public void setHigh(String High) {
            this.High = High;
        }

        public String getLow() {
            return Low;
        }

        public void setLow(String Low) {
            this.Low = Low;
        }


    }

    public static class NextBean {
        /**
         * Symbol : GLDBullionN
         * Bid : 30320
         * Ask : 30321
         * High : 30418
         * Low : 30252
         * DateTime : 2018-11-30 18:15:22
         * BidStatus : up
         * AskStatus : down
         * Date : 2018-11-30
         * Time : 18:15:22
         */

        private String Symbol;
        private String Bid;
        private String Ask;
        private String High;
        private String Low;

        private String DateTime;
        private String BidStatus;
        private String AskStatus;
        private String Date;
        private String Time;

        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }

        public String getBid() {
            return Bid;
        }

        public void setBid(String Bid) {
            this.Bid = Bid;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String Ask) {
            this.Ask = Ask;
        }

        public String getHigh() {
            return High;
        }

        public void setHigh(String High) {
            this.High = High;
        }

        public String getLow() {
            return Low;
        }

        public void setLow(String Low) {
            this.Low = Low;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getBidStatus() {
            return BidStatus;
        }

        public void setBidStatus(String BidStatus) {
            this.BidStatus = BidStatus;
        }

        public String getAskStatus() {
            return AskStatus;
        }

        public void setAskStatus(String AskStatus) {
            this.AskStatus = AskStatus;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }
    }

    public static class DataBean {
        /**
         * DisplayRate : true
         * DisplayRateMessage : Live Rates Currently Not Available.
         * ProductHeaderDetails : BUY~true~SELL~true~HIGH/LOW~true~LOW~true~PRODUCTS~True~GOLD FUTURE~SILVER FUTURE~GOLD SPOT~SILVER SPOT~INR SPOT
         */

        private boolean DisplayRate;
        private String DisplayRateMessage;
        private String ProductHeaderDetails;

        public boolean isDisplayRate() {
            return DisplayRate;
        }

        public void setDisplayRate(boolean DisplayRate) {
            this.DisplayRate = DisplayRate;
        }

        public String getDisplayRateMessage() {
            return DisplayRateMessage;
        }

        public void setDisplayRateMessage(String DisplayRateMessage) {
            this.DisplayRateMessage = DisplayRateMessage;
        }

        public String getProductHeaderDetails() {
            return ProductHeaderDetails;
        }

        public void setProductHeaderDetails(String ProductHeaderDetails) {
            this.ProductHeaderDetails = ProductHeaderDetails;
        }
    }

    public static class GeneralPremiumBean {
        /**
         * SymbolId : 3
         * Symbol : GOLD995
         * Bid : --
         * Ask : 34455
         * High : 34661
         * Low : 34403
         * Status : eq
         * DateTime : 2019/05/02 12:52:16
         * Date : 2019/05/02
         * Time : 12:52:16
         * IsDisplay : True
         * Source : GLD
         * Stock : 1
         * OrderNo : 1
         * chkIsTola : False
         * DisplayType : 1
         */

      /*  SymbolId":"1",
                "Symbol":"GOLD T+1",
                "Bid":"51173",
                "Ask":"51184",
                "High":"51555",
                "Low":"51139",
                "Description":"gold t+1",
                "ProductType":"1",
                "Source":"Gold",
                "Stock":"1",
                "OrderNo":"1",
                "IsDisplayWidget":"False",
                "IsTrade":"True",
                "Grams":"10",
                "SecurityMin":"5",
                "SecurityMax":"30",
                "Client":"karan007\r"*/

        private String SymbolId;
        private String Symbol;
        private String Bid;
        private String Ask;
        private String High;
        private String Low;
        private String IsTrade;
        private String SymbolNameHindi;



        public String getSymbolId() {
            return SymbolId;
        }

        public void setSymbolId(String SymbolId) {
            this.SymbolId = SymbolId;
        }

        public String getIsTrade() {
            return IsTrade;
        }

        public void setIsTrade(String IsTrade) {
            this.IsTrade = IsTrade;
        }

        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }


        public String getSymbolNameHindi() {
            return SymbolNameHindi;
        }

        public void setSymbolNameHindi(String SymbolNameHindi) {
            this.SymbolNameHindi = SymbolNameHindi;
        }


        public String getBid() {
            return Bid;
        }

        public void setBid(String Bid) {
            this.Bid = Bid;
        }

        public String getAsk() {
            return Ask;
        }

        public void setAsk(String Ask) {
            this.Ask = Ask;
        }

        public String getHigh() {
            return High;
        }

        public void setHigh(String High) {
            this.High = High;
        }

        public String getLow() {
            return Low;
        }

        public void setLow(String Low) {
            this.Low = Low;
        }


    }
}
