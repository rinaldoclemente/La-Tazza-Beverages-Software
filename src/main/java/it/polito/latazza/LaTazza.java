package it.polito.latazza;

import it.polito.latazza.dao.DBManager;
import it.polito.latazza.data.DataImpl;
import it.polito.latazza.data.DataInterface;
import it.polito.latazza.gui.MainSwing;

import java.sql.SQLException;

public class LaTazza {

	public static void main(String[] args) {
		DBManager.createTables();
		DataInterface data = new DataImpl();
	    new MainSwing(data);
		try {
			DBManager.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
