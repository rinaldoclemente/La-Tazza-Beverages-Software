package it.polito.latazza.dao;

import it.polito.latazza.data.Colleague;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColleagueDAO {

    public static ArrayList<Colleague> readAll() {
        ArrayList<Colleague> colleagues = new ArrayList<>();
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from colleague");
            rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) {
                System.out.println("No Colleague data available");
            }
            else {
                while(rs.next()) {
                    colleagues.add(new Colleague(rs.getInt("colleagueId"), rs.getString("name"), rs.getString("surname"), PersonalAccountDAO.read(rs.getInt("colleagueId"))));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colleagues;
    }

    public static Colleague read(Integer employeeId) {
        Colleague colleague = null;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("select * from colleague where colleagueId=?");
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();
            if(rs.first()) colleague = new Colleague(rs.getInt("colleagueId"), rs.getString("name"), rs.getString("surname"), PersonalAccountDAO.read(rs.getInt("colleagueId")));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colleague;
    }

    public static Integer write(Colleague c) {
        if(c==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            ps = connection.prepareStatement("select max(colleagueId) from colleague");
            rs = ps.executeQuery();
//            if(rs.first()) id = rs.getInt(1)+1;
            if (rs.first()) {
                id = Math.addExact(rs.getInt(1), 1);
            }
            ps = connection.prepareStatement("insert into colleague (colleagueId, name, surname) values (?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, c.getName());
            ps.setString(3, c.getSurname());
            ps.executeUpdate();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Integer(id);
    }

    public static int update(Colleague c) {
        if(c==null) return 0;
        Connection connection = DBManager.getConnection();
        PreparedStatement ps;
        int result = 0;
        try {
            ps = connection.prepareStatement("update colleague set name=?, surname=? where colleagueId=?");
            ps.setString(1, c.getName());
            ps.setString(2, c.getSurname());
            ps.setInt(3, c.getId());
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
            ps = connection.prepareStatement("delete from COLLEAGUE");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
