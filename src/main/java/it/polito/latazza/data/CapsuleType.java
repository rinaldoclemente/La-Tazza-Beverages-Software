package it.polito.latazza.data;

/**
 * 
 */
public class CapsuleType {

    private Integer id;
    private String name;
    private Integer oldPrice, newPrice;
    private Integer oldQuantity, newQuantity;
    private Integer oldQtyPerBox, newQtyPerBox;

    public CapsuleType( Integer id, String name, Integer oldPrice, Integer oldQuantity, Integer oldQtyPerBox,  Integer newPrice, Integer newQuantity, Integer newQtyPerBox){

        this.id = id;
        this.name = name;
        this.oldPrice = oldPrice;
        this.oldQuantity = oldQuantity;
        this.oldQtyPerBox = oldQtyPerBox;
        this.newPrice = newPrice;
        this.newQuantity = newQuantity;
        this.newQtyPerBox = newQtyPerBox;
    }

    public void addOldQuantity( Integer amount ){
        if(amount<0) return;
        this.oldQuantity = Math.addExact(this.oldQuantity, amount);
    }
    public void decOldQuantity( Integer amount ){
        if(amount<0 || oldQuantity==0) return;
        this.oldQuantity = Math.subtractExact(this.oldQuantity, amount);
    }

    public void addNewQuantity( Integer amount ){
        if(amount<0) return;
        this.newQuantity = Math.addExact(this.newQuantity, amount);
    }
    public void decNewQuantity( Integer amount ){
        if(amount<0 || newQuantity==0) return;
        this.newQuantity = Math.subtractExact(this.newQuantity, amount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Integer getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
    }

    public Integer getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(Integer oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Integer getOldQtyPerBox() {
        return oldQtyPerBox;
    }

    public void setOldQtyPerBox(Integer oldQtyPerBox) {
        this.oldQtyPerBox = oldQtyPerBox;
    }

    public Integer getNewQtyPerBox() {
        return newQtyPerBox;
    }

    public void setNewQtyPerBox(Integer newQtyPerBox) {
        this.newQtyPerBox = newQtyPerBox;
    }
}