package it.polito.latazza.data;

/**
 * 
 */
public class PersonalAccount {

    private Integer balance;
    private Integer employeeId;



    /**
     * @param employeeId
     */
    public PersonalAccount(Integer balance,Integer employeeId) {
        this.balance = balance;
        this.employeeId = employeeId;
    }

    /**
     * @return
     */
    public Integer getBalance() {

        return this.balance;
    }

    /**
     * @return
     */
    public Integer getAccountId() {

        return this.employeeId;
    }

    /**
     * @param amount 
     * @return
     */
    public void addBalance(Integer amount) {
//        this.balance += amount;
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