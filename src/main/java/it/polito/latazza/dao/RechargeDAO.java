package it.polito.latazza.dao;

import it.polito.latazza.data.Recharge;

import java.sql.*;
import java.util.ArrayList;

public class RechargeDAO {

    public static ArrayList<Recharge> readAll() {
        ArrayList<Recharge> recharges = new ArrayList<>();
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from RECHARGE");
            rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) {
                System.out.println("No Recharge data available");
            }
            else {
                while (rs.next()) {
                    recharges.add(new Recharge(rs.getInt("rechargeid"), rs.getString("date"), rs.getInt("amount"), rs.getInt("colleagueid")));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recharges;
    }

//    public static Recharge read(Integer colleagueId) {
//        Recharge recharge = null;
//        Connection connection = DBManager.getConnection();
//        PreparedStatement ps;
//        ResultSet rs;
//        try {
//            ps = connection.prepareStatement("select * from RECHARGE where COLLEAGUEID=?");
//            ps.setInt(1, colleagueId);
//            rs = ps.executeQuery();
//            if (rs.first())
//                recharge = new Recharge(rs.getInt("rechargeid"), rs.getString("date"), rs.getInt("amount"), rs.getInt("colleagueid"));
//            ps.close();
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return recharge;
//    }

    public static Integer write(Recharge re) {
        if(re==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(RECHARGEID) from RECHARGE");
            rs = ps.executeQuery();
//            if (rs.first()) id = rs.getInt(1) + 1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into RECHARGE (RECHARGEID, DATE, AMOUNT, COLLEAGUEID) values (?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, re.getDate());
            ps.setInt(3, re.getAmount());
            ps.setInt(4, re.getEmployeeId());
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
            ps = connection.prepareStatement("delete from RECHARGE");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}