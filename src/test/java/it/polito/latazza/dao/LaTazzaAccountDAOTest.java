package it.polito.latazza.dao;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.data.LaTazzaAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LaTazzaAccountDAOTest {
    DataImpl di;

    @BeforeEach
    void setUp() {
        di = new DataImpl();
        DBManager.createLaTazzaTable();
    }

     @AfterEach
    void tearDown() {
        try {
            DBManager.closeConnection();
            DBManager.deleteDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readTC1() {
        LaTazzaAccountDAO.delete();
        assertNull(LaTazzaAccountDAO.read());
    }

    @Test
    void readTC2() {
        DBManager.deleteLaTazzaTable();
        assertNull(LaTazzaAccountDAO.read());
        // NOTE: the SQLException is catched inside the read() method
        // so it cannot be thrown till the test case but the object return is still null
        DBManager.createLaTazzaTable();
    }

    @Test
    void readTC3() {
        LaTazzaAccountDAO.write(new LaTazzaAccount(0));
        assertNotNull(LaTazzaAccountDAO.read());
    }

    @Test
    void resetTC4() {
        DBManager.deleteLaTazzaTable();
        assertNull(LaTazzaAccountDAO.read());
        DBManager.createLaTazzaTable();
    }

    @Test
    void writeTC1() {
        assertEquals(0, LaTazzaAccountDAO.write(null).intValue());
    }

    @Test
    void writeTC2() {
        assertEquals(1, LaTazzaAccountDAO.write(new LaTazzaAccount(0)).intValue());
    }

    @Test
    void writeTC3() {
        assertNotEquals(1, LaTazzaAccountDAO.write(null));
    }

    @Test
    void writeTC4() {
        DBManager.deleteLaTazzaTable();
        assertEquals(1, LaTazzaAccountDAO.write(new LaTazzaAccount(0)).intValue());
        DBManager.createLaTazzaTable();
    }

    @Test
    void updateTC1() {
        DBManager.deleteLaTazzaTable();
        LaTazzaAccount laTazzaAccount = new LaTazzaAccount(0);
        laTazzaAccount.addBalance(10);
        assertEquals(0, LaTazzaAccountDAO.update(laTazzaAccount));
        DBManager.createLaTazzaTable();
    }

    @Test
    void updateTC2() {
        assertEquals(0, LaTazzaAccountDAO.update(null));
    }

    @Test
    void updateTC4() {
        LaTazzaAccount laTazzaAccount = new LaTazzaAccount(0);
        LaTazzaAccountDAO.delete();
        LaTazzaAccountDAO.write(laTazzaAccount);
        laTazzaAccount.addBalance(10);
        assertEquals(1, LaTazzaAccountDAO.update(laTazzaAccount));
    }

    @Test
    void deleteTC1() {
        LaTazzaAccountDAO.delete();
        assertNull(LaTazzaAccountDAO.read());
    }

    @Test
    void deleteTC2() {
        DBManager.deleteLaTazzaTable();
        LaTazzaAccountDAO.delete();
        assertNull(LaTazzaAccountDAO.read());
        DBManager.createLaTazzaTable();
    }
}