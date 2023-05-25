package com.VijayAssayCenter.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 30/1/16.
 */
public class GetGenralPrimum {

    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("SymbolMasterList")
    @Expose
    public List<SymbolMasterList> symbolMasterList = null;

    public GetGenralPrimum withStatus(String status) {
        this.status = status;
        return this;
    }

    public GetGenralPrimum withSymbolMasterList(List<SymbolMasterList> symbolMasterList) {
        this.symbolMasterList = symbolMasterList;
        return this;
    }



    public class SymbolMasterList {

        @SerializedName("SymbolId")
        @Expose
        public String symbolId;
        @SerializedName("SymbolName")
        @Expose
        public String symbolName;
        @SerializedName("Source")
        @Expose
        public String source;
        @SerializedName("SourceCalculation")
        @Expose
        public String sourceCalculation;
        @SerializedName("Digits")
        @Expose
        public String digits;
        @SerializedName("Description")
        @Expose
        public String description;
        @SerializedName("IsDisplay")
        @Expose
        public String isDisplay;
        @SerializedName("ProductType")
        @Expose
        public String productType;
        @SerializedName("CreatedDate")
        @Expose
        public String createdDate;
        @SerializedName("UpdatedDate")
        @Expose
        public String updatedDate;
        @SerializedName("Status")
        @Expose
        public String status;
        @SerializedName("DisplayRateType")
        @Expose
        public String displayRateType;
        @SerializedName("MCXPremiumBuy")
        @Expose
        public String mCXPremiumBuy;
        @SerializedName("MCXPremiumSell")
        @Expose
        public String mCXPremiumSell;
        @SerializedName("MCXDivBuy")
        @Expose
        public String mCXDivBuy;
        @SerializedName("MCXDivSell")
        @Expose
        public String mCXDivSell;
        @SerializedName("MCXVatBuy")
        @Expose
        public String mCXVatBuy;
        @SerializedName("MCXVatSell")
        @Expose
        public String mCXVatSell;
        @SerializedName("McxMultiPlyBuy")
        @Expose
        public String mcxMultiPlyBuy;
        @SerializedName("McxMultiPlysell")
        @Expose
        public String mcxMultiPlysell;
        @SerializedName("BankPremiumBuy")
        @Expose
        public String bankPremiumBuy;
        @SerializedName("BankPremiumSell")
        @Expose
        public String bankPremiumSell;
        @SerializedName("BankMultiplySell")
        @Expose
        public String bankMultiplySell;
        @SerializedName("BankMultiplyBuy")
        @Expose
        public String bankMultiplyBuy;
        @SerializedName("BankCustomDutySell")
        @Expose
        public String bankCustomDutySell;
        @SerializedName("BankCustomDutyBuy")
        @Expose
        public String bankCustomDutyBuy;
        @SerializedName("BankMarginSell")
        @Expose
        public String bankMarginSell;
        @SerializedName("BankMarginBuy")
        @Expose
        public String bankMarginBuy;
        @SerializedName("BankVatSell")
        @Expose
        public String bankVatSell;
        @SerializedName("BankVatBuy")
        @Expose
        public String bankVatBuy;
        @SerializedName("BankDivSell")
        @Expose
        public String bankDivSell;
        @SerializedName("BankDivBuy")
        @Expose
        public String bankDivBuy;
        @SerializedName("FixRateBuy")
        @Expose
        public String fixRateBuy;
        @SerializedName("FixRateSell")
        @Expose
        public String fixRateSell;
        @SerializedName("Stocks")
        @Expose
        public String stocks;

        public SymbolMasterList withSymbolId(String symbolId) {
            this.symbolId = symbolId;
            return this;
        }

        public SymbolMasterList withSymbolName(String symbolName) {
            this.symbolName = symbolName;
            return this;
        }

        public SymbolMasterList withSource(String source) {
            this.source = source;
            return this;
        }

        public SymbolMasterList withSourceCalculation(String sourceCalculation) {
            this.sourceCalculation = sourceCalculation;
            return this;
        }

        public SymbolMasterList withDigits(String digits) {
            this.digits = digits;
            return this;
        }

