package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestLaTazzaAccount extends Object {

    LaTazzaAccount lta;

    @BeforeEach
    void setUp() {
        lta = new LaTazzaAccount(0);
    }

    @AfterEach
    void tearDown() {
        lta = null;
    }


    @Test
    void testAddBalanceTC1() {
        int res = lta.getBalance();
        System.out.println("Balance is " + res);
        assertEquals(0, res);
        lta.addBalance(10);
        res = lta.getBalance();
        System.out.println("Balance is " + res);
        assertEquals(10, res);
        tearDown();
    }

    @Test
    void testAddBalanceTC2() {
        int res = lta.getBalance();
        System.out.println("Balance is " + res);
        lta.addBalance(1);
        assertThrows(ArithmeticException.class, () -> { lta.addBalance(MAX_VALUE); });
        tearDown();
    }

    @Test
        void testDecBalanceTC1() {
        int res = lta.getBalance();
        System.out.println("Balance is " + res);
        assertEquals(0, res);
        lta.decBalance(10);
        res = lta.getBalance();
        System.out.println("Balance is " + res);
        assertEquals(-10, res);
        tearDown();
    }
    @Test
    void testDecBalanceTC2() {
        int res = lta.getBalance();
        System.out.println("Balance is " + res);
        assertEquals(0, res);
        assertThrows(ArithmeticException.class, () -> { lta.decBalance(MIN_VALUE); });
        tearDown();
    }
}