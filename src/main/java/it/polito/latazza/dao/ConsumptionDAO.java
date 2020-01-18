package it.polito.latazza.dao;

import it.polito.latazza.data.Consumption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsumptionDAO {

    public static ArrayList<Consumption> readAll() {
        ArrayList<Consumption> consumptions = new ArrayList<>();
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from consumption");
            rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) {
                System.out.println("No Consumption data available");
            }
            else {
                while(rs.next()) {
                    consumptions.add(new Consumption(rs.getInt("consumptionId"), rs.getString("Date"), rs.getInt("Amount"), rs.getInt("Quantity"), rs.getInt("CapsuleId"), rs.getInt("colleagueId"), rs.getBoolean("fromAccount")));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consumptions;
    }

//    public static Consumption read(Integer employeeId) {
//        Consumption consumption = null;
//        Connection connection = DBManager.getConnection();
//        PreparedStatement ps;
//        ResultSet rs;
//        try {
//            ps = connection.prepareStatement("select * from consumption where CAPSULEID=?");
//            ps.setInt(1, employeeId);
//            rs = ps.executeQuery();
//            if(rs.first()) consumption = new Consumption(rs.getInt("consumptionId"), rs.getString("Date"), rs.getInt("Amount"), rs.getInt("Quantity"), rs.getInt("CapsuleId"), rs.getInt("colleagueId"), rs.getBoolean("fromAccount"));
//            ps.close();
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return consumption;
//    }

    public static Integer write(Consumption c) {
        if(c==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(CONSUMPTIONID) from CONSUMPTION");
            rs = ps.executeQuery();
//            if(rs.first()) id = rs.getInt(1)+1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into CONSUMPTION (CONSUMPTIONID, DATE, AMOUNT, QUANTITY, CAPSULEID, COLLEAGUEID, fromAccount) values (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, c.getDate());
            ps.setInt(3, c.getAmount());
            ps.setInt(4, c.getQuantity());
            ps.setInt(5, c.getCapsId());
            ps.setInt(6, c.getEmployeeId());
            ps.setBoolean(7, c.isFromAccount());
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Integer(id);
    }

    public static void delete(){
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("delete from CONSUMPTION");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
