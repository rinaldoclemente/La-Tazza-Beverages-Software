package it.polito.latazza.dao;

import it.polito.latazza.data.BoxPurchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoxPurchaseDAO {

    public static ArrayList<BoxPurchase> readAll() {
        ArrayList<BoxPurchase> boxPurchases = new ArrayList<>();
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from box_purchase");
            rs = ps.executeQuery();
            while(rs.next()) {
                boxPurchases.add(new BoxPurchase(rs.getInt("boxpurchaseid"), rs.getString("date"), rs.getInt("amount"), rs.getInt("quantity"), CapsuleTypeDAO.read(rs.getInt("capsuleId"))));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boxPurchases;
    }

    public static Integer write(BoxPurchase boxPurchase) {
        if(boxPurchase==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(boxpurchaseId) from box_purchase");
            rs = ps.executeQuery();
//            if(rs.first()) id = rs.getInt(1)+1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into box_purchase (boxpurchaseid, date, amount, quantity, capsuleid) values (?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, boxPurchase.getDate());
            ps.setInt(3, boxPurchase.getAmount());
            ps.setInt(4, boxPurchase.getQuantity());
            ps.setInt(5, boxPurchase.getCapsuleType().getId());
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
            ps = connection.prepareStatement("delete from BOX_PURCHASE");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
