package it.polito.latazza.data;

import it.polito.latazza.dao.*;
import it.polito.latazza.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataImpl extends Object {
    DataInterface di;
    LaTazzaAccount lt;

    @BeforeEach
    void setUp() {
        di = new DataImpl();
        di.reset();
    }

    @AfterEach
    void tearDown() {
        try {
            di.reset();
            DBManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sellCapsulesTC1() {
        PersonalAccount pa=null;
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
            pa = PersonalAccountDAO.read(di.getEmployeesId().get(0));
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        int bal = 0;
        try {
            bal = di.sellCapsules(1, 1, 10, true);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } finally {
            int pab = pa.getBalance();
            pab = pab - 20 / 5 * 10;
            assertEquals(pab, bal);
        }
    }

    @Test
    void sellCapsulesTC2() {
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        assertThrows(NotEnoughCapsules.class, () -> {
            di.sellCapsules(1, 1, 30, true);
        });
    }

    @Test
    void sellCapsulesTC3() {
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        assertThrows(BeverageException.class, () -> {
            di.sellCapsules(1, -1, 30, true);
        });
    }

    @Test
    void sellCapsulesTC4() {
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        assertThrows(EmployeeException.class, () -> {
            di.sellCapsules(-1, 1, 10, true);
        });
    }

    @Test
    void sellCapsulesTC5() {
        int empOldBal=0;
        int empNewBal=0;
        int id = 0;
        try {
            id = di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
            empOldBal = ColleagueDAO.read(id).getPa().getBalance();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        try {
            empNewBal= di.sellCapsules(1, 1, 10, false);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } finally {
            assertEquals(empNewBal, empOldBal);
        }
    }

    @Test
    void sellCapsulesTC6() {
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        assertThrows(EmployeeException.class, () -> {
            di.sellCapsules(null, 1, 10, true);
        });
    }

    @Test
    void sellCapsulesTC7() {
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        assertThrows(BeverageException.class, () -> {
            di.sellCapsules(1, null, 10, true);
        });
    }

    @Test
    void sellCapsulesTC8() {
        PersonalAccount pa=null;
        try {
            di.createEmployee("mario", "rossi");
            di.createBeverage("arabic", 5, 20);
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.rechargeAccount(1, 300);
            di.buyBoxes(1, 5);
            di.updateBeverage(1, "arabic", 5, 10);
            di.buyBoxes(1, 5);
            pa = PersonalAccountDAO.read(di.getEmployeesId().get(0));
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
        int bal = 0;
        try {
            bal = di.sellCapsules(1, 1, 25, true);
            bal = di.sellCapsules(1, 1, 25, true);
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } finally {
            int pab = pa.getBalance();
            pab = pab - 150;
            assertEquals(pab, bal);
        }
    }

    @Test
    void getEmployeesTC1() {
        try {
            di.createEmployee("name1", "surname1");
            di.createEmployee("name2", "surname2");
            di.createEmployee("name3", "surname3");
            Map<Integer, String> Emp = di.getEmployees();
            assertEquals(Emp.size(), 3);
        }catch (EmployeeException e) {
            e.printStackTrace();
        }
    }



    @Test
    void createEmployeeTC1(){
        int id =0;
        try {
            id = di.createEmployee("mario", "rossi");
            assertEquals(id, PersonalAccountDAO.read(1).getAccountId().intValue());
        }
        catch (EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createEmployeeTC2(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee("mario", ""); });
    }
    @Test
    void createEmployeeTC3(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee("", "rossi"); });
    }
    @Test
    void createEmployeeTC4(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee("", null); });
    }
    @Test
    void createEmployeeTC5(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee(null, ""); });
    }
    @Test
    void createEmployeeTC6(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee(null, null); });
    }
    @Test
    void createEmployeeTC7(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee("mario", null); });
    }
    @Test
    void createEmployeeTC8(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee(null, "rossi"); });
    }
    @Test
    void createEmployeeTC9(){
        assertThrows(EmployeeException.class, () -> { di.createEmployee("", ""); });
    }


    @Test
    void buyBoxesTC1() {
        try {
            Integer beverageId = di.createBeverage("coffee", 100, 15);
            Integer employee = di.createEmployee("antonio", "santoro");
            Integer rechargedAmount = di.rechargeAccount(employee, 2000);
            di.buyBoxes(beverageId, 2);
            Integer cashAccountBalance1 = rechargedAmount - 15 * 2;
            assertEquals(cashAccountBalance1, di.getBalance().intValue());
            assertEquals(100 * 2, di.getBeverageCapsules(beverageId).intValue());

            di.updateBeverage(beverageId, "coffee", 100, 20);
            di.buyBoxes(beverageId, 2);
            Integer cashAccountBalance2 = cashAccountBalance1 - 20 * 2;
            assertEquals(cashAccountBalance2, di.getBalance().intValue());
            assertEquals(100 * 2 * 2, di.getBeverageCapsules(beverageId).intValue());

        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void buyBoxesTC2() {
        try {
            Integer beverageId = di.createBeverage("coffee", 100, 15);
            di.buyBoxes(beverageId, 2);
            assertThrows(NotEnoughBalance.class, () -> di.buyBoxes(1, 2));

            di.updateBeverage(beverageId, "coffee", 100, 20);
            di.buyBoxes(beverageId, 2);
            assertThrows(NotEnoughBalance.class, () -> di.buyBoxes(1, 2));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }

    }

    @Test
    void buyBoxesTC3() {
        try {
            Integer beverageId = di.createBeverage("coffee", 100, 15);
            Integer employee = di.createEmployee("antonio", "santoro");
            Integer rechargedAmount = di.rechargeAccount(employee, 100);
            di.buyBoxes(beverageId, 0);
            assertEquals(rechargedAmount, di.getBalance());
            assertEquals(0, di.getBeverageCapsules(beverageId).intValue());
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void buyBoxesTC4() {
        try {
            Integer beverageId = di.createBeverage("coffee", 100, 15);
            Integer employee = di.createEmployee("antonio", "santoro");
            Integer rechargedAmount = di.rechargeAccount(employee, 100);
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 2));
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 10));
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 0));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 2));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 10));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 0));

            di.updateBeverage(beverageId, "coffee", 100, 25);
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 2));
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 10));
            assertThrows(BeverageException.class, () -> di.buyBoxes(-1, 0));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 2));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 10));
            assertThrows(BeverageException.class, () -> di.buyBoxes(null, 0));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }



    @Test
    void sellCapsulesToVisitorTC1(){
        try {
            lt = new LaTazzaAccount(30);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            assertEquals(20, LaTazzaAccountDAO.read().getBalance().intValue());
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertEquals(0, LaTazzaAccountDAO.read().getBalance().intValue());
            di.sellCapsulesToVisitor(1, 20);
            assertEquals(30, LaTazzaAccountDAO.read().getBalance().intValue());
        }
        catch(BeverageException e){
            e.printStackTrace();
        }
        catch(NotEnoughCapsules e){
            e.printStackTrace();
        }
        catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }

    @Test
    void sellCapsulesToVisitorTC2(){
        try {
            lt = new LaTazzaAccount(50);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertThrows(NotEnoughCapsules.class, () -> {
                di.sellCapsulesToVisitor(1, 25);
            });
        }
        catch(BeverageException e){
            e.printStackTrace();
        } catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }

    @Test
    void sellCapsulesToVisitorTC3(){
        try {
            lt = new LaTazzaAccount(50);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertThrows(BeverageException.class, () -> {
                di.sellCapsulesToVisitor(-1, 10);
            });
        }
        catch(BeverageException e){
            e.printStackTrace();
        } catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }

    @Test
    void sellCapsulesToVisitorTC4(){
        try {
            lt = new LaTazzaAccount(50);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertThrows(BeverageException.class, () -> {
                di.sellCapsulesToVisitor(-1, 25);
            });
        }
        catch(BeverageException e){
            e.printStackTrace();
        } catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }

    @Test
    void sellCapsulesToVisitorTC5(){
        try {
            lt = new LaTazzaAccount(50);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertThrows(BeverageException.class, () -> {
                di.sellCapsulesToVisitor(null, 10);
            });
        }
        catch(BeverageException e){
            e.printStackTrace();
        } catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }

    @Test
    void sellCapsulesToVisitorTC6(){
        try {
            lt = new LaTazzaAccount(50);
            LaTazzaAccountDAO.update(lt);
            di.createBeverage("The", 10, 10);
            di.buyBoxes(1, 1);
            di.updateBeverage(1, "The", 10, 20);
            di.buyBoxes(1, 1);
            assertThrows(BeverageException.class, () -> {
                di.sellCapsulesToVisitor(null, 20);
            });
        }
        catch(BeverageException e){
            e.printStackTrace();
        } catch(NotEnoughBalance e){
            e.printStackTrace();
        }

    }
    

    @Test
    void getReportTC1() {
        try {
            Integer eId = di.createEmployee("antonio", "santoro");
            Integer bId = di.createBeverage("coffee", 100, 10);
            di.rechargeAccount(eId, 200);
            di.buyBoxes(bId, 2);
            di.sellCapsules(eId, bId, 1, false);
            di.sellCapsules(eId, bId, 1, true);
            di.sellCapsulesToVisitor(bId, 1);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            List<String> report = di.getReport(today, tomorrow);
            assertTrue(report.get(0).contains("RECHARGE antonio santoro " + String.format("%.2f \u20ac", 2.0).replace(",",".")));
            assertTrue(report.get(1).contains("BUY coffee 2"));
            assertTrue(report.get(2).contains("CASH antonio santoro coffee 1"));
            assertTrue(report.get(3).contains("BALANCE antonio santoro coffee 1"));
            assertTrue(report.get(4).contains("VISITOR coffee 1"));
            assertEquals(5, report.size());
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (DateException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void getReportTC2() {
        try {
            Integer eId = di.createEmployee("antonio", "santoro");
            Integer bId = di.createBeverage("coffee", 100, 10);
            di.rechargeAccount(eId, 200);
            di.buyBoxes(bId, 2);
            di.sellCapsules(eId, bId, 1, false);
            di.sellCapsules(eId, bId, 1, true);
            di.sellCapsulesToVisitor(bId, 1);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertThrows(DateException.class, () -> di.getReport(null, tomorrow));
            assertThrows(DateException.class, () -> di.getReport(today, null));
            assertThrows(DateException.class, () -> di.getReport(tomorrow, today));
            assertThrows(DateException.class, () -> di.getReport(tomorrow, null));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void getBeverageNameTC1() {
        try {
            di.createBeverage("arabic", 5, 30);
        } catch (BeverageException e) {
            e.printStackTrace();
        }
        String name = null;
        try {
            name = di.getBeverageName(1);
        } catch (BeverageException e) {
            e.printStackTrace();
        }
        assertEquals(name, "arabic");
    }

    @Test
    void getBeverageNameTC2() {
        try {
            di.createBeverage("arabic", 5, 30);
        } catch (BeverageException e) {
            e.printStackTrace();
        }
        assertThrows(BeverageException.class, () -> di.getBeverageName(-1));
    }

    @Test
    void getBeverageNameTC3() {
        try {
            di.createBeverage("arabic", 5, 30);
        } catch (BeverageException e) {
            e.printStackTrace();
        }
        assertThrows(BeverageException.class, () -> di.getBeverageName(null));
    }

    @Test
    void getEmployeeReportTC1() {
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("rinaldo", "clemente");
            di.createBeverage("the", 5, 5);
            di.rechargeAccount(1, 2000);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

            assertEquals(3, di.getEmployeeReport(1, today, tomorrow).size());
            assertEquals(3, di.getEmployeeReport(1, today, tomorrow).size());
            assertNotEquals(0, di.getEmployeeReport(1, today, tomorrow).size());
            assertNotEquals(0, di.getEmployeeReport(1, today, tomorrow).size());
            assertTrue(di.getEmployeeReport(1, today, tomorrow).get(0).contains("RECHARGE rinaldo clemente 20.00 \u20ac"));
            assertTrue(di.getEmployeeReport(1, today, tomorrow).get(1).contains("BALANCE rinaldo clemente the 1"));
            assertTrue(di.getEmployeeReport(1, today, tomorrow).get(2).contains("CASH rinaldo clemente the 1"));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (DateException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportTC2() {
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("rinaldo", "clemente");
            di.createBeverage("getReportTest", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertThrows(DateException.class, () -> di.getEmployeeReport(1, tomorrow, today));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportTC3() {
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("rinaldo", "clemente");
            di.createBeverage("the", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

            assertThrows(EmployeeException.class, () -> di.getEmployeeReport(-1, today, tomorrow));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportTC4() {
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("rinaldo", "clemente");
            di.createBeverage("getReportTest", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            di.sellCapsules(1, 1, 1, false);

            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertThrows(EmployeeException.class, () -> di.getEmployeeReport(-1, tomorrow, today));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        }
    }

    @Test
    void getEmployeeNameTC1() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertEquals("Rinaldo", di.getEmployeeName(1));
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeNameTC2() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeName(-1);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeNameTC3() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeName(null);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeSurnameTC1() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertEquals("Clemente",di.getEmployeeSurname(1));
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeSurnameTC2() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeSurname(-1);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeSurnameTC3() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeSurname(null);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeBalanceTC1() {
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.createEmployee("Rinaldo", "Clemente");
            di.rechargeAccount(1, 5000);
            assertEquals(5000,di.getEmployeeBalance(1).intValue());
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeBalanceTC2() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeBalance(-1);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeBalanceTC3() {
        try {
            di.createEmployee("Rinaldo", "Clemente");
            assertThrows(EmployeeException.class, () -> {
                di.getEmployeeBalance(null);
            });
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeesIdTC1() {
        try {
            di.createEmployee("name1", "surname1");
            di.createEmployee("name2", "surname2");
            di.createEmployee("name3", "surname3");
            assertEquals(3, di.getEmployeesId().size());
        } catch(EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createBeverageTC1() {
        try {
            Integer id1 = di.createBeverage("coffee", 100, 10);
            Integer id2 = di.createBeverage("the", 100, 20);
            assertNotEquals(id1, id2);
            assertEquals("coffee", di.getBeverageName(id1));
            assertEquals("the", di.getBeverageName(id2));
            assertEquals(100, di.getBeverageCapsulesPerBox(id1).intValue());
            assertEquals(100, di.getBeverageCapsulesPerBox(id2).intValue());
            assertEquals(10, di.getBeverageBoxPrice(id1).intValue());
            assertEquals(20, di.getBeverageBoxPrice(id2).intValue());
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createBeverageTC2() {
        assertThrows(BeverageException.class, () -> di.createBeverage(null, 100, 10));
        assertThrows(BeverageException.class, () -> di.createBeverage("coffee", null, 10));
        assertThrows(BeverageException.class, () -> di.createBeverage("coffee", 100, null));
        assertThrows(BeverageException.class, () -> di.createBeverage("", 100, 10));
        assertThrows(BeverageException.class, () -> di.createBeverage("coffee", -1, 10));
        assertThrows(BeverageException.class, () -> di.createBeverage("coffee", 100, -1));
    }

    @Test
    void getBeverageCapsulesTC1(){
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(100));
            di.createBeverage("the", 10, 10);
            di.buyBoxes(1, 1);
            assertEquals(10, di.getBeverageCapsules(1).intValue());
        }catch(BeverageException e){
            e.printStackTrace();
        }catch (NotEnoughBalance e){
            e.printStackTrace();
        }
    }

    @Test
    void getBeverageCapsulesTC2(){
        assertThrows(BeverageException.class, () -> di.getBeverageCapsules(-1));
    }

    @Test
    void getBeverageCapsulesTC3(){
        assertThrows(BeverageException.class, () -> di.getBeverageCapsules(null));
    }

    @Test
    void getBeverageBoxPriceTC1(){
        try {
            di.createBeverage("the", 10, 10);
            assertEquals(10, di.getBeverageBoxPrice(1).intValue());
        }catch(BeverageException e){
            e.printStackTrace();
        }

    }

    @Test
    void getBeverageBoxPriceTC2(){
        assertThrows(BeverageException.class, () -> di.getBeverageBoxPrice(-1));
    }

    @Test
    void getBeverageBoxPriceTC3(){
        assertThrows(BeverageException.class, () -> di.getBeverageBoxPrice(null));
    }



    @Test
    void updateEmployeeTC1() {
        try {
            Integer id = di.createEmployee("antonio", "santoro");
            di.updateEmployee(id, "tony", "santoro");
            assertEquals("tony", di.getEmployeeName(id));
            di.updateEmployee(id, "tony", "santor");
            assertEquals("santor", di.getEmployeeSurname(id));

        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateEmployeeTC2() {
        try {
            Integer id = di.createEmployee("antonio", "santoro");
            assertThrows(EmployeeException.class, () -> di.updateEmployee(-1, "tony", "santoro"));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(null, "tony", "santoro"));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(id, null, "santoro"));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(id, "", "santoro"));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(id, "antonio", null));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(id, "antonio", ""));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(-1, "antonio", ""));
            assertThrows(EmployeeException.class, () -> di.updateEmployee(-1, "", ""));
        } catch (EmployeeException e) {
            e.printStackTrace();
        }

    }
    @Test
    void updateBeverageTC1() {
        try {
            Integer id = di.createBeverage("coffee", 100, 10);
            di.updateBeverage(id, "the", 200, 20);
            assertEquals("the", di.getBeverageName(id));
            assertEquals(200, di.getBeverageCapsulesPerBox(id).intValue());
            assertEquals(20, di.getBeverageBoxPrice(id).intValue());
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateBeverageTC2() {
        try {
            Integer id = di.createBeverage("coffee", 100, 10);
            assertThrows(BeverageException.class, () -> di.updateBeverage(-1, "the", 200 ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(null, "the", 200 ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, "", 200 ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, null, 200 ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, "the", -1 ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, "the", null ,20));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, "the", 200 ,-1));
            assertThrows(BeverageException.class, () -> di.updateBeverage(id, "the", 200 ,null));
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBeverageCapsulesPerBoxTC1() {
        try {
            di.createBeverage("name", 1, 1);
            assertNotEquals(0, di.getBeverageCapsulesPerBox(1));
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }


    @Test
    void getBeveragesIdTC1(){
        try {
            Integer id = di.createBeverage("coffee", 100, 10);
            assertEquals(100, di.getBeverageCapsulesPerBox(id).intValue());
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBeverageCapsulesPerBoxTC2() {
        assertThrows(BeverageException.class, () -> di.getBeverageCapsulesPerBox(-1));
        assertThrows(BeverageException.class, () -> di.getBeverageCapsulesPerBox(null));
    }

    @Test
    void getBeveragesTC1() {
        try {
            di.createBeverage("beverage1", 1, 1);
            di.createBeverage("beverage2", 2, 2);
            assertEquals(2, di.getBeverages().size());
            assertNotEquals(3, di.getBeverages().size());
        } catch (BeverageException e) {
            e.printStackTrace();
        }
        Map<Integer, String> beverages = di.getBeverages();
        assertEquals(beverages.size(), 2);
    }


    @Test
    void rechargeAccountTC1() {
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.createEmployee("Mario", "Rossi");
            int aic = di.rechargeAccount(1, 50);
            assertEquals(50, aic);
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }


    @Test
    void rechargeAccountTC2() {
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.createEmployee("Mario", "Rossi");
            assertThrows(EmployeeException.class, () ->di.rechargeAccount(-1, 50));
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void rechargeAccountTC3() {
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.createEmployee("Mario", "Rossi");
            assertThrows(EmployeeException.class, () ->di.rechargeAccount(null, 50));
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void rechargeAccount() {
        try {
            LaTazzaAccountDAO.update(new LaTazzaAccount(0));
            di.createEmployee("antonio", "santoro");
            di.createEmployee("antonio", "santoro");
            int id1 = di.rechargeAccount(1, 500);
            int id2 = di.rechargeAccount(2, 500);
            assertEquals(PersonalAccountDAO.read(1).getBalance().intValue(), id1);
            assertEquals(PersonalAccountDAO.read(2).getBalance().intValue(), id2);
        } catch (EmployeeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void resetTC1() {
            assertTrue(BoxPurchaseDAO.readAll().size()==0);
            assertTrue(CapsuleTypeDAO.readAll().size()==0);
            assertTrue(ColleagueDAO.readAll().size()==0);
            assertTrue(ConsumptionDAO.readAll().size()==0);
            assertEquals(0, LaTazzaAccountDAO.read().getBalance().intValue());
            assertNull( PersonalAccountDAO.read(1));
            assertTrue(RechargeDAO.readAll().size()==0);
        }


    @Test
    void getReportWTC1() {
       // 0 iterations
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("antonio", "santoro");
            di.createBeverage("getReportTest", 5, 5);
//            di.rechargeAccount(1, 200);
//            di.buyBoxes(1, 2);
//            di.sellCapsules(1, 1, 1, true);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getReport(today, tomorrow));
            assertNotNull(di.getReport(today, today));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (DateException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getReportWTC2() {
        // 1 iteration
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("antonio", "santoro");
            di.createBeverage("getReportTest", 5, 5);
            di.rechargeAccount(1, 200);
//            di.buyBoxes(1, 2);
//            di.sellCapsules(1, 1, 1, true);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getReport(today, tomorrow));
//            assertNotNull(di.getReport(today, today));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (DateException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getReportWTC3() {
        // 600 iteration
        LaTazzaAccountDAO.update(new LaTazzaAccount(100000));
        try {
            di.createEmployee("antonio", "santoro");
            di.createBeverage("getReportTest", 5, 5);
            for(int i=0; i<100; i++) {
                di.rechargeAccount(1, 200);
                di.buyBoxes(1, 2);
                di.sellCapsules(1, 1, 1, true);
            }
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getReport(today, tomorrow));
            assertNotNull(di.getReport(today, today));
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (DateException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportWTC1(){
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("Rinaldo", "Clemente");
            di.createBeverage("The", 5, 5);
            di.buyBoxes(1, 2);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getEmployeeReport(1, today, tomorrow));
            assertNotNull(di.getEmployeeReport(1, today, today));
        } catch (DateException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportWTC2(){
        LaTazzaAccountDAO.update(new LaTazzaAccount(100));
        try {
            di.createEmployee("Rinaldo", "Clemente");
            di.createBeverage("The", 5, 5);
            di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            di.sellCapsules(1, 1, 1, true);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getEmployeeReport(1, today, tomorrow));
        } catch (DateException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        }
    }

    @Test
    void getEmployeeReportWTC3(){
        LaTazzaAccountDAO.update(new LaTazzaAccount(10000));
        try {
            di.createEmployee("Rinaldo", "Clemente");
            di.createBeverage("The", 500, 500);
            for(int i =0; i<100; i++) di.rechargeAccount(1, 200);
            di.buyBoxes(1, 2);
            for(int i =0; i<100; i++) di.sellCapsules(1, 1, 1, true);
            Date today = new Date(System.currentTimeMillis());
            Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
            assertNotNull(di.getEmployeeReport(1, today, tomorrow));
        } catch (DateException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (NotEnoughBalance notEnoughBalance) {
            notEnoughBalance.printStackTrace();
        } catch (BeverageException e) {
            e.printStackTrace();
        } catch (NotEnoughCapsules notEnoughCapsules) {
            notEnoughCapsules.printStackTrace();
        }
    }



}