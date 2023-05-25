package com.VijayAssayCenter.model;

public class BankDetailsModel {


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

    private int Bank_id;
    private String AccountName;
    private String BankName;
    private String AccountNo;
    private String Ifsc;
    private String BranchName;
    private String BankLogo;
    private String Cdate;
    private String Mdate;

    public int getBank_id() {
        return Bank_id;
    }

    public void setBank_id(int Bank_id) {
        this.Bank_id = Bank_id;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String AccountNo) {
        this.AccountNo = AccountNo;
    }

    public String getIfsc() {
        return Ifsc;
    }

    public void setIfsc(String Ifsc) {
        this.Ifsc = Ifsc;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getBankLogo() {
        return BankLogo;
    }

    public void setBankLogo(String BankLogo) {
        this.BankLogo = BankLogo;
    }

    public String getCdate() {
        return Cdate;
    }

    public void setCdate(String Cdate) {
        this.Cdate = Cdate;
    }

    public String getMdate() {
        return Mdate;
    }

    public void setMdate(String Mdate) {
        this.Mdate = Mdate;
    }
}
