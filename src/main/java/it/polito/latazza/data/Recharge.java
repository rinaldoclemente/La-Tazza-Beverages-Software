package it.polito.latazza.data;

/**
 * 
 */
public class Recharge extends Transaction {

    private Integer employeeId;

    /**
     * @param id 
     * @param date 
     * @param amount 
     * @param employeeId
     */
    public Recharge(Integer id, String date, Integer amount, Integer employeeId) {
        super(id, date, amount);
        this.employeeId=employeeId;
    }

    /**
     * @return
     */
    public Integer getEmployeeId() {
        return this.employeeId;
    }

}