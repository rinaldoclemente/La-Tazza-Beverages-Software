package it.polito.latazza.dao;

import it.polito.latazza.data.CapsuleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CapsuleTypeDAO {

   public static ArrayList<CapsuleType> readAll() {
        ArrayList<CapsuleType> capsules = new ArrayList<>();
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from CAPSULE_TYPE");
            rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) {
                System.out.println("No CapsuleType data available");
            }
            else {
                while(rs.next()) {
                    capsules.add(new CapsuleType(rs.getInt("capsuleId"), rs.getString("name"), rs.getInt("old_price"), rs.getInt("old_quantity"), rs.getInt("old_qtyperbox"), rs.getInt("new_price"), rs.getInt("new_quantity"), rs.getInt("new_qtyperbox")));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return capsules;
    }

    public static CapsuleType read(Integer Id) {

        CapsuleType capsule = null;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from CAPSULE_TYPE where CAPSULEID=?");
            ps.setInt(1, Id);
            rs = ps.executeQuery();
            if(rs.first()) capsule = new CapsuleType(rs.getInt("capsuleId"), rs.getString("name"), rs.getInt("old_price"), rs.getInt("old_quantity"), rs.getInt("old_qtyperbox"), rs.getInt("new_price"), rs.getInt("new_quantity"), rs.getInt("new_qtyperbox"));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return capsule;
    }

    public static Integer write(CapsuleType ct) {
        if(ct==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(CAPSULEID) from CAPSULE_TYPE");
            rs = ps.executeQuery();
//            if(rs.first()) id = rs.getInt(1)+1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into CAPSULE_TYPE (CAPSULEID, NAME, OLD_PRICE, OLD_QUANTITY, OLD_QTYPERBOX, NEW_PRICE, NEW_QUANTITY, NEW_QTYPERBOX) values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, ct.getName());
            ps.setInt(3, ct.getOldPrice());
            ps.setInt(4, ct.getOldQuantity());
            ps.setInt(5, ct.getOldQtyPerBox());
            ps.setInt(6, ct.getNewPrice());
            ps.setInt(7, ct.getNewQuantity());
            ps.setInt(8, ct.getNewQtyPerBox());
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Integer(id);
    }

    public static int update(CapsuleType ct) {
        if(ct==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        int result = 0;
        try {
            ps = connection.prepareStatement("update CAPSULE_TYPE set NAME=?, OLD_QUANTITY=?, OLD_QTYPERBOX=?, OLD_PRICE=?, NEW_PRICE=?, NEW_QUANTITY=?, NEW_QTYPERBOX=? where CAPSULEID=?");
            ps.setString(1, ct.getName());
            ps.setInt(2, ct.getOldQuantity());
            ps.setInt(3, ct.getOldQtyPerBox());
            ps.setInt(4, ct.getOldPrice());
            ps.setInt(5, ct.getNewPrice());
            ps.setInt(6, ct.getNewQuantity());
            ps.setInt(7, ct.getNewQtyPerBox());
            ps.setInt(8, ct.getId());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void delete(){
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("delete from CAPSULE_TYPE");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
