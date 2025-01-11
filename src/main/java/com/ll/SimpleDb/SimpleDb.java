package com.ll.SimpleDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleDb {
    Connection conn = null;
    private boolean devMode = false;

    public SimpleDb(String hostName, String userName, String password, String dbName) {
        String url = "jdbc:mysql://" + hostName + "/" + dbName;

        try {
            // MySQL JDBC Driver 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password); // DB 연결
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public void run(String sql, Object... args) {
        if(devMode) {
            System.out.println("[SQL Query] " + sql);
        }

        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                prst.setObject(i + 1, args[i]);
            }
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Sql genSql() {
        return new Sql(conn);
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
    }

    public void rollback() {
    }

    public void commit() {
    }
}
