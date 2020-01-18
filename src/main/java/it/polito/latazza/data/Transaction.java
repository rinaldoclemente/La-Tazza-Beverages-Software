package it.polito.latazza.data;

/**
 * 
 */
public class Transaction {

    private Integer tId;
    private String date;
    private Integer amount;


    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Default constructor
     */
    public  Transaction(Integer tid, String date, Integer amount) {
        this.tId = tid;
        this.date = date;
        this.amount = amount;
    }


    /**
     * @param date 
     * @param amount
     */
    public void addTransaction(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

}