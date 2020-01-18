package it.polito.latazza.dao;

import it.polito.latazza.data.LaTazzaAccount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DBManager {
    private static Connection connection;

    public static Connection getConnection(){
        try {
            if (connection == null) {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection("jdbc:h2:file:db/latazza", "manager", "1234");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
    public static void createTables() {
        if (Files.exists(Paths.get("db/latazza.h2.db"))) {
            System.out.println("Database already exists");
        } else {
            Connection connection = DBManager.getConnection();
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement(
                            "create table colleague\n" +
                                "(\n" +
                                "    colleagueId int not null,\n" +
                                "    name varchar2(30) not null,\n" +
                                "    surname varchar2(30) not null,\n" +
                                "    constraint colleague_pk\n" +
                                "        primary key (colleagueId)\n" +
                                ");\n" +
                                "\n" +
                                "create table personal_account\n" +
                                "(\n" +
                                "    personalAccountId int not null,\n" +
                                "    balance int not null,\n" +
                                "    colleagueId int not null,\n" +
                                "    constraint personal_account_pk\n" +
                                "        primary key (personalAccountId),\n" +
                                "    constraint personal_account_COLLEAGUE_ID_fk\n" +
                                "        foreign key (personalAccountId) references COLLEAGUE\n" +
                                "            on update cascade on delete cascade\n" +
                                ");\n" +
                                "\n" +
                                "create table CAPSULE_TYPE\n" +
                                "(\n" +
                                "    CAPSULEID     INTEGER     not null,\n" +
                                "    NAME          VARCHAR(30) not null,\n" +
                                "    OLD_PRICE     INTEGER     not null,\n" +
                                "    OLD_QUANTITY  INTEGER     not null,\n" +
                                "    OLD_QTYPERBOX INTEGER     not null,\n" +
                                "    NEW_PRICE     INTEGER     not null,\n" +
                                "    NEW_QUANTITY  INTEGER     not null,\n" +
                                "    NEW_QTYPERBOX INTEGER     not null,\n" +
                                "    constraint CAPSULE_TYPE_PK\n" +
                                "        primary key (CAPSULEID)\n" +
                                ");"+
                                "create table BOX_PURCHASE\n" +
                                "(\n" +
                                "\tBOXPURCHASEID INTEGER not null,\n" +
                                "\tDATE VARCHAR(50) not null,\n" +
                                "\tAMOUNT INTEGER not null,\n" +
                                "\tQUANTITY INTEGER not null,\n" +
                                "\tCAPSULEID INTEGER not null,\n" +
                                "\tconstraint BOX_PURCHASE_PK\n" +
                                "\t\tprimary key (BOXPURCHASEID),\n" +
                                "\tconstraint BOX_PURCHASE_CAPSULE_TYPE_ID_FK\n" +
                                "\t\tforeign key (CAPSULEID) references CAPSULE_TYPE\n" +
                                "\t\t\ton update cascade on delete cascade\n" +
                                ");\n" +
                                "\n" +
                                "create table CONSUMPTION\n" +
                                "(\n" +
                                "\tCONSUMPTIONID INTEGER not null,\n" +
                                "\tDATE VARCHAR(50) not null,\n" +
                                "\tAMOUNT INTEGER not null,\n" +
                                "\tQUANTITY INTEGER not null,\n" +
                                "\tCAPSULEID INTEGER not null,\n" +
                                "\tCOLLEAGUEID INTEGER,\n" +
                                "\tFROMACCOUNT BOOLEAN,\n" +
                                "\tconstraint CONSUMPTION_PK\n" +
                                "\t\tprimary key (CONSUMPTIONID),\n" +
                                "\tconstraint CONSUMPTION_CAPSULE_TYPE_ID_FK\n" +
                                "\t\tforeign key (CAPSULEID) references CAPSULE_TYPE\n" +
                                "\t\t\ton update cascade on delete cascade\n" +
                                ");\n" +
                                "\n" +
                                "create table RECHARGE\n" +
                                "(\n" +
                                "\tRECHARGEID INTEGER not null,\n" +
                                "\tDATE VARCHAR(50) not null,\n" +
                                "\tAMOUNT INTEGER not null,\n" +
                                "\tCOLLEAGUEID INTEGER not null,\n" +
                                "\tconstraint RECHARGE_PK\n" +
                                "\t\tprimary key (RECHARGEID)\n" +
                                ");\n" +
                                "\n" +
                                "create table LATAZZA_ACCOUNT\n" +
                                "(\n" +
                                "\tID INTEGER not null,\n" +
                                "\tBALANCE INTEGER not null,\n" +
                                "\tconstraint LATAZZA_ACCOUNT_PK\n" +
                                "\t\tprimary key (ID)\n" +
                                ");\n"
                );
                ps.executeUpdate();
                ps.close();
                LaTazzaAccountDAO.write(new LaTazzaAccount(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createLaTazzaTable() {
        if (!Files.exists(Paths.get("db/latazza.h2.db"))) {
            Connection connection = DBManager.getConnection();
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement("create table latazza_account (id integer not null, balance integer not null, constraint latazza_account_pk primary key (id));");
                ps.executeUpdate();
                LaTazzaAccountDAO.write(new LaTazzaAccount(0));
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("LaTazza table already exists");
        }

    }

    public static void deleteLaTazzaTable() {
        if (Files.exists(Paths.get("db/latazza.h2.db"))) {
            Connection connection = DBManager.getConnection();
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement("drop table latazza_account;");
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("db not created");
        }
    }

    public static void deleteDB() {
        try {
            Files.delete(Paths.get("db/latazza.h2.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}