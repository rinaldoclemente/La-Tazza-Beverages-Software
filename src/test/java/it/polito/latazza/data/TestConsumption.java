package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestConsumption extends Object {

    Consumption c;

    @BeforeEach
    void setUp() {
        c = new Consumption(1, "2019-05-01", 30, 10, 1, 1, true);
    }

    @AfterEach
    void tearDown() { c = null; }

    @Test
    void setQuantity() {
        c.setQuantity(20);
        int res = c.getQuantity();
        assertEquals(20, res);
        tearDown();
    }

    @Test
    void setCapsId() {
        c.setCapsId(20);
        int res = c.getCapsId();
        assertEquals(20, res);
        tearDown();
    }

    @Test
    void getEmployeeId() {
        int res = c.getEmployeeId();
        assertEquals(1, res);
        tearDown();
    }

    @Test
    void isFromAccount() {
        boolean f = c.isFromAccount();
        assertEquals(true, f);
    }

    @Test
    void getQuantity() {
        int res = c.getQuantity();
        assertEquals(10, res);
        tearDown();
    }

    @Test
    void getCapsId() {
        int res = c.getCapsId();
        assertEquals(1, res);
        tearDown();
    }
}