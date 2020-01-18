package it.polito.latazza.data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class TestPersonalAccount extends Object {

    PersonalAccount pa;

    @BeforeEach
    void setUp() {
        pa = new PersonalAccount(20, 1);

    }
    @AfterEach
    void tearDown() { pa=null; }


    @Test
    void getBalance() {
        int b = pa.getBalance();
        assertEquals(20, b);
    }

    @Test
    void getAccountId() {
        int id = pa.getAccountId();
        assertEquals(1, id);
    }

    @Test
    void addBalanceTC1() {
        pa.addBalance(10);
        int b = pa.getBalance();
        assertEquals(30, b);
    }

    @Test
    void addBalanceTC2() {
        assertThrows(ArithmeticException.class, () -> { pa.addBalance(MAX_VALUE); });
    }

    @Test
    void decBalance() {
        pa.decBalance(10);
        int b = pa.getBalance();
        assertEquals(10, b);
    }

    @Test
    void decBalance2() {
        pa.decBalance(20);
        assertThrows(ArithmeticException.class, () -> { pa.decBalance(MIN_VALUE); });
    }
}