package it.polito.latazza.dao;

import it.polito.latazza.data.LaTazzaAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LaTazzaAccountDAO {

    public static LaTazzaAccount read() {
        LaTazzaAccount laTazzaAccount = null;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from latazza_account");
            rs = ps.executeQuery();
            if(rs.first()) laTazzaAccount = new LaTazzaAccount(rs.getInt("balance"));
            else System.out.println("No LaTazza Account data available");
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laTazzaAccount;
    }

    public static Integer write(LaTazzaAccount laTazzaAccount) {
        if(laTazzaAccount==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("insert into LATAZZA_ACCOUNT (id, BALANCE) values (?, ?)");
            ps.setInt(1, 1);
            ps.setInt(2, laTazzaAccount.getBalance());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int update(LaTazzaAccount laTazzaAccount) {
        if(laTazzaAccount==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        int res = 0;
        try {
            ps = connection.prepareStatement("update latazza_account set balance=? where id=1");
            ps.setInt(1, laTazzaAccount.getBalance());
            res = ps.executeUpdate();
            if(res==0) System.out.println("No LaTazza Account data available");
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void delete(){
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("delete from LATAZZA_ACCOUNT");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
