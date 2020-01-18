package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBoxPurchase extends Object {

    BoxPurchase bp;
    CapsuleType ct;

    @BeforeEach
    void setUp() {

        ct = new CapsuleType( 1, "Arabic Coffee", 20, 1, 50, 0, 0, 0);
        bp = new BoxPurchase(1, "2019-05-17", 20,5, ct);

    }
    @AfterEach
    void tearDown() {
        bp = null;
    }

    @Test
    void getQuantity() {
        int qt = bp.getQuantity();
        System.out.println("Quantity is " + qt);
        assertEquals(5, qt);
    }

    @Test
    void getCapsuleType() {
        CapsuleType capsType = bp.getCapsuleType();
        assertEquals(ct, capsType);
    }
}