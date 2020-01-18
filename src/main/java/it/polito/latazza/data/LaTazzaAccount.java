package it.polito.latazza.data;
/**
 * 
 */
public class LaTazzaAccount {

    private Integer balance;



    /**
     * @param balance
     */
    public LaTazzaAccount(Integer balance) {

        this.balance = balance;
    }

    /**
     * @return
     */
    public Integer getBalance() {

        return this.balance;
    }

    /**
     * @param amount 
     * @return
     */
    public void addBalance(Integer amount) {
//        this.balance = this.balance + amount;
        this.balance = Math.addExact(this.balance, amount);
        return;
    }

    /**
     * @param amount 
     * @return
     */
    public void decBalance(Integer amount) {
//        this.balance = this.balance - amount;
        this.balance = Math.subtractExact(this.balance, amount);
        return;
    }

}