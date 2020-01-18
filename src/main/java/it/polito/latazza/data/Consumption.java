package it.polito.latazza.data;

/**
 * 
 */
public class Consumption extends Transaction {
    private Integer quantity;
    private Integer capsId;
    private Integer employeeId;
    private boolean fromAccount;

    /**
     * Default constructor
     */

    public Consumption(Integer tid, String date, Integer amount, Integer quantity, Integer capsId, Integer employeeId, boolean fromAccount) {
        super(tid, date, amount);
        this.quantity = quantity;
        this.capsId = capsId;
        this.employeeId = employeeId;
        this.fromAccount = fromAccount;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCapsId(Integer capsId) {
        this.capsId = capsId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public boolean isFromAccount() {
        return fromAccount;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Integer getCapsId() {
        return this.capsId;
    }
}