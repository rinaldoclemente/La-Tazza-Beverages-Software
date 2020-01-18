package it.polito.latazza.dao;

import it.polito.latazza.data.PersonalAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalAccountDAO {

    public static PersonalAccount read(Integer id) {
        PersonalAccount personalAccount = null;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from personal_account where colleagueId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.first()) {
                personalAccount = new PersonalAccount(rs.getInt("balance"), rs.getInt("colleagueId"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personalAccount;
    }

    public static Integer write(PersonalAccount pa) {
        if(pa==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(personalAccountId) from personal_account");
            rs = ps.executeQuery();
//            if(rs.first()) id = rs.getInt(1)+1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into personal_account (personalaccountid, balance, colleagueid) values (?, ?, ?)");
            ps.setInt(1, id);
            ps.setInt(2, pa.getBalance());
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Integer(id);
    }

    public static int update(PersonalAccount pa) {
        if(pa==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        int result = 0;
        try {
            ps = connection.prepareStatement("update personal_account set balance=? where colleagueId=?");
            ps.setInt(1, pa.getBalance());
            ps.setInt(2, pa.getAccountId());
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
            ps = connection.prepareStatement("delete from PERSONAL_ACCOUNT");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
