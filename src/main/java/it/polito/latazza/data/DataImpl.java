package it.polito.latazza.data;

import it.polito.latazza.dao.*;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DataImpl implements DataInterface {

	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		DBManager.createTables();
		Integer updatedBal = 0, newCapNum=0, sub=0;
		Boolean flagNew=false;
		LaTazzaAccount lt = LaTazzaAccountDAO.read();
		if(employeeId == null)
			throw new EmployeeException();
		if(beverageId == null)
			throw new BeverageException();

		if( beverageId<=0 )
			if (employeeId==null) throw new EmployeeException();
		if (numberOfCapsules==null || numberOfCapsules<0) throw new NotEnoughCapsules();
		if( beverageId==null || beverageId<=0 )
			throw new BeverageException();
		if(numberOfCapsules == null)
			throw new NotEnoughCapsules();
		if(numberOfCapsules < 0)
			throw new NotEnoughCapsules();
		CapsuleType ct = CapsuleTypeDAO.read(beverageId);
		if(ct.getOldQuantity()>=numberOfCapsules) {
			newCapNum = Math.subtractExact(ct.getOldQuantity(), numberOfCapsules);
			flagNew=false;
			if(ct.getOldQuantity()==0){
				CapsuleTypeDAO.update(new CapsuleType(ct.getId(), ct.getName(), ct.getNewPrice(), ct.getNewQuantity(), ct.getNewQtyPerBox(), 0, 0, 0));
			}
		}
		else if (ct.getOldQuantity()<numberOfCapsules && ct.getNewQuantity()!=0){
			sub = Math.subtractExact(numberOfCapsules, ct.getOldQuantity());
			newCapNum = Math.subtractExact(ct.getNewQuantity(), sub);
			CapsuleTypeDAO.update(new CapsuleType(ct.getId(), ct.getName(), ct.getNewPrice(), ct.getNewQuantity(), ct.getNewQtyPerBox(), 0, 0, 0));

			flagNew=true;
		}
		else{
			throw new NotEnoughCapsules();
		}
		if(newCapNum<0)
			throw new NotEnoughCapsules();
		if( employeeId<=0 )
			throw new EmployeeException();

		if(fromAccount) {
			PersonalAccount pa = PersonalAccountDAO.read(employeeId);

			if(flagNew==false) {
				pa.decBalance(Math.floorDiv(Math.multiplyExact(numberOfCapsules, ct.getOldPrice()), ct.getOldQtyPerBox()));
				ct.decOldQuantity(numberOfCapsules);
				CapsuleTypeDAO.update(ct);
			}
			else {
				pa.decBalance(Math.floorDiv(Math.multiplyExact(numberOfCapsules - sub, ct.getOldPrice()), ct.getOldQtyPerBox()));
				pa.decBalance(Math.floorDiv(Math.multiplyExact(sub, ct.getNewPrice()), ct.getNewQtyPerBox()));
				ct.decOldQuantity(numberOfCapsules - sub);
				ct.decNewQuantity(sub);
				int newPrice = ct.getNewPrice();
				int newQty = ct.getNewQuantity();
				int newQtyPerBox = ct.getNewQtyPerBox();
				CapsuleTypeDAO.update(new CapsuleType(beverageId, ct.getName(), newPrice, newQty, newQtyPerBox, 0, 0, 0));
			}

			PersonalAccountDAO.update(pa);
			updatedBal = pa.getBalance();
		}
		else{
			if(flagNew==false) {
				ct.decOldQuantity(numberOfCapsules);
				CapsuleTypeDAO.update(ct);
				lt.addBalance(Math.floorDiv(Math.multiplyExact(numberOfCapsules, ct.getOldPrice()), ct.getOldQtyPerBox()));
				LaTazzaAccountDAO.update(lt);
			}
			else{
				ct.decOldQuantity(numberOfCapsules-sub);
				ct.decNewQuantity(sub);
				int newPrice = ct.getNewPrice();
				int newQty = ct.getNewQuantity();
				int newQtyPerBox = ct.getNewQtyPerBox();
				CapsuleTypeDAO.update(new CapsuleType(beverageId, ct.getName(),newPrice,newQty,newQtyPerBox,0,0,0));
				lt.addBalance(Math.floorDiv(Math.multiplyExact(numberOfCapsules-sub, ct.getOldPrice()), ct.getOldQtyPerBox()));
				lt.addBalance(Math.floorDiv(Math.multiplyExact(sub, ct.getOldPrice()), ct.getOldQtyPerBox()));
				LaTazzaAccountDAO.update(lt);
			}

			updatedBal = PersonalAccountDAO.read(employeeId).getBalance();

		}

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = sdf.format(date);
		Consumption com;

		if(flagNew==false){
			com = new Consumption(0, formattedTime, ct.getOldPrice()/ct.getOldQtyPerBox()*numberOfCapsules, numberOfCapsules, beverageId, employeeId, fromAccount);
		}
		else{
			com = new Consumption(0, formattedTime, ct.getOldPrice()/ct.getOldQtyPerBox()*numberOfCapsules-sub, numberOfCapsules, beverageId, employeeId, fromAccount);
			com = new Consumption(0, formattedTime, ct.getNewPrice()/ct.getNewQtyPerBox()*sub, numberOfCapsules, beverageId, employeeId, fromAccount);

		}
		ConsumptionDAO.write(com);
		return updatedBal;
	}


	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		DBManager.createTables();
		Integer updatedBal = 0, newCapNum=0, sub=0;
		Boolean flagNew=false;
		LaTazzaAccount lt;
    	if (beverageId==null || beverageId <0) throw new BeverageException();
    	else if (numberOfCapsules == null || numberOfCapsules<0) throw new NotEnoughCapsules();
		CapsuleType ct= CapsuleTypeDAO.read(beverageId);

		if(ct.getOldQuantity()>=numberOfCapsules) {
			newCapNum = Math.subtractExact(ct.getOldQuantity(), numberOfCapsules);
			flagNew=false;
		}
		else if (ct.getOldQuantity()<numberOfCapsules && ct.getNewQuantity()!=0){
			sub = Math.subtractExact(numberOfCapsules, ct.getOldQuantity());
			newCapNum = Math.subtractExact(ct.getNewQuantity(), sub);
			flagNew=true;
		}
		else{
			throw new NotEnoughCapsules();
		}
		if(newCapNum < 0)
			throw new NotEnoughCapsules();

		if(flagNew==false) {
			ct.decOldQuantity(numberOfCapsules);
			CapsuleTypeDAO.update(ct);
		}
		else {
			ct.decOldQuantity(numberOfCapsules - sub);
			ct.decNewQuantity(sub);
			int newPrice = ct.getNewPrice();
			int newQty = ct.getNewQuantity();
			int newQtyPerBox = ct.getNewQtyPerBox();
			CapsuleTypeDAO.update(new CapsuleType(beverageId, ct.getName(),newPrice,newQty,newQtyPerBox,0,0,0));
		}

		lt= LaTazzaAccountDAO.read();
		if(flagNew==false) {
			lt.addBalance(Math.multiplyExact(numberOfCapsules, Math.floorDiv(ct.getOldPrice(), ct.getOldQtyPerBox())));
		}
		else{
			lt.addBalance(Math.multiplyExact(numberOfCapsules-sub, Math.floorDiv(ct.getOldPrice(), ct.getOldQtyPerBox())));
			lt.addBalance(Math.multiplyExact(sub, Math.floorDiv(ct.getNewPrice(), ct.getNewQtyPerBox())));
		}

        LaTazzaAccountDAO.update(lt);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = sdf.format(date);
		Consumption com;

		if(flagNew==false){
			com = new Consumption(0, formattedTime, ct.getOldPrice()/ct.getOldQtyPerBox()*numberOfCapsules, numberOfCapsules, beverageId, 0, false);		}
		else{
			com = new Consumption(0, formattedTime, ct.getOldPrice()/ct.getOldQtyPerBox()*numberOfCapsules-sub, numberOfCapsules, beverageId, 0, false);
			com = new Consumption(0, formattedTime, ct.getNewPrice()/ct.getNewQtyPerBox()*sub, numberOfCapsules, beverageId, 0, false);

		}
		ConsumptionDAO.write(com);
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException {
		DBManager.createTables();
    	if(id==null || id <=0 || amountInCents<0)
    		throw new EmployeeException();
		PersonalAccount pa = null;

		pa = PersonalAccountDAO.read(id);
		pa.addBalance(amountInCents);
		PersonalAccountDAO.update(pa);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = sdf.format(date);
		RechargeDAO.write(new Recharge(0, formattedTime, amountInCents, id));
		LaTazzaAccount lt = LaTazzaAccountDAO.read();
		lt.addBalance(amountInCents);
		LaTazzaAccountDAO.update(lt);

		if (pa == null) {
			return 0;
		} else
			return pa.getBalance();
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws BeverageException, NotEnoughBalance {
		DBManager.createTables();
    	if(beverageId == null)
    		throw new BeverageException();
		if(beverageId <= 0)
			throw new BeverageException();

    	CapsuleType ct = CapsuleTypeDAO.read(beverageId);
    	if(ct == null)
    		throw new BeverageException();

		LaTazzaAccount lt = LaTazzaAccountDAO.read();
		Integer moneySpent;

		if(ct.getNewPrice()!=0) {
			moneySpent = Math.multiplyExact(ct.getNewPrice(), boxQuantity);
			Integer addedCaps = Math.multiplyExact(boxQuantity, ct.getNewQtyPerBox());
			ct.addNewQuantity(addedCaps);
		}
		else{
			moneySpent = Math.multiplyExact(ct.getOldPrice(), boxQuantity);
			Integer addedCaps = Math.multiplyExact(boxQuantity, ct.getOldQtyPerBox());
			ct.addOldQuantity(addedCaps);
		}

		if(lt.getBalance() < moneySpent)
			throw new NotEnoughBalance();

		lt.decBalance(moneySpent);
		LaTazzaAccountDAO.update(lt);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedTime = sdf.format(date);
		CapsuleTypeDAO.update( ct );
		BoxPurchase bp = new BoxPurchase(0, formattedTime, moneySpent ,boxQuantity, ct);
		BoxPurchaseDAO.write(bp);
	}

	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		DBManager.createTables();
		if(employeeId==null||employeeId<=0) throw new EmployeeException();
		if(startDate==null || endDate==null) throw new DateException();
		ArrayList<String> reports= new ArrayList<>();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		String stringStartDate = sqlStartDate.toString();
		String stringEndDate = sqlEndDate.toString();
		if(startDate.after(endDate)) throw new DateException();
		ArrayList<Consumption> consumptions= ConsumptionDAO.readAll();
		ArrayList<Recharge> recharges= RechargeDAO.readAll();

		DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		java.sql.Date sqlDate = null;
		String stringDate= null;



        for (Recharge r : recharges){
            try {
                date = df.parse(r.getDate());
            }
            catch (ParseException e){
                e.printStackTrace();
            }
            sqlDate = new java.sql.Date(date.getTime());
            stringDate = sqlDate.toString();

            if (stringDate.compareTo(stringStartDate)>=0 && stringDate.compareTo(stringEndDate)<=0 && r.getEmployeeId()==employeeId) {
                reports.add(r.getDate()+" RECHARGE "+ColleagueDAO.read(employeeId).getName()+" "+ColleagueDAO.read(employeeId).getSurname()+" "+String.format("%.2f \u20ac",new Float(r.getAmount()/100)).replace(",","."));
            }
        }
		for( Consumption c : consumptions){
			try {
				date = df.parse(c.getDate());
			}
			catch (ParseException e){
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(date.getTime());
			stringDate = sqlDate.toString();

			if (stringDate.compareTo(stringStartDate)>=0 && stringDate.compareTo(stringEndDate)<=0 && c.isFromAccount() && c.getEmployeeId()==employeeId) {
				try {
					reports.add(c.getDate()+" BALANCE "+ColleagueDAO.read(employeeId).getName()+" "+ColleagueDAO.read(employeeId).getSurname()+" "+getBeverageName(c.getCapsId())+" "+c.getQuantity());
				} catch (BeverageException e) {
					e.printStackTrace();
				}
			}
			else if (stringDate.compareTo(stringStartDate)>=0 && stringDate.compareTo(stringEndDate)<=0 && !c.isFromAccount()&& c.getEmployeeId()==employeeId) {
				try {
					reports.add(c.getDate()+" CASH "+ColleagueDAO.read(employeeId).getName()+" "+ColleagueDAO.read(employeeId).getSurname()+" "+getBeverageName(c.getCapsId())+" "+c.getQuantity());
				} catch (BeverageException e) {
					e.printStackTrace();
				}
			}
		}

		return reports;
	}

    @Override
    public List<String> getReport(Date startDate, Date endDate) throws DateException {
		DBManager.createTables();
    	if(startDate == null || endDate == null)
    		throw new DateException();
        ArrayList<String> report = new ArrayList<>();
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        String stringStartDate = sqlStartDate.toString();
        String stringEndDate = sqlEndDate.toString();
        if(startDate.after(endDate)) throw new DateException();
        ArrayList<Consumption> consumptions = ConsumptionDAO.readAll();
        ArrayList<Recharge> recharges = RechargeDAO.readAll();
        ArrayList<BoxPurchase> boxPurchases = BoxPurchaseDAO.readAll();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date currentDate = null;
        java.sql.Date sqlCurrentDate = null;
        String stringCurrentDate= null;


        Integer amount;
        for(Recharge r : recharges) {
            try {
                currentDate = df.parse(r.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sqlCurrentDate = new java.sql.Date(currentDate.getTime());
            stringCurrentDate = sqlCurrentDate.toString();
            if(stringCurrentDate.compareTo(stringStartDate)>=0 && stringCurrentDate.compareTo(stringEndDate)<=0) {
//                amount = r.getAmount()/100;
                amount = Math.floorDiv(r.getAmount(), 100);
                try {
                    report.add(r.getDate()+" RECHARGE "+getEmployeeName(r.getEmployeeId())+" "+getEmployeeSurname(r.getEmployeeId())+" "+String.format("%.2f \u20ac",new Float(amount)).replace(",","."));

                } catch (EmployeeException e) {
                    e.printStackTrace();
                }

            }
        }
        for(BoxPurchase b : boxPurchases) {
            try {
                currentDate = df.parse(b.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sqlCurrentDate = new java.sql.Date(currentDate.getTime());
            stringCurrentDate = sqlCurrentDate.toString();
            if(stringCurrentDate.compareTo(stringStartDate)>=0 && stringCurrentDate.compareTo(stringEndDate)<=0)
                report.add(b.getDate()+" BUY "+b.getCapsuleType().getName()+" "+b.getQuantity());
        }
        for(Consumption c : consumptions) {
			try {
				currentDate = df.parse(c.getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
			stringCurrentDate = sqlCurrentDate.toString();
            if(stringCurrentDate.compareTo(stringStartDate)>=0 && stringCurrentDate.compareTo(stringEndDate)<=0){
                try {
					if(c.getEmployeeId()==0)
						report.add(c.getDate()+" VISITOR "+getBeverageName(c.getCapsId())+" "+c.getQuantity());
					else if(c.isFromAccount())
						report.add(c.getDate()+" BALANCE "+getEmployeeName(c.getEmployeeId())+" "+getEmployeeSurname(c.getEmployeeId())+" "+getBeverageName(c.getCapsId())+" "+c.getQuantity());
					else
						report.add(c.getDate()+" CASH "+getEmployeeName(c.getEmployeeId())+" "+getEmployeeSurname(c.getEmployeeId())+" "+getBeverageName(c.getCapsId())+" "+c.getQuantity());

				} catch (EmployeeException e) {
					e.printStackTrace();
				} catch (BeverageException e) {
					e.printStackTrace();
				}
			}
        }

        return report;
    }

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		DBManager.createTables();
		if(capsulesPerBox==null || boxPrice==null || name==null || capsulesPerBox<=0|| boxPrice <=0 || name.isEmpty()) throw new BeverageException();
    	return CapsuleTypeDAO.write(new CapsuleType(0, name, boxPrice, 0, capsulesPerBox, 0, 0 , 0));
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		DBManager.createTables();
		Float div = new Float(0);
		Float divOld = new Float(0);
		Float divNew = new Float(0);
		if(id==null || capsulesPerBox==null || boxPrice==null|| name==null || name.isEmpty() || id <= 0 || capsulesPerBox <= 0 || boxPrice <= 0) throw new BeverageException();
    	CapsuleType ct = CapsuleTypeDAO.read(id);
		div = new Float(Float.valueOf(capsulesPerBox)/(Float.valueOf(boxPrice)/100));
		divOld = new Float(Float.valueOf(ct.getOldQtyPerBox())/(Float.valueOf(ct.getOldPrice()/100)));

    	if(ct.getNewQtyPerBox()!=0&&ct.getNewPrice()!=0){
			div = new Float(Float.valueOf(ct.getNewQtyPerBox())/(Float.valueOf(ct.getNewPrice()/100)));
		}
    	if(divOld==div) { //inserisce in old
            CapsuleType ctNew = new CapsuleType(id, name, boxPrice, ct.getOldQuantity(), capsulesPerBox, 0, 0, 0);
            CapsuleTypeDAO.update(ctNew);
        }
		else if(divNew==div) { //inscerisce in new
			CapsuleType ctNew = new CapsuleType(id, name, ct.getOldPrice(), ct.getOldQuantity(), ct.getOldQtyPerBox(), boxPrice, ct.getNewQuantity(), capsulesPerBox);
			CapsuleTypeDAO.update(ctNew);
		}
		else if(ct.getOldQuantity()==0&&ct.getNewQuantity()==0) { //inserisce in old
			CapsuleType ctNew = new CapsuleType(id, name, boxPrice, 0, capsulesPerBox, 0, 0, 0);
			CapsuleTypeDAO.update(ctNew);
		}
    	else if(ct.getNewQtyPerBox()==0&&ct.getNewPrice()==0&&ct.getNewQuantity()==0){ //inserisce in new
			CapsuleType ctNew = new CapsuleType(id, name, ct.getOldPrice(), ct.getOldQuantity(), ct.getOldQtyPerBox(), boxPrice, 0, capsulesPerBox);
			CapsuleTypeDAO.update(ctNew);
		}
    	else{
    		throw new BeverageException();
		}

	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		DBManager.createTables();
    	if(id==null || id <=0 )
    		throw new BeverageException();
		String name = CapsuleTypeDAO.read(id).getName();
		return name;
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		DBManager.createTables();
		if(id==null || id <=0 ) throw new BeverageException();
		CapsuleType ct = CapsuleTypeDAO.read(id);
		Integer nCapPerBox;
		if(ct.getNewQtyPerBox()!=0&&ct.getNewPrice()!=0){
			nCapPerBox = CapsuleTypeDAO.read(id).getNewQtyPerBox();
		}
		else {
			nCapPerBox = CapsuleTypeDAO.read(id).getOldQtyPerBox();
		}
		return nCapPerBox;
	}

	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		DBManager.createTables();
		if(id==null || id <=0 ) throw new BeverageException();
		CapsuleType ct = CapsuleTypeDAO.read(id);
		if(ct.getNewQtyPerBox()!=0&&ct.getNewPrice()!=0){
			return CapsuleTypeDAO.read(id).getNewPrice();
		}
		else {
			return CapsuleTypeDAO.read(id).getOldPrice();
		}
	}

	@Override
	public List<Integer> getBeveragesId() {
		DBManager.createTables();
		List<CapsuleType> capsules = CapsuleTypeDAO.readAll();
		List<Integer> capsulesInt = new ArrayList<>();
		for( CapsuleType capsule: capsules ){
			capsulesInt.add(capsule.getId());
		}
		return capsulesInt;
	}

	@Override
	public Map<Integer, String> getBeverages() {
		DBManager.createTables();
		HashMap<Integer, String> bevMap = new HashMap<>();
		List<CapsuleType> capsules = CapsuleTypeDAO.readAll();
		for( CapsuleType capsule: capsules ){
			bevMap.put(capsule.getId(), capsule.getName());
		}
		return bevMap;
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		DBManager.createTables();
		if(id==null || id <=0 ) throw new BeverageException();
		Integer nCap = CapsuleTypeDAO.read(id).getOldQuantity()+CapsuleTypeDAO.read(id).getNewQuantity();
		return nCap;
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		DBManager.createTables();
		if( name==null || surname==null)
			throw new EmployeeException();
		if(name.isEmpty() || surname.isEmpty())
			throw new EmployeeException();
		int id = ColleagueDAO.write(new Colleague(0,name,surname, new PersonalAccount(0,0)));
		PersonalAccountDAO.write(new PersonalAccount(0, 0));
		return id;
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {
		DBManager.createTables();
    	if(id == null)
    		throw new EmployeeException();
    	if(name == null || surname == null)
    		throw new EmployeeException();

    	if(id <= 0 || name.isEmpty() || surname.isEmpty()) throw new EmployeeException();

    	PersonalAccount pa = PersonalAccountDAO.read(id);
    	Colleague c = new Colleague(id, name, surname, pa);
    	ColleagueDAO.update(c);
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		DBManager.createTables();
		if(id==null||id<=0) throw new EmployeeException();
		String name = ColleagueDAO.read(id).getName();
		return name;
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		DBManager.createTables();
		if(id==null||id<=0) throw new EmployeeException();
		String surname= ColleagueDAO.read(id).getSurname();
		return surname;
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		DBManager.createTables();
		if(id==null||id<=0) throw new EmployeeException();
		int balance= PersonalAccountDAO.read(id).getBalance();
		return balance;
	}

	@Override
	public List<Integer> getEmployeesId() {
		DBManager.createTables();
		ArrayList<Colleague> colleagues= ColleagueDAO.readAll();
		ArrayList<Integer> ids= new ArrayList<>();
		for(Colleague c: colleagues) {
			ids.add(c.getId());
		}
		return ids;
	}

	@Override
	public Map<Integer, String> getEmployees() {
		DBManager.createTables();
		ArrayList<Colleague> colleagues= ColleagueDAO.readAll();
		HashMap<Integer,String> emps= new HashMap<>();
		for(Colleague c: colleagues) {
			emps.put(c.getId(),c.getName()+" "+c.getSurname());
		}
		return emps;
	}

	@Override
	public Integer getBalance() {
		DBManager.createTables();
		return LaTazzaAccountDAO.read().getBalance();

	}

	@Override
	public void reset() {
		DBManager.createTables();
		if (!Files.exists(Paths.get("db/latazza.h2.db"))) {
			System.out.println("db does not exists");
		} else {
			BoxPurchaseDAO.delete();
			CapsuleTypeDAO.delete();
			ColleagueDAO.delete();
			ConsumptionDAO.delete();
			LaTazzaAccountDAO.delete();
			PersonalAccountDAO.delete();
			RechargeDAO.delete();
			LaTazzaAccountDAO.write(new LaTazzaAccount(0));
		}

	}

}