        public SymbolMasterList withDescription(String description) {
            this.description = description;
            return this;
        }

        public SymbolMasterList withIsDisplay(String isDisplay) {
            this.isDisplay = isDisplay;
            return this;
        }

        public SymbolMasterList withProductType(String productType) {
            this.productType = productType;
            return this;
        }

        public SymbolMasterList withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public SymbolMasterList withUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public SymbolMasterList withStatus(String status) {
            this.status = status;
            return this;
        }

        public SymbolMasterList withDisplayRateType(String displayRateType) {
            this.displayRateType = displayRateType;
            return this;
        }

        public SymbolMasterList withMCXPremiumBuy(String mCXPremiumBuy) {
            this.mCXPremiumBuy = mCXPremiumBuy;
            return this;
        }

        public SymbolMasterList withMCXPremiumSell(String mCXPremiumSell) {
            this.mCXPremiumSell = mCXPremiumSell;
            return this;
        }

        public SymbolMasterList withMCXDivBuy(String mCXDivBuy) {
            this.mCXDivBuy = mCXDivBuy;
            return this;
        }

        public SymbolMasterList withMCXDivSell(String mCXDivSell) {
            this.mCXDivSell = mCXDivSell;
            return this;
        }

        public SymbolMasterList withMCXVatBuy(String mCXVatBuy) {
            this.mCXVatBuy = mCXVatBuy;
            return this;
        }

        public SymbolMasterList withMCXVatSell(String mCXVatSell) {
            this.mCXVatSell = mCXVatSell;
            return this;
        }

        public SymbolMasterList withMcxMultiPlyBuy(String mcxMultiPlyBuy) {
            this.mcxMultiPlyBuy = mcxMultiPlyBuy;
            return this;
        }

        public SymbolMasterList withMcxMultiPlysell(String mcxMultiPlysell) {
            this.mcxMultiPlysell = mcxMultiPlysell;
            return this;
        }

        public SymbolMasterList withBankPremiumBuy(String bankPremiumBuy) {
            this.bankPremiumBuy = bankPremiumBuy;
            return this;
        }

        public SymbolMasterList withBankPremiumSell(String bankPremiumSell) {
            this.bankPremiumSell = bankPremiumSell;
            return this;
        }

        public SymbolMasterList withBankMultiplySell(String bankMultiplySell) {
            this.bankMultiplySell = bankMultiplySell;
            return this;
        }

        public SymbolMasterList withBankMultiplyBuy(String bankMultiplyBuy) {
            this.bankMultiplyBuy = bankMultiplyBuy;
            return this;
        }

        public SymbolMasterList withBankCustomDutySell(String bankCustomDutySell) {
            this.bankCustomDutySell = bankCustomDutySell;
            return this;
        }

        public SymbolMasterList withBankCustomDutyBuy(String bankCustomDutyBuy) {
            this.bankCustomDutyBuy = bankCustomDutyBuy;
            return this;
        }

        public SymbolMasterList withBankMarginSell(String bankMarginSell) {
            this.bankMarginSell = bankMarginSell;
            return this;
        }

        public SymbolMasterList withBankMarginBuy(String bankMarginBuy) {
            this.bankMarginBuy = bankMarginBuy;
            return this;
        }

        public SymbolMasterList withBankVatSell(String bankVatSell) {
            this.bankVatSell = bankVatSell;
            return this;
        }

        public SymbolMasterList withBankVatBuy(String bankVatBuy) {
            this.bankVatBuy = bankVatBuy;
            return this;
        }

        public SymbolMasterList withBankDivSell(String bankDivSell) {
            this.bankDivSell = bankDivSell;
            return this;
        }

        public SymbolMasterList withBankDivBuy(String bankDivBuy) {
            this.bankDivBuy = bankDivBuy;
            return this;
        }

        public SymbolMasterList withFixRateBuy(String fixRateBuy) {
            this.fixRateBuy = fixRateBuy;
            return this;
        }

        public SymbolMasterList withFixRateSell(String fixRateSell) {
            this.fixRateSell = fixRateSell;
            return this;
        }

        public SymbolMasterList withStocks(String stocks) {
            this.stocks = stocks;
            return this;
        }

    }
}
