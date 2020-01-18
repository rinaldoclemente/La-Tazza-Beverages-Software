package it.polito.latazza.data;

/**
 * 
 */
public class BoxPurchase extends Transaction {

    private Integer quantity;
    private CapsuleType capsuleType;

    public BoxPurchase(Integer id, String date, Integer amount, Integer quantity, CapsuleType capsuleType) {
        super(id, date, amount);
        this.quantity=quantity;
        this.capsuleType = capsuleType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CapsuleType getCapsuleType() {
        return capsuleType;
    }

}