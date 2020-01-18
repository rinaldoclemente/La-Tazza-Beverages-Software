package it.polito.latazza.data;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestCapsuleType extends Object {
    CapsuleType ct;

    @BeforeEach
    void setUp() {
        ct = new CapsuleType(1, "coffee", 5, 2, 10, 10, 10, 5);
    }
    @BeforeEach
    void tearDown() { }

    @Test
    void addOldQuantityTC1() {
        ct.setOldQuantity(0);
        ct.addOldQuantity(100);
        assertEquals(100, ct.getOldQuantity().intValue());
    }

    @Test
    void addOldQuantityTC2() {
        ct.setOldQuantity(0);
        assertThrows(ArithmeticException.class, () -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ct.addOldQuantity(1);
            }
            ct.addOldQuantity(1);
        });

    }

    @Test
    void addOldQuantityTC3() {
        ct.setOldQuantity(0);
        ct.addOldQuantity(-1);
        assertEquals(0, ct.getOldQuantity().intValue());
    }

    @Test
    void addOldQuantityTC4() {
        ct.setOldQuantity(0);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            ct.addOldQuantity(-1);
        }
        ct.addOldQuantity(-1);
        assertEquals(0, ct.getOldQuantity().intValue());
    }

    @Test
    void decOldQuantityTC1() {
        ct.setOldQuantity(100);
        ct.decOldQuantity(100);
        assertEquals(0, ct.getOldQuantity().intValue());
    }

    @Test
    void decOldQuantityTC2() {
        ct.setOldQuantity(100);
        for (int i = 0; i < 100; i++) {
            ct.decOldQuantity(1);
        }
        ct.decOldQuantity(1);
        assertEquals(0, ct.getOldQuantity().intValue());
    }

    @Test
    void decOldQuantityTC3() {
        ct.setOldQuantity(0);
        ct.decOldQuantity(-1);
        assertEquals(0, ct.getOldQuantity().intValue());
    }

    @Test
    void decOldQuantityTC4() {
        ct.setOldQuantity(100);
        for (int i = 0; i < 100; i++) {
            ct.decOldQuantity(-1);
        }
        ct.decOldQuantity(-1);
        assertEquals(100, ct.getOldQuantity().intValue());
    }

    @Test
    void getId() {
        int id = ct.getId();
        assertEquals(1, id);

    }

    @Test
    void getName() {
        String name = ct.getName();
        assertEquals("coffee", name);
    }

    @Test
    void getOldPrice() {
        int p = ct.getOldPrice();
        assertEquals(5, p);

    }

    @Test
    void getQuantity() {
        int q = ct.getOldQuantity();
        assertEquals(2, q);

    }

    @Test
    void getOldQtyPerBox() {
        int qpb = ct.getOldQtyPerBox();
        assertEquals(10, qpb);

    }

    @Test
    void setId() {
        ct.setId(5);
        int id = ct.getId();
        assertEquals(5, id);

    }

    @Test
    void setName() {
        ct.setName("arabic");
        String name = ct.getName();
        assertEquals("arabic", name);

    }

    @Test
    void setOldPrice() {
        ct.setOldPrice(15);
        int p = ct.getOldPrice();
        assertEquals(15, p);

    }

    @Test
    void setOldQuantity() {
        ct.setOldQuantity(40);
        int q = ct.getOldQuantity();
        assertEquals(40, q);

    }

    @Test
    void setOldQtyPerBox() {
        ct.setOldQtyPerBox(25);
        int qpb = ct.getOldQtyPerBox();
        assertEquals(25, qpb);

    }
}