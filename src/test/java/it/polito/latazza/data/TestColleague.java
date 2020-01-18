package it.polito.latazza.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestColleague extends Object {
    Colleague c;
    PersonalAccount pa;


    @BeforeEach
    void setUp() {
        pa = new PersonalAccount(30, 1);
        c = new Colleague(1, "marco", "testa", pa);
    }

    @AfterEach
    void tearDown() { }

    @Test
    void getName() {
        assertEquals("marco", c.getName());
    }

    @Test
    void getSurname() {
        assertEquals("testa", c.getSurname());
    }

    @Test
    void getId() {
        int res = c.getId();
        assertEquals(1, res);
    }

    @Test
    void setId() {
        c.setId(10);
        int res = c.getId();
        assertEquals(10, res);
    }

    @Test
    void setName() {
        c.setName("Marco");
        String res = c.getName();
        assertEquals("Marco", res);
    }

    @Test
    void setSurname() {
        c.setSurname("Testa");
        String res = c.getSurname();
        assertEquals("Testa", res);
    }

    @Test
    void getPa() {
        PersonalAccount patest = c.getPa();
        assertNotNull(patest);
    }

    @Test
    void setPa() {
        c.setPa(pa);
        PersonalAccount patest = c.getPa();
        assertEquals(pa, patest);
    }

}