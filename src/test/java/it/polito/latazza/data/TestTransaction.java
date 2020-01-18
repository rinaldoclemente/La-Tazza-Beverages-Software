package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTransaction extends Object {

    Transaction t;
    @BeforeEach
    private void setUp() {
        t = new Transaction(3, "2019-01-18", 10);
    }

    @AfterEach
    private void tearDown() { t= null; }

    @Test
    void gettId() {
        int id = t.gettId();
        assertEquals(3, id);
    }

    @Test
    void settId() {
        t.settId(6);
        int id = t.gettId();
        assertEquals(6, id);
    }

    @Test
    void getDate() {
        String date = t.getDate();
        assertEquals("2019-01-18", date);
    }

    @Test
    void setDate() {
        t.setDate("2017-08-12");
        String d = t.getDate();
        assertEquals("2017-08-12", d);
    }

    @Test
    void getAmount() {
        int a = t.getAmount();
        assertEquals(10, a);
    }

    @Test
    void setAmount() {
        t.setAmount(17);
        int a = t.getAmount();
        assertEquals(17, a);
    }

    @Test
    void addTransaction() {
        t.addTransaction("2012-03-19", 40);
        int a = t.getAmount();
        assertEquals(40, a);
        String d = t.getDate();
        assertEquals("2012-03-19", d);

    }
}