package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRecharge extends Object {

    Recharge rc;

    @BeforeEach
    void setUp() {
        rc = new Recharge(1,"2019-05-16", 20, 1);
    }
    @AfterEach
    void tearDown() { rc= null; }

    @Test
    void getEmployeeId() {
        int id= rc.getEmployeeId();
        assertEquals(1, id);
    }
}