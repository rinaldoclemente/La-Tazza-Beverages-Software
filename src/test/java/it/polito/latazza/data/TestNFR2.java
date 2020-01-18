package it.polito.latazza.data;

import it.polito.latazza.dao.*;
import it.polito.latazza.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNFR2 extends Object {
    DataInterface di;
    LaTazzaAccount lt;
    CapsuleType ct;
    Colleague c;
    PersonalAccount pa;

    @BeforeEach
    void setUp() {
        di = new DataImpl();
        DBManager.createTables();
        di.reset();
    }

    @AfterEach
    void tearDown() {
        di.reset();
        LaTazzaAccountDAO.update(new LaTazzaAccount(0));
    }

    @Test
    void testUC1min() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);
        pa = new PersonalAccount(100, 1);
        c = new Colleague(1, "name", "surname", pa);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);
        ColleagueDAO.write(c);
        PersonalAccountDAO.write(pa);


        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.sellCapsules(c.getId(), ct.getId(), 2, true);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughCapsules notEnoughCapsules) {
                notEnoughCapsules.printStackTrace();
            } catch (EmployeeException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if (totTime < min)
                min = totTime;
        }
        assertTrue(totTime < 500);
    }


    @Test
    void testUC1avg() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0 , 0);
        lt = new LaTazzaAccount(3000);
        pa = new PersonalAccount(100, 1);
        c = new Colleague(1, "name", "surname", pa);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);
        ColleagueDAO.write(c);
        PersonalAccountDAO.write(pa);


        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.sellCapsules(c.getId(), ct.getId(), 2, true);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughCapsules notEnoughCapsules) {
                notEnoughCapsules.printStackTrace();
            } catch (EmployeeException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime += end - begin;

        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }

    @Test
    void testUC2min() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);

        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);



        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.sellCapsulesToVisitor(ct.getId(), 2);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughCapsules notEnoughCapsules) {
                notEnoughCapsules.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if (totTime < min)
                min = totTime;
        }
        assertTrue(totTime < 500);
    }


    @Test
    void testUC2avg() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);

        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);



        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.sellCapsulesToVisitor(ct.getId(), 2);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughCapsules notEnoughCapsules) {
                notEnoughCapsules.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime += end - begin;

        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }

    @Test
    void testUC3min() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);
        pa = new PersonalAccount(100, 1);
        c = new Colleague(1, "name", "surname", pa);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);
        ColleagueDAO.write(c);
        PersonalAccountDAO.write(pa);



        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.rechargeAccount(c.getId(), 3000);
            } catch (EmployeeException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if (totTime < min)
                min = totTime;
        }

            assertTrue(totTime < 500);
    }


    @Test
    void testUC3avg() {
        ct = new CapsuleType(1, "Coffee", 2, 205, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);
        pa = new PersonalAccount(100, 1);
        c = new Colleague(1, "name", "surname", pa);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);
        ColleagueDAO.write(c);
        PersonalAccountDAO.write(pa);



        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.rechargeAccount(c.getId(), 3000);
            } catch (EmployeeException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime += end - begin;

        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }

    @Test
    void testUC4min() {
        ct = new CapsuleType(1, "Coffee", 2, 2, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);

        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.buyBoxes(ct.getId(), 2);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughBalance neb) {
                neb.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if(totTime < min)
                min = totTime;

        }

        assertTrue(totTime < 500);
    }

    @Test
    void testUC4avg() {
        ct = new CapsuleType(1, "Coffee", 2, 2, 5, 0, 0, 0);
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        CapsuleTypeDAO.write(ct);


        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.buyBoxes(ct.getId(), 2);
            } catch (BeverageException e) {
                e.printStackTrace();
            } catch (NotEnoughBalance neb) {
                neb.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime += end - begin;

        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }

    @Test
    void testUC5min() {
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        try {
            di.createEmployee("marco", "testa");
            di.createBeverage("coffee", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }

        Date today = new Date(System.currentTimeMillis());
        Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.getEmployeeReport(1, today, tomorrow);
            } catch (EmployeeException e) {
                e.printStackTrace();
            } catch (DateException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if (totTime < min)
                min = totTime;
        }
        assertTrue(totTime < 500);
    }


    @Test
    void testUC5avg() {
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        try {
            di.createEmployee("marco", "testa");
            di.createBeverage("coffee", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }

        Date today = new Date(System.currentTimeMillis());
        Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {

                di.getEmployeeReport(1, today, tomorrow);

                long end = System.currentTimeMillis();
                totTime += end - begin;

            } catch (DateException e) {
                e.printStackTrace();
            } catch (EmployeeException e) {
                e.printStackTrace();
            }
        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }



    @Test
    void testUC6min() {
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        try {
            di.createEmployee("marco", "testa");
            di.createBeverage("coffee", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }

        Date today = new Date(System.currentTimeMillis());
        Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        long min = Long.MAX_VALUE;
        long totTime = 0;

        for(int i = 0; i < 10; i++) {
            long begin = System.currentTimeMillis();
            try {
                di.getReport(today, tomorrow);
            } catch (DateException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            totTime = end - begin;
            if (totTime < min)
                min = totTime;
        }
        assertTrue(totTime < 500);
    }


    @Test
    void testUC6avg() {
        lt = new LaTazzaAccount(3000);
        LaTazzaAccountDAO.update(lt);
        try {
            di.createEmployee("marco", "testa");
            di.createBeverage("coffee", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }

        Date today = new Date(System.currentTimeMillis());
        Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        double avgTime = 0;
        long totTime = 0;

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            try {

                di.getReport(today, tomorrow);

                long end = System.currentTimeMillis();
                totTime += end - begin;

            } catch (DateException e) {
                e.printStackTrace();
            }
        }

        avgTime = totTime/(100.0);

        assertTrue(avgTime < 500);

    }








}

